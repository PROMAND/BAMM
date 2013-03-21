package pl.byd.promand.Team3.infrastructure.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDAO {
    /**********************************/
    /* Reservation Data               */

    /**
     * ******************************
     */
    private ArrayList<MenuCategory> menuList = new ArrayList<MenuCategory>();
    private ArrayList<Customer> customerArrayList = new ArrayList<Customer>();
    private ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
    private ArrayList<RestaurantTime> restaurantTimes = new ArrayList<RestaurantTime>();

    public ArrayList<MenuCategory> getMenuList() {
        if (menuList != null) {
            return menuList;
        }
        return new ArrayList<MenuCategory>();
    }

    public void setMenuList(ArrayList<MenuCategory> menuList) {
        this.menuList = menuList;
    }


    public void setRestaurantTimes(ArrayList<RestaurantTime> restaurantTimes) {
        this.restaurantTimes = restaurantTimes;
    }
    private ArrayList<Order> orderArray = new ArrayList<Order>();
    private int currentReservationId = 0;

    /**********************************/
    /* menuItem data                  */
    /**
     * ******************************
     */
    private int currentMenuItemId;

    private static MyDAO ourInstance = new MyDAO();

    public static MyDAO getInstance() {
        return ourInstance;
    }

    private MyDAO() {
        currentReservationId = 0; //ToDo import from DB
        currentMenuItemId = 0;
    }

    public void setReservationArrayList(ArrayList<Reservation> reservationArrayList) {
        this.reservationArrayList = reservationArrayList;
    }

    public void setCustomerArrayList(ArrayList<Customer> customerArrayList) {
        this.customerArrayList = customerArrayList;
    }

    public void downloadMenuItems(Integer id) {
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("menu_item", "restaurantId", id.toString());
    }

    public void downloadRestaurant() {
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("restaurant");
    }

   public void downloadCustomer() {
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("customer");
    }

    public void downloadOrders() {
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("orders");
    }

    public void downloadMenu() {
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("menu");
    }

    public void downloadReservation() {
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("reservation");
    }

    public void downloadRestaurantTime() {
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("restaurant_time");
    }

    public Customer getCustomer(){
        return null;
    }

    public Reservation getReservation(){
        return null;
    }

    public ArrayList<Restaurant> getRestaurantArray() {
        if (!restaurantArray.isEmpty())
            return restaurantArray;

        return null;
    }

    public Restaurant getRestaurant(int Restaurant_ID) {
        for (int i = 0; i < restaurantArray.size(); i++) {
            if (restaurantArray.get(i).Restaurant_ID == Restaurant_ID) {
                return restaurantArray.get(i);
            }
        }
        return null;
    }

    public ArrayList<MenuItem> getMenuItemArray(int restaurantId) {
        for (int i = 0; i < menuItem.size(); i++) {
            if (menuItem.get(i).get(0).restaurantId == restaurantId) {
                return menuItem.get(i);
            }
        }
        return null;
    }

    public ArrayList<MenuItem> getMenuItemArray(int restaurantId, int categoryId) {
        ArrayList<MenuItem> menuItemArray = getMenuItemArray(restaurantId);
        ArrayList<MenuItem> tempMenuItemArray = new ArrayList<MenuItem>();

        for (int i = 0; i < menuItemArray.size(); i++) {
            if (menuItemArray.get(i).menuId == categoryId) {
                tempMenuItemArray.add(menuItemArray.get(i));
            }
        }
        return tempMenuItemArray;
    }

    public void setMenuItemArray(ArrayList<MenuItem> itemArray) {
        if (itemArray == null || itemArray.isEmpty())
            return;

        int restaurantId = itemArray.get(0).restaurantId;
        for (int i = 0; i < menuItem.size(); i++) {
            if (menuItem.get(i).get(0).restaurantId == restaurantId) {
                menuItem.remove(i);
            }
        }
        menuItem.add(itemArray);
    }

    public ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
    public ArrayList<ArrayList<MenuItem>> menuItem = new ArrayList<ArrayList<MenuItem>>();

    public String file;
}
