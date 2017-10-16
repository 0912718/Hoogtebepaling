package stage.hoogtebepaling5;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    public DatabaseOpenHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        final LocationManager gps = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        mDBHelper = new DatabaseOpenHelper(this);

        // checkt of wifi en GPS aanstaan
        if (wifi.isWifiEnabled() && gps.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //laad de plattegrond in scherm
            ImageView image = (ImageView) findViewById((R.id.plattegrond));
            image.setImageResource(R.drawable.beganegrond);
            //image staat standaard uit om bugs te voorkomen, hieronder wordt ie visible gemaakt
            image.setVisibility(View.VISIBLE);
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            //Database access openen
            databaseAccess.open();
            //query uit getData() wordt in een list geladen
            List<String> lijst = databaseAccess.getData();
            //database wordt gesloten
            databaseAccess.close();
            //ter controle
            System.out.println(lijst);
        }
        else{
            //als wifi uitstaat
            if (!wifi.isWifiEnabled()) {

                //pop up waarin komt te staan dat wifi aanmoet
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setTitle("The app does not work with Wi-Fi turned off");
                dialog.setMessage("Do you want to turn on Wi-Fi?");
                dialog.setPositiveButton("Turn me on", (dialog1, id) -> {
                    wifi.setWifiEnabled(true);
                })
                        .setNegativeButton("Cancel ", (dialog12, which) -> {
                            wifi.setWifiEnabled(false);
                        });

                final AlertDialog alert = dialog.create();
                alert.show();
            }
            else{
                //pop up om gps aan te zetten
                final Intent action = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setTitle("The app does not work with Wi-Fi turned off");
                dialog.setMessage("Do you want to turn on Wi-Fi?");
                dialog.setPositiveButton("Turn me on", (dialog1, id) -> {
                    startActivity(action);
                })
                        .setNegativeButton("Cancel ", (dialog12, which) -> {


                        });
                final AlertDialog alert = dialog.create();
                alert.show();
            }
        }
    }
}
