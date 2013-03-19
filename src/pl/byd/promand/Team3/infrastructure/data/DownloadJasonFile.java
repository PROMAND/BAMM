package pl.byd.promand.Team3.infrastructure.data;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DownloadJasonFile extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... sUrl) {

        InputStream is = null;
        String result = "";

        try {

            // Thread.sleep(4000);   //Slow network simulate
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://promand.xaa.pl/server/"+ sUrl[0]+".php"); //ToDo: hardcoded name
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            jsonString = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return sUrl[0];
    }

    protected void onPostExecute(String result){
        try{
            MyDAO.getInstance().restaurantArray.clear();
            JSONArray jsonArray = new JSONArray(jsonString);
            if(result.compareTo("restaurant") == 0){ //Todo: hardcoded name
                ArrayList<Restaurant> resArray = MyDAO.getInstance().getRestaurantArray();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = (JSONObject) jsonArray.getJSONObject(i);
                    Restaurant temp = new Restaurant();
                    temp.Contact_email = jo.getString("Contact_email");
                    temp.Contact_number = jo.getString("Contact_number") ;
                    temp.Desc_long = jo.getString("Desc_long");
                    temp.Localization_x = jo.getString("Localization_x");
                    temp.Localization_y = jo.getString("Localization_y");
                    temp.Name = jo.getString("Name");
                    temp.Path_to_img = jo.getString("Path_to_img");
                    temp.Desc_short = jo.getString("Desc_short");
                    temp.Sits_current = jo.getInt("Sits_current");
                    temp.Sits_max = jo.getInt("Sits_max");
                    temp.Restaurant_ID  = jo.getInt("Restaurant_ID");

                    MyDAO.getInstance().restaurantArray.add(temp);
                }
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","restaurant");
                msg.setData(bundle);
                GlobalState.getInstance().mainHandler.sendMessage(msg);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String jsonString;
}
