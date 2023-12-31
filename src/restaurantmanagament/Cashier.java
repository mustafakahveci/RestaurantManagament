package restaurantmanagament;

import java.util.logging.Level;
import java.util.logging.Logger;
import static restaurantmanagament.RestaurantManagament.*;

public class Cashier implements Runnable {

    private int id;
    private String name;

    public Cashier(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            hesapAl();
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cashier.class.getName()).log(Level.SEVERE, null, ex);
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

    private void hesapAl() {
        if (!kasaKuyrugu.isEmpty()) {
            Customer customer = kasaKuyrugu.poll();
            synchronized (customer) {
                System.out.println(getName() + "'a hesabını ödüyor... : " + customer.getName());
                customer.setOrderStatus(5);
                masayiBosalt(customer);
            }
        }
    }

    private void masayiBosalt(Customer customer) {
        if (customer.getTable() != null) {
            for (int i = 0; i < tables.size(); i++) {
                if (customer.getTable() == tables.get(i)) {
                    tables.get(i).setCustomer(null);
                }
            }
        }
    }

}
