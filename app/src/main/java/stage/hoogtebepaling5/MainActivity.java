package stage.hoogtebepaling5;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public DatabaseOpenHelper mDBHelper;
    public SQLiteDatabase mDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        mDBHelper = new DatabaseOpenHelper(this);
        if (wifi.isWifiEnabled()){
              //TODO  Load Map
            System.out.println("lol1");
            System.out.println("lol2");
             ListView listview = (ListView) findViewById(R.id.listView);
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            List<String> lijst = databaseAccess.getData();
            databaseAccess.close();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lijst);
            listview.setAdapter(adapter);
        }
        else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("The app does not work with Wi-Fi turned off");
            dialog.setMessage("Do you want to turn on Wi-Fi?" );
            dialog.setPositiveButton("Turn on", (dialog1, id) -> {
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
