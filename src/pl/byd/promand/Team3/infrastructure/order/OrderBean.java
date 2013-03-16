package pl.byd.promand.Team3.infrastructure.order;

import java.util.List;

public class OrderBean {
    private List<String> orders;

    public OrderBean(String name, String phone, List<String> orders) {
        this.orders = orders;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
