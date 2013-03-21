package pl.byd.promand.Team3.infrastructure.data;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

public class GlobalState extends Application {
    public Handler mainHandler;
    public Handler menuHandler;

    public void dropOrder(int itemId){
        for(int i=0;i < orders.size();i++){
            if (orders.get(i).first == itemId){
                if(orders.get(i).second < 2){
                    orders.remove(i);
                } else {
                    int q = orders.get(i).second;
                    orders.remove(i);
                    orders.add(new Pair<Integer,Integer>(itemId,q - 1));
                }
            }
        }
    }

    public int getOrder(int itemId) {
        for(int i=0;i < orders.size();i++){
            if (orders.get(i).first == itemId){
                return orders.get(i).second;
            }
        }
        return 0;
    }

    public void addToOrder(int itemId) {
        for(int i =0; i < orders.size();i++){
            if (orders.get(i).first == itemId){
                 int q = orders.get(i).second;
                orders.remove(i);
                orders.add(new Pair<Integer,Integer>(itemId,q +1));
                return;
            }
        }
        orders.add(new Pair<Integer,Integer>(itemId,1));
    }

    public ArrayList<Pair<Integer,Integer>> getOrderByRestaurant(int restId){
        ArrayList<Pair<Integer,Integer>> itemArray = new ArrayList<Pair<Integer, Integer>>();
        for(int i =0; i < orders.size();i++){
            int itemId = orders.get(i).first;
            if(MyDAO.getInstance().getMenuItem(itemId).restaurantId == restId){
                    itemArray.add(new Pair<Integer, Integer>(orders.get(i).first,orders.get(i).second));
            }
        }
        return itemArray;
    }

    static public GlobalState getInstance() {
        return instance;
    }

    private ArrayList<Pair<Integer,Integer>> orders = new ArrayList<Pair<Integer, Integer>>();



    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.d("MyDebug", "class GlobalState onCreate()");
    }

    static GlobalState instance;
}
