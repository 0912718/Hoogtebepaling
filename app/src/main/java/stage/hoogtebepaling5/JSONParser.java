//TODO --> API OP LAAG PITJE, EERST ZORGEN DAT DE REST VAN DE APP WERKT!

//package stage.hoogtebepaling5;
//
///**
// * Created by Edgar on 10/6/2017.
// */
//import android.util.Log;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//
//public class JSONParser {
//        final String TAG = "JSONParser.java";
//
//        static InputStream is = null;
//        static JSONObject jObj = null;
//        static String json = "";
//
//        public JSONObject getJSONFromUrl(String url) {
//
//            // make HTTP request
//            try {
//
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
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line + "\n");
//                }
//                is.close();
//                json = sb.toString();
//
//            } catch (Exception e) {
//                Log.e(TAG, "Error converting result " + e.toString());
//            }
//
//            // try parse the string to a JSON object
//            try {
//                jObj = new JSONObject(json);
//            } catch (JSONException e) {
//                Log.e(TAG, "Error parsing data " + e.toString());
//            }
//
//            // return JSON String
//            return jObj;
//        }
//
//    }
//
