package stage.hoogtebepaling5;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import stage.hoogtebepaling5.Database.*;
import stage.hoogtebepaling5.Location.LocationActivity;

public class MainActivity extends AppCompatActivity {
    public DatabaseOpenHelper mDBHelper;
    final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDBHelper = new DatabaseOpenHelper(this);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this); //initialize db
        databaseAccess.open(); //Database access openen
        List<String> lijst = databaseAccess.getData(); //retrieve db data
        databaseAccess.close(); //database being closed
        System.out.println(lijst); //for debugging, shows the list from db
        if (!wifi.isWifiEnabled()) {
            checkWifi();
            final Handler handler = new Handler(); //is necessary to activate the wifi. If not wait, then location will not show in locationclass
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToLocation(); //after 5 seconds, go to locationclass
                }
            }, 5000);

        }else{
            goToLocation(); // if wifi is already activated, go to
        }
    }

    public void checkWifi(){
    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("The app does not work without Wi-Fi");
            dialog.setMessage("Do you want to turn on Wi-Fi?");
            dialog.setPositiveButton("Turn me on", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id){
            wifi.setWifiEnabled(true);
            System.out.println("clicked yes");
        }})
            .setNegativeButton("Cancel ",new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            wifi.setWifiEnabled(false);
        }});

    final AlertDialog alert = dialog.create();
            alert.show();
    }

    public void goToLocation(){
        startActivity(new Intent(this, LocationActivity.class));
    }
}



