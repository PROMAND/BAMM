package pl.byd.promand.Team3.presentation.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.data.*;
import pl.byd.promand.Team3.infrastructure.menu.ExpandListAdapter;
import pl.byd.promand.Team3.infrastructure.menu.ExpandListGroup;
import pl.byd.promand.Team3.presentation.main.MainActivity;
import pl.byd.promand.Team3.presentation.order.OrderActivity;

import java.util.ArrayList;

public class MenuActivity extends SherlockActivity {
    private ExpandListAdapter ExpAdapter;
    private ArrayList<ExpandListGroup> ExpListItems;
    private ExpandableListView ExpandList;
    private MyDAO myDao;
    private Restaurant restaurant;

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent home = new Intent(this, MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
                return true;
            case R.id.cart:
                Intent order = new Intent(this, OrderActivity.class);
                order.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                order.putExtra("RestaurantId",(Integer)restaurant.Restaurant_ID);
                startActivity(order);
                return true;
            case R.id.location:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q=" + restaurant.Localization_x + "," + restaurant.Localization_y));
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        // Change app action bar title
        getSupportActionBar().setTitle("Menu");
        myDao = MyDAO.getInstance();

        int resId = getIntent().getExtras().getInt("RestaurantId");
        restaurant = MyDAO.getInstance().getRestaurant(resId);
        MyDAO.getInstance().downloadMenu();
        MyDAO.getInstance().downloadMenuItems(resId);


        GlobalState.getInstance().menuHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.getData().getString("type").compareTo("menu_item") == 0) {
                    ExpandList = (ExpandableListView) findViewById(R.id.menuExpandableListView);
                    ExpListItems = SetStandardGroups();
                    ExpAdapter = new ExpandListAdapter(MenuActivity.this, ExpListItems);
                    ExpandList.setAdapter(ExpAdapter);
                }
                if (msg.getData().getString("type").compareTo("img_download") == 0){
                    ExpAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    public ArrayList<ExpandListGroup> SetStandardGroups() {
        ArrayList<ExpandListGroup> gru_list = new ArrayList<ExpandListGroup>();
        ArrayList<ArrayList<MenuItem>> child_list = new ArrayList<ArrayList<MenuItem>>();

        int categoryItr = 0;
        ArrayList<MenuCategory> catArray = myDao.getMenuList();//myDao.getCategoryArray(restaurant.Restaurant_ID);
        while (categoryItr < catArray.size()) {
            ExpandListGroup group = new ExpandListGroup();
            group.setName(catArray.get(categoryItr).getDescription());
            gru_list.add(group);

            ArrayList<MenuItem> temp_child_list = new ArrayList<MenuItem>();
            int itemItr = 0;
            ArrayList<MenuItem> itemArray = myDao.getMenuItemArray(restaurant.Restaurant_ID, catArray.get(categoryItr).getCategoryId());
            while (itemItr < itemArray.size()) {
                MenuItem tempItem = itemArray.get(itemItr);
                MenuItem child = new MenuItem(itemArray.get(itemItr));

                temp_child_list.add(child);
                itemItr++;
            }
            group.setItems(temp_child_list);
            child_list.add(temp_child_list);
            categoryItr++;
        }

        Button but = (Button) findViewById(R.id.menuOrderButton);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intend = new Intent(getApplicationContext(), OrderActivity.class);
                intend.putExtra("RestaurantId",(Integer)restaurant.Restaurant_ID);
                startActivity(intend);
            }
        });
        return gru_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

}