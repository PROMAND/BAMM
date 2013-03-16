package pl.byd.promand.Team3.presentation.menu;


import java.io.DataOutput;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import com.actionbarsherlock.app.SherlockActivity;
import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.data.MenuCategory;
import pl.byd.promand.Team3.infrastructure.data.MenuItem;
import pl.byd.promand.Team3.infrastructure.data.MyDAO;
import pl.byd.promand.Team3.infrastructure.menu.ExpandListAdapter;
import pl.byd.promand.Team3.infrastructure.menu.ExpandListGroup;
import pl.byd.promand.Team3.presentation.order.OrderActivity;

public class MenuActivity extends SherlockActivity {
    /** Called when the activity is first created. */
    private ExpandListAdapter ExpAdapter;
    private ArrayList<ExpandListGroup> ExpListItems;
    private ExpandableListView ExpandList;
    private MyDAO myDao;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        myDao = MyDAO.getInstance();

        ExpandList = (ExpandableListView) findViewById(R.id.menuExpandableListView);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapter(MenuActivity.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
    }

    public ArrayList<ExpandListGroup> SetStandardGroups() {

        ArrayList<ExpandListGroup> gru_list = new ArrayList<ExpandListGroup>();
        ArrayList<ArrayList<MenuItem>> child_list = new ArrayList<ArrayList<MenuItem>>();

        int categoryItr = 0;
        ArrayList<MenuCategory> catArray = myDao.getCategoryArray(1);
        while(categoryItr < catArray.size()){
            ExpandListGroup group = new ExpandListGroup();
            group.setName(catArray.get(categoryItr).categoryName);
            gru_list.add(group);

            ArrayList<MenuItem> temp_child_list = new ArrayList<MenuItem>();
            int itemItr = 0;
            ArrayList<MenuItem> itemArray = myDao.getMenuItemArray(1, catArray.get(categoryItr).categoryId);
            while(itemItr < itemArray.size()){
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
                startActivity(intend);
            }
        });

        return gru_list;
   }

}