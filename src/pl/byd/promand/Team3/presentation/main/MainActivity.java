package pl.byd.promand.Team3.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import com.actionbarsherlock.app.SherlockActivity;
import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.main.MainExpandableListAdapter;
import pl.byd.promand.Team3.infrastructure.main.MenuItemDetailsBean;
import pl.byd.promand.Team3.presentation.menu.MenuActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SherlockActivity {

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
        groupTitleList.add(new String("Parent1"));
        groupTitleList.add(new String("Parent2"));
        groupTitleList.add(new String("Parent3"));

        List<MenuItemDetailsBean> groupList = new ArrayList<MenuItemDetailsBean>();
        MenuItemDetailsBean childsFromParent1 = new MenuItemDetailsBean();
        childsFromParent1.add("Child1");
        childsFromParent1.add("Child2");
        childsFromParent1.add("Child3");

        MenuItemDetailsBean childsFromParent2 = new MenuItemDetailsBean();
        childsFromParent2.add("Child1");
        childsFromParent2.add("Child2");
        childsFromParent2.add("Child3");

        MenuItemDetailsBean childsFromParent3 = new MenuItemDetailsBean();
        childsFromParent3.add("Child1");
        childsFromParent3.add("Child2");
        childsFromParent3.add("Child3");

        groupList.add(childsFromParent1);
        groupList.add(childsFromParent2);
        groupList.add(childsFromParent3);

        MainExpandableListAdapter adapter = new MainExpandableListAdapter(groupTitleList, groupList, this);

        listView.setAdapter(adapter);

    }
}
