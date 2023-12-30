package restaurantmanagament;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class RestaurantManagament {

    public static Queue<Customer> customerQueue = new LinkedList<>();    // müşteri sırası için bir kuyruk
    public static Queue<Customer> priorityCustomerQueue = new LinkedList<>(); // öncelikli müşteri sırası için bir kuyruk
    public static List<Table> tables = new ArrayList<>();        // boş masalardan oluşan bir liste
    public static List<Waiter> waiters = new ArrayList<>();
    public static List<Chef> chefs = new ArrayList<>();
    public static Queue<Customer> siparisVerenMusteriler = new LinkedList<>();
    private static int customerId = 1; // RestaurantManagament sınıfında tanımlanan bir değişken

    public static void main(String[] args) {

        initializeRestaurant();

        while (true) {
            simulateStep();
            try {
                Thread.sleep(5000); // 5 saniyede bir adım at
                System.out.println("                                                      ");
                System.out.println("                                                      ");
                System.out.println("                                                      ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static void initializeRestaurant() {
        Table table1 = new Table(1, "table1");
        Table table2 = new Table(2, "table2");
        Table table3 = new Table(3, "table3");
        Table table4 = new Table(4, "table4");
        Table table5 = new Table(5, "table5");
        Table table6 = new Table(6, "table6");
        tables.add(table1);
        tables.add(table2);
        tables.add(table3);
        tables.add(table4);
        tables.add(table5);
        tables.add(table6);

        Waiter waiter1 = new Waiter(1, "waiter1");
        Waiter waiter2 = new Waiter(2, "waiter2");
        Waiter waiter3 = new Waiter(3, "waiter3");
        waiters.add(waiter1);
        waiters.add(waiter2);
        waiters.add(waiter3);

        Chef chef1 = new Chef(1, "chef1");
        Chef chef2 = new Chef(2, "chef2");
        chefs.add(chef1);
        chefs.add(chef2);

        Cashier cashier1 = new Cashier(1, "cashier1");
    }

    private static void simulateStep() {
        createCustomers();
        System.out.println("Adım 1 : " + (priorityCustomerQueue.size() + customerQueue.size()) + " müşteri geldi."
                + " Öncelikli müşteri sayısı : " + priorityCustomerQueue.size());

        sitCustomers();
        int doluMasa = 0;
        //masalarda kim oturuyor ? 
        for (Table table : tables) {
            if (table.getCustomer() != null) {
                doluMasa++;
                System.out.println("Table " + table.getId() + ": " + table.getCustomer().getName() + " is sitting.");
            } else {
                System.out.println("Table " + table.getId() + ": boş");
            }
        }
        System.out.println("Adım 2 : " + doluMasa + " müşteri masalara yerleştirildi. " + (priorityCustomerQueue.size() + customerQueue.size())
                + " müşteri beklemede.");

        System.out.println("---------------------------------------------------------------------");
        //garsonların müşterilerini dolaşalım.
        //siparisleriAl();
        // Garson thread'lerini başlat
        for (Waiter waiter : waiters) {
            Thread waiterThread = new Thread(waiter);
            waiterThread.start();
        }
/*
        System.out.println("Adım 3 : ve Adım 4 :");
        for (int i = 0; i < waiters.size(); i++) {
            if (waiters.get(i).getCustomer() != null) {
                System.out.println(waiters.get(i).getName() + "'in müşterisi : " + waiters.get(i).getCustomer().getName() + ", siparişini aşçıya iletti.");
            }
        }

        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getCustomer() != null) {
                if (tables.get(i).getOrderStatus() == 0) {
                    System.out.println(tables.get(i).getCustomer().getName() + " sipariş için beklemede");
                }
            }
        }*/

        System.out.println("----------------------------------------------------------------------");
        System.out.println("Adım 5 : ");

        // Aşçı thread'lerini başlat
        for (Chef chef : chefs) {
            Thread chefThread = new Thread(chef);
            chefThread.start();
        }

        /*for (int i = 0; i < chefs.size(); i++) {
            System.out.println(chefs.get(i).getName() + "'in müşterileri : " + chefs.get(i).getCustomers().toString());
        }*/
    }

    //bir seferde en fazla 10 müşteri oluşturan fonksiyon.
    private static void createCustomers() {

        Random random = new Random();
        // rastgele müşteri sayısı üret    max:10
        int numberOfCustomers = random.nextInt(10) + 1;

        for (int i = 0; i < numberOfCustomers; i++) {

            // rastgele müşteri
            Customer customer = new Customer(customerId, "Customer" + customerId, random.nextBoolean());

            // Müşteri thread'ini başlatın
            Thread customerThread = new Thread(customer);
            customerThread.start();

            //müşterileri kendi kuyruklarına ekledik.
            if (customer.isPriority()) {
                addPriorityCustomerToQueue(customer);
            } else {
                addCustomerToQueue(customer);
            }
            customerId++;
        }
    }

    //müşterileri kuyruğa ekledik.
    private static void addCustomerToQueue(Customer customer) {
        customerQueue.add(customer);
        System.out.println(customer.getName() + " joined the queue.");
    }

    //öncelikli müşterileri öncelikli kuyruğa ekledik.
    private static void addPriorityCustomerToQueue(Customer customer) {
        priorityCustomerQueue.add(customer);
        System.out.println(customer.getName() + " joined the priority queue.");
    }

    //müşteriler kuyruğa göre masalara yerleştirildi.
    private static void sitCustomers() {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getCustomer() == null) {
                if (!priorityCustomerQueue.isEmpty()) {
                    tables.get(i).setCustomer(priorityCustomerQueue.poll());
                    tables.get(i).getCustomer().setOturduMu(true);
                } else if (!customerQueue.isEmpty()) {
                    tables.get(i).setCustomer(customerQueue.poll());
                    tables.get(i).getCustomer().setOturduMu(true);
                }
            }
        }
    }

}
