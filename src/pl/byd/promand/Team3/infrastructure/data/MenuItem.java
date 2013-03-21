package pl.byd.promand.Team3.infrastructure.data;

public class MenuItem {
    public int menuItemId;
    public int menuId;
    public int restaurantId;
    public String description;
    public double price;
    public String ingredients;
    public String name;
    public String path_to_img;
    private boolean collapsed = true;

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public boolean getCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public int getId() {
        return menuItemId;
    }

    public void setId(int id) {
        this.menuItemId = id;
    }

    public MenuItem(MenuItem temp) {
        this.menuItemId = temp.menuItemId;
        this.restaurantId = temp.restaurantId;
        this.ingredients = temp.ingredients;
        this.price = temp.price;
        this.menuId = temp.menuId;
        this.name = temp.name;
        this.description = temp.description;
        this.collapsed = temp.collapsed;
        this.path_to_img = temp.path_to_img;
    }

    public MenuItem() {
    }
}