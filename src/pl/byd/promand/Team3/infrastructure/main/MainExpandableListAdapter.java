package pl.byd.promand.Team3.infrastructure.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import pl.byd.promand.Team3.R;
import java.util.List;

public class MainExpandableListAdapter extends BaseExpandableListAdapter {

    private List<String> groupTitleList;
    private List<MenuItemDetailsBean> groupList;
    private Context context;

    public MainExpandableListAdapter(List<String> groupTitleList, List<MenuItemDetailsBean> groupList, Context context) {
        this.groupTitleList = groupTitleList;
        this.groupList = groupList;
        this.context = context;
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

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
