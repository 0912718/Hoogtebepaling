//package stage.hoogtebepaling5;
//
//import android.os.AsyncTask;
//
//import com.google.gson.JSONParser;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//
//public class JSONParser {
//    public class JSONParser {
//
//        static InputStream is = null;
//        static JSONObject jObj = null;
//        static String json = "";
//
//        // constructor
//        public JSONParser() {}
//
//        public JSONObject getJSONFromUrl(String url) {
//
//            // Making HTTP request
//            try {
//                // defaultHttpClient
//                DefaultHttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost(url);
//
//                HttpResponse httpResponse = httpClient.execute(httpPost);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                is = httpEntity.getContent();
//
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (ClientProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(
//                        is, "iso-8859-1"), 8);
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line + "\n");
//                }
//                is.close();
//                json = sb.toString();
//            } catch (Exception e) {
//                Log.e("Buffer Error", "Error converting result " + e.toString());
//            }
//
//            // try parse the string to a JSON object
//            try {
//                jObj = new JSONObject(json);
//            } catch (JSONException e) {
//                Log.e("JSON Parser", "Error parsing data " + e.toString());
//            }
//
//            // return JSON String
//            return jObj;
//
//        }
//    }
////        final String TAG = "AsyncTaskParseJson.java";
////
////        // set your json string url here
////        String yourJsonStringUrl = "https://location-api-acceptance.safeguardapp.nl/api/v1/access-points/653532323033643938396666";
////
////        // contacts JSONArray
////        JSONArray dataJsonArr = null;
////        private JSONObject json;
////
////        @Override
////        protected void onPreExecute() {}
////
////        @Override
////        protected JSONObject doInBackground(String... arg0) {
////
////            try {
////
////                // instantiate our json parser
////                JSONParser jParser = new JSONParser();
////
////                // get json string from url
////                json = jParser.getJSONArray(yourJsonStringUrl);
////
////                // get the array of users
////                dataJsonArr = json.getJSONArray("BSSID");
////
////
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////
////            return json;
////        }
////
////        @Override
////        protected void onPostExecute(JSONObject strFromDoInBg) {}
////    }
//
