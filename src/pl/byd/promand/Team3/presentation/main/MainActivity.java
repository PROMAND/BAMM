package pl.byd.promand.Team3.presentation.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ExpandableListView;
import com.actionbarsherlock.app.SherlockActivity;
import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.data.GlobalState;
import pl.byd.promand.Team3.infrastructure.data.MyDAO;
import pl.byd.promand.Team3.infrastructure.data.Restaurant;
import pl.byd.promand.Team3.infrastructure.main.MainExpandableListAdapter;
import pl.byd.promand.Team3.infrastructure.main.MenuItemDetailsBean;
import pl.byd.promand.Team3.presentation.menu.MenuActivity;
import com.actionbarsherlock.view.Menu;
import pl.byd.promand.Team3.presentation.order.OrderActivity;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends SherlockActivity {
    private String restaurantOne;
    private String restaurantTwo;
    private String restaurantThree;

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                Intent order = new Intent(this, OrderActivity.class);
                order.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(order);
                return true;
            case R.id.location:
                String coordinate1 = "53.127256";
                String coordinate2 = "17.993782";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q=" + coordinate1 + "," + coordinate2));
                startActivity(intent);
            case R.id.info:

                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Change app action bar title
        getSupportActionBar().setTitle("Restaurants");
        //Intent intent = new Intent(this, MenuActivity.class);
        //startActivity(intent);
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);

        //TODO
        // it is expected that these values will be placed in a database

        GlobalState.getInstance().mainHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.getData().getString("type").compareTo("restaurant") == 0)
                    showListView();
            }
        };

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                Intent moveToMenu = new Intent(getApplicationContext(), MenuActivity.class);
                Log.d("MyDebug","onClick " + view.getTag(R.id.TAG_VIEW));
                moveToMenu.putExtra("RestaurantId",(Integer)view.getTag(R.id.TAG_VIEW));
                startActivity(moveToMenu);

                return false;
            }
        });

        MyDAO.getInstance().downloadRestaurant();
    }

    private void showListView(){
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);
        List<String> groupTitleList = new ArrayList<String>();

        List<MenuItemDetailsBean> groupList = new ArrayList<MenuItemDetailsBean>();
        ArrayList <Restaurant> resArray = MyDAO.getInstance().getRestaurantArray();
        ArrayList<Pair<Integer,Integer>> resIdList = new ArrayList<Pair<Integer,Integer>>();

        for(int i = 0;i < resArray.size();i++){
            groupTitleList.add(resArray.get(i).Name);
            MenuItemDetailsBean restaurantDetails1 = new MenuItemDetailsBean();
            restaurantDetails1.add(resArray.get(i).Desc_short);
            groupList.add(restaurantDetails1);
            Pair<Integer,Integer> res = new Pair<Integer,Integer>(i,resArray.get(i).Restaurant_ID);
            resIdList.add(res);
        }

        MainExpandableListAdapter adapter = new MainExpandableListAdapter(groupTitleList, groupList, this, resIdList);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getSupportMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

}
