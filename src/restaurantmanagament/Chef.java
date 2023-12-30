package restaurantmanagament;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import static restaurantmanagament.RestaurantManagament.chefs;
import static restaurantmanagament.RestaurantManagament.waiters;

public class Chef implements Runnable {

    private int id;
    private String name;
    private List<Customer> customers;

    public Chef(int id, String name) {
        this.id = id;
        this.name = name;
        this.customers = new ArrayList<>(2);
    }

    @Override
    public void run() {
        while (true) {
            asciyaGonder();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.customers = null;
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

    public List<Customer> getCustomers() {
        if (customers == null) {
            customers = new ArrayList<>();
        }
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomertoChef(Customer customer) {
        if (customers.size() < 2) {
            customers.add(customer);
        } else {
            System.out.println("Chef " + name + " can't take more than 2 orders simultaneously.");
        }
    }

    //garsonlar sipariş aldıktan sonra aşçıya ilettiği fonksiyon
    private static void asciyaGonder() {
        for (int i = 0; i < waiters.size(); i++) {
            if (waiters.get(i).getCustomer() != null) {
                for (int j = 0; j < chefs.size(); j++) {
                    List<Customer> chefCustomers = chefs.get(j).getCustomers();
                    if (chefCustomers != null && chefs.get(j).getCustomers().size() < 2) {
                        chefs.get(j).addCustomertoChef(waiters.get(i).getCustomer());
                        break;
                    }
                }
            }
        }
    }

}
