package pl.byd.promand.Team3.infrastructure.order;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.data.MyDAO;

import java.util.List;

public class OrderAdapter extends ArrayAdapter {

    List<Pair<String, Integer>> objects;

    public OrderAdapter(Context context, List<Pair<String, Integer>> objects) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.order_listview_layout, null);
            TextView itemId = (TextView) convertView.findViewById(R.id.orderItemID_TV);
            itemId.setText(objects.get(position).first);
            TextView itemQuantity = (TextView) convertView.findViewById(R.id.orderItemQuantity_TV);

            //MyDAO.getInstance().getMenuItemArray()

            itemQuantity.setText(String.valueOf(objects.get(position).second));
            itemQuantity.setPadding(10, 0, 0, 0);

        }

        return convertView;
    }
}
