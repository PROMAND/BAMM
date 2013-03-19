package pl.byd.promand.Team3.infrastructure.data;

import android.util.Log;
import java.util.ArrayList;

public class MyDAO {
    /**********************************/
    /* Reservation Data               */
    /**********************************/

    public Order getNewOrder(int restaurantId){
        return new Order(getNewReservationId(),restaurantId);
    }

    public int getNewReservationId(){
               return ++currentReservationId;
    }

    public Order getOrder(int restaurantId){
         for(int i=0;i < orderArray.size();i++){
            if( orderArray.get(i).getRestaurantId() == restaurantId){
                return orderArray.get(i);
            }
         }
        return getNewOrder(restaurantId);
    }

    public int addToOrder(int restaurantId,int itemId){
        Order order = getOrder(restaurantId);
        if (order == null)
            order = getNewOrder(restaurantId);

        order.addQuantity(itemId);
        Log.d("MyDebug","Addtoorder end dao "+ order.getQuantity(itemId));
        return order.getQuantity(itemId);
    }

    private ArrayList<Order> orderArray = new ArrayList<Order>();
    private int currentReservationId=0;

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

    /*public ArrayList<MenuItem> getMenuItemArray(int restaurantId,int categoryId){
        ArrayList<MenuItem> tempArray = new ArrayList<MenuItem>();
        //todo db support
        for(int i =0;i < 20;i++){
            MenuItem temp = new MenuItem();
            temp.ingredients = " - ingredients /n-ingredients /n-ingredients /n-ingredients /n-ingredients/n";
            temp.menuId = categoryId; //wtf ? rename it ?
            temp.menuItemId = restaurantId * 1000 + categoryId * 100 + i;
            temp.description = "orem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac nibh imperdiet metus convallis egestas. Nullam mi eros, tempor quis fermentum sed, fermentum nec augue.";
            temp.name = "Food Name " + i;
            temp.restaurantId = restaurantId;
            tempArray.add(temp);
        }

        return tempArray;
    }

    public MenuItem getMenuItem(int itemId){
        MenuItem temp = new MenuItem();
        temp.ingredients = " - ingredients /n-ingredients /n-ingredients /n-ingredients /n-ingredients/n";
        temp.menuId = 1; //wtf ? rename it ?
        temp.menuItemId = itemId;
        temp.name = "Food Name";
        temp.restaurantId = 1;
        temp.description = "orem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac nibh imperdiet metus convallis egestas. Nullam mi eros, tempor quis fermentum sed, fermentum nec augue.";
        return temp;
    }   */

    private static MyDAO ourInstance = new MyDAO();

    public static MyDAO getInstance() {
        return ourInstance;
    }

    private MyDAO() {
        currentReservationId = 0; //ToDo import from DB
        currentMenuItemId = 0;
    }

    public void downloadMenuItems(Integer id){
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("menu_item","restaurantId",id.toString());
    }

    public void downloadRestaurant(){
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("restaurant");
    }

    public ArrayList<Restaurant> getRestaurantArray(){
        if(!restaurantArray.isEmpty())
            return restaurantArray;

        return null;
    }

    public Restaurant getRestaurant(int Restaurant_ID){
        for(int i=0;i < restaurantArray.size();i++){
            if(restaurantArray.get(i).Restaurant_ID == Restaurant_ID){
                return  restaurantArray.get(i);
            }
        }
        return null;
    }

    public ArrayList<MenuItem> getMenuItemArray(int restaurantId){
        for(int i = 0;i < menuItem.size();i++){
            if(menuItem.get(i).get(0).restaurantId == restaurantId){
                return menuItem.get(i);
            }
        }
        return null;
    }

    public ArrayList<MenuItem> getMenuItemArray(int restaurantId, int categoryId){
        ArrayList<MenuItem> menuItemArray = getMenuItemArray(restaurantId);
        ArrayList<MenuItem> tempMenuItemArray = new ArrayList<MenuItem>();

        for(int i = 0;i < menuItemArray.size();i++){
            if(menuItemArray.get(i).menuId == categoryId){
                tempMenuItemArray.add(menuItemArray.get(i));
            }
        }
        return tempMenuItemArray;
    }

    public void setMenuItemArray(ArrayList<MenuItem> itemArray){
        if(itemArray == null || itemArray.isEmpty())
            return;

        int restaurantId = itemArray.get(0).restaurantId;
        for(int i = 0;i < menuItem.size();i++){
            if(menuItem.get(i).get(0).restaurantId == restaurantId){
                menuItem.remove(i);
            }
        }
        menuItem.add(itemArray);
    }

    public ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
    public ArrayList<ArrayList<MenuItem>> menuItem = new ArrayList<ArrayList<MenuItem>>();

    public String file;
}
