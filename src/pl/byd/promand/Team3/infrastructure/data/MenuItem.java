package pl.byd.promand.Team3.infrastructure.data;

public class MenuItem{

    public int menuItemsId;
    public int restaurantId;
    public String ingredients;
    public float price;
    public int menuId;
    public String name;
    public String desc;
    private boolean collapsed =true;
    private int id;

    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = Name;
    }

    public boolean getCollapsed()
    {return collapsed;}
    public void setCollapsed(boolean collapsed) {this.collapsed = collapsed;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //TODO: wtf
    public MenuItem(MenuItem temp){
        this.menuItemsId = temp.menuItemsId;
        this.restaurantId = temp.restaurantId;
        this.ingredients = temp.ingredients;
        this.price = temp.price;
        this.menuId = temp.menuId;
        this.name = temp.name;
        this.desc = temp.desc;
        this.id = temp.id;
        this.collapsed = temp.collapsed;
    }

    public MenuItem(){}
}