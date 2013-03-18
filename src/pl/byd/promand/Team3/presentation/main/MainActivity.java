package pl.byd.promand.Team3.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import com.actionbarsherlock.app.SherlockActivity;
import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.main.MainExpandableListAdapter;
import pl.byd.promand.Team3.infrastructure.main.MenuItemDetailsBean;
import pl.byd.promand.Team3.presentation.menu.MenuActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SherlockActivity {
    private String restaurantOne;
    private String restaurantTwo;
    private String restaurantThree;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:

                break;
            case R.id.location:

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
        //Intent intent = new Intent(this, MenuActivity.class);
        //startActivity(intent);
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);

        //TODO
        // it is expected that these values will be placed in a database

        List<String> groupTitleList = new ArrayList<String>();

        restaurantOne = "Restaurant One";
        restaurantTwo = "Restaurant Two";
        restaurantThree = "Restaurant Three";

        groupTitleList.add(restaurantOne);
        groupTitleList.add(restaurantTwo);
        groupTitleList.add(restaurantThree);


        List<MenuItemDetailsBean> groupList = new ArrayList<MenuItemDetailsBean>();
        MenuItemDetailsBean restaurantDetails1 = new MenuItemDetailsBean();
        restaurantDetails1.add("RestaurantDetails");

        MenuItemDetailsBean restaurantDetails2 = new MenuItemDetailsBean();
        restaurantDetails2.add("RestaurantDetails");

        MenuItemDetailsBean restaurantDetails3 = new MenuItemDetailsBean();
        restaurantDetails3.add("RestaurantDetails");

        groupList.add(restaurantDetails1);
        groupList.add(restaurantDetails2);
        groupList.add(restaurantDetails3);

        MainExpandableListAdapter adapter = new MainExpandableListAdapter(groupTitleList, groupList, this);

        listView.setAdapter(adapter);



        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                Intent moveToMenu = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(moveToMenu);

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getSupportMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

}
