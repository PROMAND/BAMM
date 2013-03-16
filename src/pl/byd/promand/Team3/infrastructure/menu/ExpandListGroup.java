package pl.byd.promand.Team3.infrastructure.menu;

import pl.byd.promand.Team3.infrastructure.data.MenuItem;

import java.util.ArrayList;


public class ExpandListGroup {

    private String Name;
    private ArrayList<MenuItem> Items;

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public ArrayList<MenuItem> getItems() {
        return Items;
    }
    public void setItems(ArrayList<MenuItem> Items) {
        this.Items = Items;
    }
}


