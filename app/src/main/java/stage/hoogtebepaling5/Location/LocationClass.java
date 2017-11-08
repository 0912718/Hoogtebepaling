package stage.hoogtebepaling5.Location;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.List;

import stage.hoogtebepaling5.R;

public class LocationClass extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private Location myLocation;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_display_layout);
       // final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        image = findViewById((R.id.mapview));
        image.setImageResource(R.drawable.beganegrond);
        text = findViewById(R.id.textView);
        image.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE); //just for the demo, shall be removed later in the project
        setUpGClient(); //setting up google services


        }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        if (myLocation != null) {
            Double latitude = myLocation.getLatitude();
            Double longitude = myLocation.getLongitude();
            System.out.println(latitude + " " + longitude);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private void getMyLocation() { // retrieve location
            if (googleApiClient != null) {
                if (googleApiClient.isConnected()) {
                    int permissionLocation = ContextCompat.checkSelfPermission(LocationClass.this,
                            Manifest.permission.ACCESS_FINE_LOCATION);
                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                        myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(300000); //300.000 ms is 5 minutes
                        locationRequest.setFastestInterval(300000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                                .addLocationRequest(locationRequest);
                        builder.setAlwaysShow(true); //shows dialog for GPS to turn on
                        LocationServices.FusedLocationApi
                                .requestLocationUpdates(googleApiClient, locationRequest, this);
                        PendingResult<LocationSettingsResult> result =
                                LocationServices.SettingsApi
                                        .checkLocationSettings(googleApiClient, builder.build());
                        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                            @Override
                            public void onResult(LocationSettingsResult result) {
                                final Status status = result.getStatus();
                                switch (status.getStatusCode()) {
                                    case LocationSettingsStatusCodes.SUCCESS:
                                        image.setVisibility(View.VISIBLE); //if location is success
                                        text.setVisibility(View.VISIBLE);
                                        // All location settings are satisfied.
                                        // initializing location requests
                                        int permissionLocation = ContextCompat
                                                .checkSelfPermission(LocationClass.this,
                                                        Manifest.permission.ACCESS_FINE_LOCATION);
                                        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                            myLocation = LocationServices.FusedLocationApi
                                                    .getLastLocation(googleApiClient);
                                            System.out.println("test4");
                                        }
                                        break;
                                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                        // Location settings are not satisfied.
                                        // showing user dialog
                                        try {
                                            // Show the dialog by calling startResolutionForResult(),
                                            // and check the result in onActivityResult().
                                            // Ask to turn on GPS automatically with an genius dialog
                                            System.out.println("test3");
                                            status.startResolutionForResult(LocationClass.this,
                                                    REQUEST_CHECK_SETTINGS_GPS);
                                        } catch (IntentSender.SendIntentException e) {
                                            // Ignore the error.
                                            System.out.println("test2");
                                        }
                                        break;
                                    }
                            }
                        });
                    }
                }
            }

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }

    private void checkPermissions(){
        int permissionLocation = ContextCompat.checkSelfPermission(LocationClass.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }else{
            getMyLocation();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(LocationClass.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }

}


