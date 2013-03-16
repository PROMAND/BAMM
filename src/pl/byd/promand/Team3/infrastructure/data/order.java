package pl.byd.promand.Team3.infrastructure.data;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

public class Order {
    //called from MyDao
    public Order(int reservationId,int restaurantId){
        this.reservationId = reservationId;
        this.restaurantId  = restaurantId;
    }

    public Order(){
        reservationId = MyDAO.getInstance().getNewReservationId();
    }

    public int getRestaurantId(){
        return restaurantId;
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
            if (items.get(i).first == menuItemId){
                return items.get(i).second;
            }
        }
        return 0;
    }

    public void setQuantity(int menuItemId,int quantity){
        for (int i = 0; i < items.size(); i++){
            if (items.get(i).first == menuItemId)
                items.remove(i);
        }

        items.add(new Pair<Integer, Integer>(menuItemId, quantity));
        Log.d("MyDebug","size in setQuantity" + items.size());

    }

    public boolean addQuantity(int menuItemId){
        Log.d("MyDebug","size " + items.size() + ":: " + menuItemId);
        for (int i = 0; i < items.size(); i++){
            Log.d("MyDebug",items.get(i).first + " " +  items.get(i).second);
            if(items.get(i).first == menuItemId) {
                setQuantity(menuItemId,items.get(i).second + 1);
                return true;
            }
        }
        Log.d("MyDebug","Nowe zamowienie");
        setQuantity(menuItemId,1);
        Log.d("MyDebug","after setquantity : " + getQuantity(menuItemId));
        return false;
    }

    public int getReservationId(){return reservationId;}

    private int restaurantId;
    private int reservationId;
    private ArrayList<Pair<Integer, Integer>> items = new ArrayList<Pair<Integer, Integer>>();
}
