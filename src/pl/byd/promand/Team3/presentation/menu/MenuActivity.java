package pl.byd.promand.Team3.presentation.menu;


import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ExpandableListView;
import com.actionbarsherlock.app.SherlockActivity;
import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.menu.ExpandListAdapter;
import pl.byd.promand.Team3.infrastructure.menu.ExpandListChild;
import pl.byd.promand.Team3.infrastructure.menu.ExpandListGroup;

public class MenuActivity extends SherlockActivity {
    /** Called when the activity is first created. */
    private ExpandListAdapter ExpAdapter;
    private ArrayList<ExpandListGroup> ExpListItems;
    private ExpandableListView ExpandList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        ExpandList = (ExpandableListView) findViewById(R.id.menuExpandableListView);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapter(MenuActivity.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
    }

    public ArrayList<ExpandListGroup> SetStandardGroups() {

        ArrayList<ExpandListGroup> gru_list = new ArrayList<ExpandListGroup>();
        ArrayList<ArrayList<ExpandListChild>> child_list = new ArrayList<ArrayList<ExpandListChild>>();

        for(int i = 0;i < 10;i++)
        {
            ExpandListGroup group = new ExpandListGroup();
            group.setName("Category "+(i +1));
            gru_list.add(group);

            ArrayList<ExpandListChild> temp_child_list = new ArrayList<ExpandListChild>();
            for(int j =0;j < 10;j++){
                ExpandListChild child = new ExpandListChild();
                child.setName("Food name" + (i * 10 + j));
                child.setTag(null);
                temp_child_list.add(child);

            }
            group.setItems(temp_child_list);
            child_list.add(temp_child_list);
        }

        return gru_list;
   }

}