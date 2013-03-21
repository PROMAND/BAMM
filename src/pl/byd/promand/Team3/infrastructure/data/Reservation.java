package pl.byd.promand.Team3.infrastructure.data;

import java.util.Date;

public class Reservation {
    private int reservationId;
    private int restaurantId;
    private int customerId;
    private Date reservationPlaced;
    private int sitsOrdered;
    private Date reservationTime;

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getReservationPlaced() {
        return reservationPlaced;
    }

    public void setReservationPlaced(Date reservationPlaced) {
        this.reservationPlaced = reservationPlaced;
    }

    public int getSitsOrdered() {
        return sitsOrdered;
    }

    public void setSitsOrdered(int sitsOrdered) {
        this.sitsOrdered = sitsOrdered;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }
}
