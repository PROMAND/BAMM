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
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DownloadJasonFile extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... sUrl) {
        InputStream is = null;
        String result = "";

        try {
            // Thread.sleep(4000);   //Slow network simulate
            HttpClient httpclient = new DefaultHttpClient();
            String Url;
            if (sUrl.length == 1) {
                Url = "http://promand.xaa.pl/server/" + sUrl[0] + ".php";
            } else {
                Url = "http://promand.xaa.pl/server/" + sUrl[0] + ".php?" + sUrl[1] + "=" + sUrl[2];
            }
            Log.d("MyDebug", "Download: " + Url);
            HttpPost httppost = new HttpPost(Url); //ToDo: hardcoded name
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            jsonString = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sUrl[0];
    }

    protected void onPostExecute(String result) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            Message msg = new Message();
            Bundle bundle = new Bundle();

            if (result.compareTo("restaurant") == 0) { //Todo: hardcoded name
                MyDAO.getInstance().restaurantArray.clear();
                saveRestaurantData(jsonArray);
                bundle.putString("type", "restaurant");
                msg.setData(bundle);
                GlobalState.getInstance().mainHandler.sendMessage(msg);
            } else if (result.compareTo("menu_item") == 0) {
                saveMenuItemData(jsonArray);
                bundle.putString("type", "menu_item");
                msg.setData(bundle);
                GlobalState.getInstance().menuHandler.sendMessage(msg);
            } else if (result.equals("menu")){
                saveMenuCategoryItemData(jsonArray);
                bundle.putString("type", "menu");
                msg.setData(bundle);
                GlobalState.getInstance().mainHandler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveRestaurantData(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = jsonArray.getJSONObject(i);
            Restaurant temp = new Restaurant();
            temp.Contact_email = jo.getString("Contact_email");
            temp.Contact_number = jo.getString("Contact_number");
            temp.Desc_long = jo.getString("Desc_long");
            temp.Localization_x = jo.getString("Localization_x");
            temp.Localization_y = jo.getString("Localization_y");
            temp.Name = jo.getString("Name");
            temp.Path_to_img = jo.getString("Path_to_img");
            temp.Desc_short = jo.getString("Desc_short");
            temp.Sits_current = jo.getInt("Sits_current");
            temp.Sits_max = jo.getInt("Sits_max");
            temp.Restaurant_ID = jo.getInt("Restaurant_ID");

            MyDAO.getInstance().restaurantArray.add(temp);
        }
    }

    private void saveMenuItemData(JSONArray jsonArray) throws JSONException {

        ArrayList<MenuItem> menuItemArray = new ArrayList<MenuItem>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = jsonArray.getJSONObject(i);
            MenuItem temp = new MenuItem();

            temp.menuItemId = jo.getInt("Menu_item_ID");
            temp.menuId = jo.getInt("Menu_ID");
            temp.restaurantId = jo.getInt("Restaurant_ID");
            temp.description = jo.getString("Description");
            temp.ingredients = jo.getString("Ingredients");
            temp.name = jo.getString("Name");
            temp.path_to_img = jo.getString("Path_to_img");
            temp.price = jo.getDouble("Price");

            menuItemArray.add(temp);
        }
        MyDAO.getInstance().setMenuItemArray(menuItemArray);

    }

    private void saveMenuCategoryItemData(JSONArray jsonArray) throws JSONException {
        ArrayList<MenuCategory> menuCategoryList = new ArrayList<MenuCategory>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = jsonArray.getJSONObject(i);
            MenuCategory temp = new MenuCategory();

            temp.setMenuId(jo.getInt("Menu_ID"));
            temp.setCategoryId(jo.getInt("Category"));
            temp.setDescription(jo.getString("Description"));

            menuCategoryList.add(temp);
        }
        MyDAO.getInstance().setMenuList(menuCategoryList);
    }

    public String jsonString;
}
