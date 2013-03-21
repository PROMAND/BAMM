package pl.byd.promand.Team3.infrastructure.menu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.data.*;

import java.util.ArrayList;

public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ExpandListGroup> groups;

    public ExpandListAdapter(Context context, ArrayList<ExpandListGroup> groups) {
        this.context = context;
        this.groups = groups;
    }

    public void addItem(MenuItem item, ExpandListGroup group) {
        if (!groups.contains(group)) {
            groups.add(group);
        }
        int index = groups.indexOf(group);
        ArrayList<MenuItem> ch = groups.get(index).getItems();
        ch.add(item);
        groups.get(index).setItems(ch);
    }

    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<MenuItem> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        final MenuItem child = (MenuItem) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.menu_list_child, null);
        }

        MenuItem menuItem = MyDAO.getInstance().getMenuItem(child.getId());
        TextView tv = (TextView) view.findViewById(R.id.textViewChild);

        tv.setText(child.getName());
        tv.setTag(child.getId());

        TextView itemDesc = (TextView) view.findViewById(R.id.TVmenuFoodDescription);
        TextView itemIng = (TextView) view.findViewById(R.id.TVmenuFoodIngredients);

        ImageButton plus = (ImageButton)view.findViewById(R.id.orderBtnPlus);
        ImageButton minus = (ImageButton)view.findViewById(R.id.orderBtnMinus);

        plus.setTag(R.id.TAG_VIEW,(Integer)child.getId());
        minus.setTag(R.id.TAG_VIEW,(Integer)child.getId());

        TextView count = (TextView) view.findViewById(R.id.TVcountOrder);
        count.setText("" + GlobalState.getInstance().getOrder(child.getId()));

        ImageView itemImg = (ImageView)view.findViewById(R.id.IMGmenuItem);
        if(itemImg == null){
            Log.d("MyDebug","Itemimg jest nullem");
        }

        Log.d("MyDebug","Path"+menuItem.path_to_img);
        itemImg.setImageBitmap(ImgMgr.getInstance().getBitmap(menuItem.path_to_img));
        itemImg.setVisibility(View.GONE);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalState.getInstance().addToOrder((Integer)v.getTag(R.id.TAG_VIEW));

                TextView count = (TextView)((LinearLayout)v.getParent()).findViewById(R.id.TVcountOrder);
                count.setText("" + GlobalState.getInstance().getOrder((Integer)v.getTag(R.id.TAG_VIEW)));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalState.getInstance().dropOrder((Integer) v.getTag(R.id.TAG_VIEW));

                TextView count = (TextView)((LinearLayout)v.getParent()).findViewById(R.id.TVcountOrder);
                count.setText("" + GlobalState.getInstance().getOrder((Integer)v.getTag(R.id.TAG_VIEW)));
            }
        });

        itemDesc.setText(menuItem.description);
        itemIng.setText(child.ingredients);
        itemDesc.setVisibility(View.GONE);
        itemIng.setVisibility(View.GONE);

        TextView itemView = (TextView) view.findViewById(R.id.TVmenuPrice);
        itemView.setText(Double.valueOf(child.price).toString());
        itemView.setVisibility(View.GONE);

        itemView = (TextView) view.findViewById(R.id.TVmenuPreparation);
        itemView.setText(child.getPreparationTime());
        itemView.setVisibility(View.GONE);

        //TODO: null pointer when activity is gone ?
        final View copyView = view;

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child.getCollapsed() == true) {
                    copyView.findViewById(R.id.TVmenuFoodDescription).setVisibility(View.VISIBLE);
                    copyView.findViewById(R.id.TVmenuFoodIngredients).setVisibility(View.VISIBLE);
                    copyView.findViewById(R.id.IMGmenuItem).setVisibility(View.VISIBLE);
                    copyView.findViewById(R.id.TVmenuPrice).setVisibility(View.VISIBLE);
                    copyView.findViewById(R.id.TVmenuPreparation).setVisibility(View.VISIBLE);

                    child.setCollapsed(false);
                } else {
                    copyView.findViewById(R.id.TVmenuFoodDescription).setVisibility(View.GONE);
                    copyView.findViewById(R.id.TVmenuFoodIngredients).setVisibility(View.GONE);
                    copyView.findViewById(R.id.IMGmenuItem).setVisibility(View.GONE);
                    copyView.findViewById(R.id.TVmenuPrice).setVisibility(View.GONE);
                    copyView.findViewById(R.id.TVmenuPreparation).setVisibility(View.GONE);
                    child.setCollapsed(true);
                }

            }
        });
        return view;
    }

    public int getChildrenCount(int groupPosition) {
        ArrayList<MenuItem> chList = groups.get(groupPosition).getItems();

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