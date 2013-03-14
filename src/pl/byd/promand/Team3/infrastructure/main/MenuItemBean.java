package pl.byd.promand.Team3.infrastructure.main;

/**
 * Created with IntelliJ IDEA.
 * User: Tosha
 * Date: 14.03.13
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
public class MenuItemBean {
    private String hotMeals;
    private String deserts;
    private String drinks;

    public MenuItemBean(String hotMeals, String deserts, String drinks) {
        this.hotMeals = hotMeals;
        this.deserts = deserts;
        this.drinks = drinks;
    }

    public String getHotMeals() {
        return hotMeals;
    }

    public void setHotMeals(String hotMeals) {
        this.hotMeals = hotMeals;
    }

    public String getDeserts() {
        return deserts;
    }

    public void setDeserts(String deserts) {
        this.deserts = deserts;
    }

    public String getDrinks() {
        return drinks;
    }

    public void setDrinks(String drinks) {
        this.drinks = drinks;
    }
}
