package pl.byd.promand.Team3.infrastructure.data;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

public class Order {

    public Order(){
        //ToDo reservationId generated from DAO class
        reservationId = 1;
    }

    public void addItemToOrder(int menuItemId) {setQuantity(menuItemId,1);}

    public Pair<Integer, Integer> getItemOrder(int menuItemId) {
        for (int i = 0; i < items.size(); i++){
            if (items.get(i).first == menuItemId) return items.get(i);
        }
        return new Pair<Integer, Integer>(0, 0);
    }

    public int getQuantity(int menuItemId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).first == menuItemId)
                return items.get(i).second;
        }
        return 0;
    }

    public void setQuantity(int menuItemId,int quantity){
        for (int i = 0; i < items.size(); i++){
            if (items.get(i).first == menuItemId)
                items.remove(i);
        }

        Log.d("MyDebug","Item " + menuItemId + " Quantity  = " + quantity );

        items.add(new Pair<Integer, Integer>(menuItemId, quantity));

    }

    public boolean addQuantity(int menuItemId){
        for (int i = 0; i < items.size(); i++){
            if(items.get(i).first == menuItemId) {
                setQuantity(menuItemId,items.get(i).second + 1);
                return true;
            }
        }
        return false;
    }

    public int getReservationId(){return reservationId;}

    private int reservationId;
    private ArrayList<Pair<Integer, Integer>> items = new ArrayList<Pair<Integer, Integer>>();
}
