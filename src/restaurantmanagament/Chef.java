package restaurantmanagament;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
                //siparisTamamla();
                /*if (getId() == 1) {
                    chef1SiparisTamamla();
                } else if (getId() == 2) {
                    chef2SiparisTamamla();
                }*/
                ortakSiparisleriTamamla();
                //Thread.sleep(3000);
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

    //garsonlar sipariş aldıktan sonra aşçıya ilettiği fonksiyon
    /* private static void asciyaGonder() {
        for (int i = 0; i < waiters.size(); i++) {
            Customer customer = waiters.get(i).getCustomer();
            if (customer != null) {
                synchronized (customer) {
                    for (int j = 0; j < chefs.size(); j++) {
                        List<Customer> chefCustomers = chefs.get(j).getCustomers();
                        if (chefCustomers != null && chefs.get(j).getCustomers().size() < 2) {
                            chefs.get(j).addCustomertoChef(customer);
                            System.out.println(chefs.get(j).getName()+" siparişini hazırlamaya başladı : "+customer);
                            break;
                        }
                    }
                }
            }
        }
    }*/
    private static void asciyaGonder() throws InterruptedException {
        synchronized (siparisVerenMusteriler) {
            for (int i = 0; i < siparisVerenMusteriler.size(); i++) {
                for (int j = 0; j < chefs.size(); j++) {
                    if (chefs.get(j).getCustomers().size() < 2) {
                        Customer customer = siparisVerenMusteriler.poll();
                        if (customer != null) {
                            synchronized (customer) {
                                chefs.get(j).addCustomertoChef(customer);
                                System.out.println(chefs.get(j).getName() + " siparişini hazırlamaya başladı : " + customer);
                                /*Thread.sleep(3000);
                                chefs.get(j).getCustomers().remove(customer);
                                System.out.println(customer+" 'ın siparişi hazır...");*/
                                customer.setOrderStatus(2);
                                //mutfaktakiSiparisler.add(customer);
                                if (chefs.get(i).getId() == 1) {
                                    chef1Orders.add(customer);
                                } else if (chefs.get(i).getId() == 2) {
                                    chef2Orders.add(customer);
                                }
                                break;
                            }
                        }
                    }
                }
            }

        }
    }

    private static void siparisTamamla() throws InterruptedException {

        if (!mutfaktakiSiparisler.isEmpty()) {

            Customer customer = mutfaktakiSiparisler.poll();
            for (int i = 0; i < chefs.size(); i++) {
                synchronized (chefs.get(i)) {
                    if (chefs.get(i).getCustomers().contains(customer)) {
                        Thread.sleep(3000);
                        chefs.get(i).getCustomers().remove(customer);
                        System.out.println(customer + " 'ın siparişi hazır...");
                        customer.setOrderStatus(3);
                        customer.eating();
                        break;
                    }
                }

            }
        }

    }

    private void chef1SiparisTamamla() throws InterruptedException {
        synchronized (chef1Orders) {
            if (!chef1Orders.isEmpty()) {

                Customer customer = chef1Orders.poll();

                if (this.getCustomers().contains(customer)) {
                    Thread.sleep(3000);
                    this.getCustomers().remove(customer);
                    System.out.println(customer + " 'ın siparişi hazır...");
                    customer.setOrderStatus(3);
                    customer.eating();
                }

            }
        }
    }

    private void chef2SiparisTamamla() throws InterruptedException {
        synchronized (chef2Orders) {
            if (!chef2Orders.isEmpty()) {

                Customer customer = chef2Orders.poll();

                if (this.getCustomers().contains(customer)) {
                    Thread.sleep(3000);
                    this.getCustomers().remove(customer);
                    System.out.println(customer + " 'ın siparişi hazır...");
                    customer.setOrderStatus(3);
                    customer.eating();
                }

            }
        }
    }

    private void ortakSiparisleriTamamla() throws InterruptedException {
        for (int i = 0; i < chefs.size(); i++) {
            Chef chef = chefs.get(i);
            synchronized (chef) {
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

}
