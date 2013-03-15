package pl.byd.promand.Team3.infrastructure.menu;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import pl.byd.promand.Team3.R;

public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ExpandListGroup> groups;

    public ExpandListAdapter(Context context, ArrayList<ExpandListGroup> groups) {
        this.context = context;
        this.groups = groups;
    }

    public void addItem(ExpandListChild item, ExpandListGroup group) {
        if (!groups.contains(group)) {
            groups.add(group);
        }
        int index = groups.indexOf(group);
        ArrayList<ExpandListChild> ch = groups.get(index).getItems();
        ch.add(item);
        groups.get(index).setItems(ch);
    }

    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ExpandListChild> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        ExpandListChild child = (ExpandListChild) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.menu_list_child, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.textViewChild);

        tv.setText(child.getName());
        tv.setTag(child.getTag());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("layout", "food name click");

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.menu_food_description);
                dialog.setTitle(((TextView)v).getText());

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.TVmenuFoodDescription);
                text.setText("Description of food here !");

                text = (TextView) dialog.findViewById(R.id.TVmenuFoodIngredients);
                text.setText("Ingredients of food here !");

                Button dialogButton = (Button) dialog.findViewById(R.id.BmenuFoodOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        // TODO Auto-generated method stub
        return view;
    }

    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        ArrayList<ExpandListChild> chList = groups.get(groupPosition).getItems();

        return chList.size();

    }

    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    public int getGroupCount() {
        return groups.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        ExpandListGroup group = (ExpandListGroup) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.menu_list_group, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.tvGroup);
        tv.setText(group.getName());
        return view;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}