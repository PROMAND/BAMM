package pl.byd.promand.Team3.infrastructure.main;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.data.ImgMgr;
import pl.byd.promand.Team3.infrastructure.data.MyDAO;
import pl.byd.promand.Team3.infrastructure.data.Restaurant;

public class MainExpandableListAdapter extends BaseExpandableListAdapter {
    private List<String> groupTitleList;
    private List<MenuItemDetailsBean> groupList;
    private Context context;
    private ArrayList<Pair<Integer, Integer>> resIdGroupList;

    public MainExpandableListAdapter(List<String> groupTitleList, List<MenuItemDetailsBean> groupList, Context context,
                                     ArrayList<Pair<Integer, Integer>> resIdGroupList) {
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

    public Integer getIdGroup(Integer id) {
        for (int i = 0; i < resIdGroupList.size(); i++) {
            if (resIdGroupList.get(i).first == id) {
                return resIdGroupList.get(i).second;
            }
        }
        return null;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String group = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.main_list_group, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.TVmainGroup);
        textView.setText(group);

        Restaurant restaurant = MyDAO.getInstance().getRestaurant(getIdGroup(groupPosition));

        textView = (TextView) convertView.findViewById(R.id.TVmainShortDesc);
        textView.setText(restaurant.Desc_short);

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

        Restaurant restaurant = MyDAO.getInstance().getRestaurant(getIdGroup(groupPosition));
        ImageView imgView = (ImageView)convertView.findViewById(R.id.mainMenuImg);
        imgView.setImageBitmap(ImgMgr.getInstance().getBitmap(restaurant.Path_to_img));

        TextView textView = (TextView) convertView.findViewById(R.id.textViewItemDetails);
        textView.setText(restaurant.Desc_long);
        convertView.setTag(R.id.TAG_VIEW, getIdGroup(groupPosition));



        textView = (TextView) convertView.findViewById(R.id.TVmainCellNr);
        textView.setText(restaurant.Contact_number);

        textView = (TextView) convertView.findViewById(R.id.TVmainEmail);
        textView.setText(restaurant.Contact_email);

        textView = (TextView) convertView.findViewById(R.id.TVmainSitsMaxCurr);
        textView.setText(restaurant.Sits_current + "/" + restaurant.Sits_max);

        return convertView;
    }

    public void update(){
        this.notifyDataSetChanged();
        Log.d("MyDebug","notifyDataSetChanged");
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
