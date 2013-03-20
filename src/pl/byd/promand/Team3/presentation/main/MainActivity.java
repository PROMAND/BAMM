package pl.byd.promand.Team3.presentation.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.View;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.List;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.data.GlobalState;
import pl.byd.promand.Team3.infrastructure.data.MyDAO;
import pl.byd.promand.Team3.infrastructure.data.Restaurant;
import pl.byd.promand.Team3.infrastructure.main.MainExpandableListAdapter;
import pl.byd.promand.Team3.infrastructure.main.MenuItemDetailsBean;
import pl.byd.promand.Team3.presentation.menu.MenuActivity;



public class MainActivity extends SherlockActivity {
    private AlertDialog alert;

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("About");
                builder.setMessage("Some information about restaurants program..");

                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        dialog.dismiss();
                    }

                });

                alert = builder.create();
                alert.show();
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
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);

        GlobalState.getInstance().mainHandler = new Handler() {
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
                moveToMenu.putExtra("RestaurantId", (Integer) view.getTag(R.id.TAG_VIEW));
                startActivity(moveToMenu);

                return false;
            }
        });

        MyDAO.getInstance().downloadRestaurant();
    }

    private void showListView() {
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);
        List<String> groupTitleList = new ArrayList<String>();

        List<MenuItemDetailsBean> groupList = new ArrayList<MenuItemDetailsBean>();
        ArrayList<Restaurant> resArray = MyDAO.getInstance().getRestaurantArray();
        ArrayList<Pair<Integer, Integer>> resIdList = new ArrayList<Pair<Integer, Integer>>();

        for (int i = 0; i < resArray.size(); i++) {
            groupTitleList.add(resArray.get(i).Name);
            MenuItemDetailsBean restaurantDetails1 = new MenuItemDetailsBean();
            restaurantDetails1.add(resArray.get(i).Desc_short);
            groupList.add(restaurantDetails1);
            Pair<Integer, Integer> res = new Pair<Integer, Integer>(i, resArray.get(i).Restaurant_ID);
            resIdList.add(res);
        }

        MainExpandableListAdapter adapter = new MainExpandableListAdapter(groupTitleList, groupList, this, resIdList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
