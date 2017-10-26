package stage.hoogtebepaling5.Location;

/**
 * Created by Edgar on 10/19/2017.
 */
import android.Manifest;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.Configurations;
import com.yayandroid.locationmanager.configuration.DefaultProviderConfiguration;
import com.yayandroid.locationmanager.configuration.GooglePlayServicesConfiguration;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;
import com.yayandroid.locationmanager.configuration.PermissionConfiguration;
import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.ProcessType;
import com.yayandroid.locationmanager.constants.ProviderType;

import stage.hoogtebepaling5.R;

public class LocationActivity extends LocationBaseActivity implements LocationPresenter.ILocationView {

    private ProgressDialog progressDialog;
    private TextView locationText;
    private LocationPresenter locationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_display_layout);

        locationText = (TextView) findViewById(R.id.locationText);
        locationPresenter = new LocationPresenter(this);
        getLocation();
        ImageView image = (ImageView) findViewById((R.id.floormap));
        image.setImageResource(R.drawable.beganegrond);
        //image is normally not visible,
        image.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationPresenter.destroy();
    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        //return Configurations.defaultConfiguration("Gimme the permission!", "Would you mind to turn GPS on?");
        return new LocationConfiguration.Builder()
                .keepTracking(true)
                .askForPermission(new PermissionConfiguration.Builder()
                        .requiredPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION })
                        .build())
                .useDefaultProviders(new DefaultProviderConfiguration.Builder()
                        .requiredTimeInterval(1000)
                        .acceptableTimePeriod(1000)
                        .requiredDistanceInterval(0)
                        .gpsMessage("Turn on GPS?")
                        .setWaitPeriod(ProviderType.GPS,1000)
                        .setWaitPeriod(ProviderType.NETWORK, 10 * 1000)
                        .build())
                .build();
    }


    @Override
    public void onLocationChanged(Location location) {
        locationPresenter.onLocationChanged(location);
    }

    @Override
    public void onLocationFailed(@FailType int failType) {
        locationPresenter.onLocationFailed(failType);
    }

    @Override
    public void onProcessTypeChanged(@ProcessType int processType) {
        locationPresenter.onProcessTypeChanged(processType);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()) {
            displayProgress();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        dismissProgress();
    }

    private void displayProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage("Getting location...");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public String getText() {
        return locationText.getText().toString();
    }

    @Override
    public void setText(String text) {
        locationText.setText(text);
    }

    @Override
    public void updateProgress(String text) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setMessage(text);
        }
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}