package pl.byd.promand.Team3.infrastructure.data;

public class MyDAO {

    public Order getNewOrder(){
        return new Order(getNewReservationId());
    }

    public int getNewReservationId(){
               return ++currentReservationId;
    }

    private static MyDAO ourInstance = new MyDAO();

    public static MyDAO getInstance() {
        return ourInstance;
    }


    private int currentReservationId;

    private MyDAO() {
        currentReservationId = 0; //ToDo import from DB
    }
}
