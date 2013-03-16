package pl.byd.promand.Team3.infrastructure.data;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MyDAO {
    /**********************************/
    /* Reservation Data               */
    /**********************************/
    public Order getNewOrder(){
        return new Order(getNewReservationId());
    }

    public int getNewReservationId(){
               return ++currentReservationId;
    }

    private int currentReservationId;

    /**********************************/
    /* menuItem data                  */
    /**********************************/
    private int currentMenuItemId;

    public ArrayList<MenuCategory> getCategoryArray(int restaurantId){
        ArrayList<MenuCategory> tempArray = new ArrayList<MenuCategory>();
        //todo db support
        for(int i =0;i < 20;i++){
            MenuCategory temp = new MenuCategory();
            temp.categoryId=i;
            temp.restaurantId=restaurantId;
            temp.categoryName="Category" + i;
            tempArray.add(temp);
        }
        return tempArray;
    }

    public ArrayList<MenuItem> getMenuItemArray(int restaurantId,int categoryId){
        ArrayList<MenuItem> tempArray = new ArrayList<MenuItem>();
        //todo db support
        for(int i =0;i < 20;i++){
            MenuItem temp = new MenuItem();
            temp.ingredients = " - ingredients /n-ingredients /n-ingredients /n-ingredients /n-ingredients/n";
            temp.menuId = categoryId; //wtf ? rename it ?
            temp.menuItemsId = restaurantId * 1000 + categoryId * 100 + i;
            temp.desc = "orem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac nibh imperdiet metus convallis egestas. Nullam mi eros, tempor quis fermentum sed, fermentum nec augue.";
            temp.name = "Food Name " + i;
            temp.restaurantId = restaurantId;
            tempArray.add(temp);
        }

        Log.d("MyDebug", "name:" + tempArray.get(1).getName());
        return tempArray;
    }

    public MenuItem getMenuItem(int itemId){
        MenuItem temp = new MenuItem();
        temp.ingredients = " - ingredients /n-ingredients /n-ingredients /n-ingredients /n-ingredients/n";
        temp.menuId = 1; //wtf ? rename it ?
        temp.menuItemsId = itemId;
        temp.name = "Food Name";
        temp.restaurantId = 1;
        temp.desc = "orem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac nibh imperdiet metus convallis egestas. Nullam mi eros, tempor quis fermentum sed, fermentum nec augue.";
        return temp;
    }

    private static MyDAO ourInstance = new MyDAO();

    public static MyDAO getInstance() {
        return ourInstance;
    }

    private MyDAO() {
        currentReservationId = 0; //ToDo import from DB
        currentMenuItemId = 0;
    }
}