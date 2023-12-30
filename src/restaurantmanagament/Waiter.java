package restaurantmanagament;

import java.util.logging.Level;
import java.util.logging.Logger;
import static restaurantmanagament.RestaurantManagament.tables;
import static restaurantmanagament.RestaurantManagament.waiters;

public class Waiter implements Runnable {

    private int id;
    private String name;
    private Customer customer = null;

    public Waiter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            siparisleriAl();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.customer = null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    //garsonların dolu masalara gidip sipariş aldığı fonksiyon
    private void siparisleriAl() {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getCustomer() != null) {
                for (int j = 0; j < waiters.size(); j++) {
                    if (waiters.get(j).getCustomer() == null) {
                        waiters.get(j).setCustomer(tables.get(i).getCustomer());
                        tables.get(i).setOrderStatus(1);
                        break;
                    }
                }
            }
        }
    }

}
