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
    private int id;

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
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MenuItem(MenuItem temp) {
        this.menuItemId = temp.menuItemId;
        this.restaurantId = temp.restaurantId;
        this.ingredients = temp.ingredients;
        this.price = temp.price;
        this.menuId = temp.menuId;
        this.name = temp.name;
        this.description = temp.description;
        this.id = temp.id;
        this.collapsed = temp.collapsed;
    }

    public MenuItem() {
    }
}