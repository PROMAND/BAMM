package pl.byd.promand.Team3.infrastructure.main;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.data.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MainExpandableListAdapter extends BaseExpandableListAdapter {

    private List<String> groupTitleList;
    private List<MenuItemDetailsBean> groupList;
    private Context context;
    private ArrayList<Pair<Integer,Integer>> resIdGroupList;

    public MainExpandableListAdapter(List<String> groupTitleList, List<MenuItemDetailsBean> groupList, Context context,
                                     ArrayList<Pair<Integer,Integer>> resIdGroupList) {
        this.groupTitleList = groupTitleList;
        this.groupList = groupList;
        this.context = context;
        this.resIdGroupList = resIdGroupList;
    }


    @Override
    public int getGroupCount() {
        return groupTitleList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int position) {
        return groupTitleList.get(position);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public Integer getIdGroup(Integer id){
        for(int i=0;i < resIdGroupList.size();i++){
            if(resIdGroupList.get(i).first == id){
                return resIdGroupList.get(i).second;
            }
        }
        Log.d("MyDebug","Nie zgadzaja sie id w mainExpandableListAdapter");
        return null;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String group = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.main_menu_item_detail, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textViewItemDetails);
        textView.setText(group);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String child = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.main_menu_item_detail, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textViewItemDetails);
        textView.setText(child);
        convertView.setTag(R.id.TAG_VIEW,getIdGroup(groupPosition));


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
