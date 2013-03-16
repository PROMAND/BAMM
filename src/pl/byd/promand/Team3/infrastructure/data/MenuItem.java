package pl.byd.promand.Team3.infrastructure.data;

public class MenuItem {

    public int menuItemsId;
    public int restaurantId;
    public String ingredients;
    public float price;
    public int menuId;
    public String name;
    public String desc;

    private boolean Tag=false;    //false = collapsed
    private int id;

    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = Name;
    }
    public boolean getTag() {
        return Tag;
    }
    public void setTag(boolean Tag) {
        this.Tag = Tag;
    }

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
        this.Tag  = temp.Tag;
        this.id = temp.id;
    }

    public MenuItem(){}
}