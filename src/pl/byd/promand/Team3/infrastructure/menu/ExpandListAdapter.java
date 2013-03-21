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

        //all Views
        TextView tv = (TextView) view.findViewById(R.id.textViewChild);
        TextView itemDesc = (TextView) view.findViewById(R.id.TVmenuFoodDescription);
        TextView itemIng = (TextView) view.findViewById(R.id.TVmenuFoodIngredients);
        TextView count = (TextView) view.findViewById(R.id.TVcountOrder);
        TextView itemPrice = (TextView) view.findViewById(R.id.TVmenuPrice);
        TextView itemPrep = (TextView) view.findViewById(R.id.TVmenuPreparation);
        ImageView itemImg = (ImageView)view.findViewById(R.id.IMGmenuItem);
        ImageButton plus = (ImageButton)view.findViewById(R.id.orderBtnPlus);
        ImageButton minus = (ImageButton)view.findViewById(R.id.orderBtnMinus);
        TextView descriptionit = (TextView)view.findViewById(R.id.descriptionit);
        TextView ingradientit = (TextView)view.findViewById(R.id.ingradientit);
        TextView priceit = (TextView)view.findViewById(R.id.priceit);

        //set data
        tv.setTag(child.getId());
        plus.setTag(R.id.TAG_VIEW,(Integer)child.getId());
        minus.setTag(R.id.TAG_VIEW,(Integer)child.getId());
        tv.setText(child.getName());
        itemDesc.setText(menuItem.description);
        itemIng.setText(child.ingredients);
        itemPrice.setText(Double.valueOf(child.price).toString());
        itemPrep.setText(child.getPreparationTime());
        itemImg.setImageBitmap(ImgMgr.getInstance().getBitmap(menuItem.path_to_img));


        //visibility (dynamic panel)
        if(child.getCollapsed() == true){
            itemDesc.setVisibility(View.GONE);
            itemIng.setVisibility(View.GONE);
            itemPrice.setVisibility(View.GONE);
            itemPrep.setVisibility(View.GONE);
            itemImg.setVisibility(View.GONE);
            descriptionit.setVisibility(View.GONE);
            ingradientit.setVisibility(View.GONE);
            priceit.setVisibility(View.GONE);
        } else {
            itemDesc.setVisibility(View.VISIBLE);
            itemIng.setVisibility(View.VISIBLE);
            itemPrice.setVisibility(View.VISIBLE);
            itemPrep.setVisibility(View.VISIBLE);
            itemImg.setVisibility(View.VISIBLE);
            descriptionit.setVisibility(View.VISIBLE);
            ingradientit.setVisibility(View.VISIBLE);
            priceit.setVisibility(View.VISIBLE);
        }

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
                    copyView.findViewById(R.id.descriptionit).setVisibility(View.VISIBLE);
                    copyView.findViewById(R.id.ingradientit).setVisibility(View.VISIBLE);
                    copyView.findViewById(R.id.priceit).setVisibility(View.VISIBLE);

                    child.setCollapsed(false);
                } else {
                    copyView.findViewById(R.id.TVmenuFoodDescription).setVisibility(View.GONE);
                    copyView.findViewById(R.id.TVmenuFoodIngredients).setVisibility(View.GONE);
                    copyView.findViewById(R.id.IMGmenuItem).setVisibility(View.GONE);
                    copyView.findViewById(R.id.TVmenuPrice).setVisibility(View.GONE);
                    copyView.findViewById(R.id.TVmenuPreparation).setVisibility(View.GONE);
                    copyView.findViewById(R.id.descriptionit).setVisibility(View.GONE);
                    copyView.findViewById(R.id.ingradientit).setVisibility(View.GONE);
                    copyView.findViewById(R.id.priceit).setVisibility(View.GONE);

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