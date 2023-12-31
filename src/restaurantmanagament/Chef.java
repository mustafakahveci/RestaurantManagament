package restaurantmanagament;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import static restaurantmanagament.RestaurantManagament.*;

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
            try {
                asciyaGonder();
                siparisleriTamamla();
            } catch (InterruptedException ex) {
                Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    private static void asciyaGonder() throws InterruptedException {
        synchronized (siparisVerenMusteriler) {
            int chefsSize = chefs.size();
            for (int i = 0; i < chefsSize; i++) {
                Chef chef = chefs.get(i);
                if (chef.getCustomers().size() < 2) {
                    Customer customer = siparisVerenMusteriler.poll();
                    if (customer != null) {
                        synchronized (customer) {
                            chef.addCustomertoChef(customer);
                            System.out.println(chef.getName() + " siparişini hazırlamaya başladı: " + customer);
                            customer.setOrderStatus(2);
                            if (chef.getId() == 1) {
                                chef1Orders.add(customer);
                            } else if (chef.getId() == 2) {
                                chef2Orders.add(customer);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private void siparisleriTamamla() throws InterruptedException {
        for (int i = 0; i < chefs.size(); i++) {
            Chef chef = chefs.get(i);
            synchronized (chef.getLock()) {
                if (chef.getId() == 1) {
                    if (!chef1Orders.isEmpty()) {
                        Customer customer = chef1Orders.poll();
                        if (chef.getCustomers().contains(customer)) {
                            Thread.sleep(3000);
                            chef.getCustomers().remove(customer);
                            System.out.println(customer + " 'ın siparişi hazır...");
                            customer.setOrderStatus(3);
                            customer.eating();
                        }
                    }
                } else if (chef.getId() == 2) {
                    if (!chef2Orders.isEmpty()) {
                        Customer customer = chef2Orders.poll();
                        if (chef.getCustomers().contains(customer)) {
                            Thread.sleep(3000);
                            chef.getCustomers().remove(customer);
                            System.out.println(customer + " 'ın siparişi hazır...");
                            customer.setOrderStatus(3);
                            customer.eating();
                        }
                    }
                }
            }
        }
    }

    private final Object lock = new Object();

    public Object getLock() {
        return lock;
    }
}
