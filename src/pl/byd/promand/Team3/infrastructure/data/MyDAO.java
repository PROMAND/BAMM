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

    public ArrayList<MenuCategory> getMenuList() {
        if (menuList != null) {
            return menuList;
        }
        return new ArrayList<MenuCategory>();
    }

    public void setMenuList(ArrayList<MenuCategory> menuList) {
        this.menuList = menuList;
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

    public void downloadMenuItems(Integer id) {
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("menu_item", "restaurantId", id.toString());
    }

    public void downloadRestaurant() {
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("restaurant");
    }

    public void downloadMenu() {
        DownloadJasonFile down = new DownloadJasonFile();
        down.execute("menu");
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
