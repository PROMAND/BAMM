package pl.byd.promand.Team3.presentation.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

        for(int i = 0;i < resArray.size();i++){
            groupTitleList.add(resArray.get(i).Name);
            MenuItemDetailsBean restaurantDetails1 = new MenuItemDetailsBean();
            restaurantDetails1.add(resArray.get(i).Desc_short);
            groupList.add(restaurantDetails1);
        }

        MainExpandableListAdapter adapter = new MainExpandableListAdapter(groupTitleList, groupList, this);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getSupportMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

}
