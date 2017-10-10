//TODO --> API OP LAAG PITJE, EERST ZORGEN DAT DE REST VAN DE APP WERKT!
package stage.hoogtebepaling5;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
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
    private JSONObject jsonObject;

//    private static final String ROOT_URL = "https://location-api-acceptance.safeguardapp.nl";
//    private static final String KEY_ID = "_id";
//    private static final String KEY_BSSID = "BSSID";
//    private static final String KEY_SSID = "SSID";
//    private static final String KEY_geofence = "geofence";
//    private static final String KEY_organisation = "organisation";
//    private static final String KEY_created_at = "created_at";
    //private List<Data> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        final LocationManager gps = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        mDBHelper = new DatabaseOpenHelper(this);
        //getAccesspoints();

        // checkt of wifi en GPS aanstaan
        if (wifi.isWifiEnabled() && gps.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            ImageView image = (ImageView) findViewById((R.id.plattegrond));
            image.setImageResource(R.drawable.beganegrond);
            image.setVisibility(View.VISIBLE);

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
            if (!wifi.isWifiEnabled()) {


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
//    // you can make this class as another java file so it will be separated from your main activity.
//    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {
//
//        final String TAG = "AsyncTaskParseJson.java";
//
//        // set your json string url here
//        String yourJsonStringUrl = "https://location-api-acceptance.safeguardapp.nl/api/v1/access-points/653532323033643938396666";
//
//        // contacts JSONArray
//        JSONArray dataJsonArr = null;
//
//
//        @Override
//        protected void onPreExecute() {}
//
//        @Override
//        protected String doInBackground(String... arg0) {
//
//            try {
//
//                // instantiate our json parser
//                JSONParser jParser = new JSONParser();
//                JSONObject json = jParser.makeHttpRequest("https://location-api-acceptance.safeguardapp.nl/api/v1/access-points/653532323033643938396666", "GET", null);
//                // get json string from url
//                //JSONObject json = jParser.makeHttpRequest(yourJsonStringUrl);
//
//                // get the array of users
//                dataJsonArr = new JSONArray(json.toString());
//
//                // loop through all users
//                for (int i = 0; i < dataJsonArr.length(); i++) {
//
//                    JSONObject c = dataJsonArr.getJSONObject(i);
//
//                    // Storing each json item in variable
//                    String id = c.getString("_id");
//                    String BSSID = c.getString("BSSID");
//                    String SSID = c.getString("SSID");
//                    String geofence = c.getString("geofence");
//                    String organ = c.getString("organisation");
//                    String created = c.getString("created_at");
//
//
//                    // show the values in our logcat
//                    Log.e(TAG, "id: " + id
//                            + ", BSSID: " + BSSID
//                            + ", SSID: " + SSID
//                            + ", geofence: " + geofence
//                            + ", organisation: " + organ
//                            + ", created at: " + created);
//
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String strFromDoInBg) {}
//    }
//private void getAccesspoints(){
//    //While the app fetched data we are displaying a progress dialog
//    final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);
//
//    //Creating a rest adapter
//    RestAdapter adapter = new RestAdapter.Builder()
//            .setEndpoint(ROOT_URL)
//            .build();
//
//    //Creating an object of our api interface
//    IAccessPointsAPI api = adapter.create(IAccessPointsAPI.class);
//
//    //Defining the method
//    api.getAccesspoints(new Callback<List<Data>>() {
//        @Override
//        public void success(List<Data> list, Response response) {
//            //Dismissing the loading progressbar
//            loading.dismiss();
//
//            //Storing the data in our list
//            data = list;
//            System.out.println(list);
//
//            //Calling a method to show the list
//            //showList();
//        }
//
//        @Override
//        public void failure(RetrofitError error) {
//            //you can handle the errors here
//        }
//    });
//}
}
