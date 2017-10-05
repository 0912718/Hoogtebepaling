package stage.hoogtebepaling5;

import android.app.AlertDialog;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public DatabaseOpenHelper mDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        mDBHelper = new DatabaseOpenHelper(this);
        if (wifi.isWifiEnabled()){
              //TODO  Load Map
            ImageView image = (ImageView) findViewById((R.id.plattegrond));
            image.setImageResource(R.drawable.beganegrond);
//             ListView listview = (ListView) findViewById(R.id.listView);
//            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            //Database access!!!!!
//            databaseAccess.open();
//            List<String> lijst = databaseAccess.getData();
//            databaseAccess.close();
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lijst);
//            listview.setAdapter(adapter);
        }
        else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("The app does not work with Wi-Fi turned off");
            dialog.setMessage("Do you want to turn on Wi-Fi?" );
            dialog.setPositiveButton("Turn me on", (dialog1, id) -> {
                wifi.setWifiEnabled(true);
            })
                    .setNegativeButton("Cancel ", (dialog12, which) -> {
                        wifi.setWifiEnabled(false);
                    });

            final AlertDialog alert = dialog.create();
            alert.show();
        }
    }
}