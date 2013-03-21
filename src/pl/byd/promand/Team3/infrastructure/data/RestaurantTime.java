package pl.byd.promand.Team3.infrastructure.data;

import java.util.Date;

public class RestaurantTime {
    private int restaurantTimeId;
    private Date openFrom;
    private Date openUntil;
    private int weekDay;
    private int restaurantId;

    public int getRestaurantTimeId() {
        return restaurantTimeId;
    }

    public void setRestaurantTimeId(int restaurantTimeId) {
        this.restaurantTimeId = restaurantTimeId;
    }

    public Date getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(Date openFrom) {
        this.openFrom = openFrom;
    }

    public Date getOpenUntil() {
        return openUntil;
    }

    public void setOpenUntil(Date openUntil) {
        this.openUntil = openUntil;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
