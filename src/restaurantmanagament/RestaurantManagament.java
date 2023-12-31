package restaurantmanagament;

<<<<<<< HEAD
import java.util.*;

public class RestaurantManagament {
    private static Queue<Customer> customerQueue = new LinkedList<>();
    private static Queue<Customer> priorityCustomerQueue = new LinkedList<>();
    private static List<Table> tables = new ArrayList<>();
    private static List<Waiter> waiters = new ArrayList<>();

    private static final int TABLE_COST = 1;
    private static final int WAITER_COST = 1;
    private static final int CHEF_COST = 1;
    private static final int CUSTOMER_PROFIT = 1;

    private static final int ARRIVAL_INTERVAL = 5;
    private static final int PRIORITY_CUSTOMER_RATIO = 5;
    private static final int SIMULATION_TIME = 180;

    private static final int CUSTOMER_WAITING_TIME = 20; // seconds
    private static final int ORDER_TAKING_TIME = 2; // seconds
    private static final int COOKING_TIME = 3; // seconds
    private static final int EATING_TIME = 3; // seconds
    private static final int PAYMENT_TIME = 1; // seconds
    private static int totalCustomerCount = 0;
    private static int totalPriorityCustomerCount = 0;



    public static void main(String[] args) {

        takeOrders();

=======
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
>>>>>>> 28dc2e164323e5e6e1fc5370cad0fe5f138a7de0
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
<<<<<<< HEAD

        createCustomers();
        System.out.println("Step 1: " + (priorityCustomerQueue.size() + customerQueue.size()) + " customers arrived."
                + " Priority customer count: " + priorityCustomerQueue.size());

        seatCustomers();
        int occupiedTables = 0;
=======
    }

    private static void simulateStep() {
        createCustomers();
        System.out.println("Adım 1 : " + (priorityCustomerQueue.size() + customerQueue.size()) + " müşteri geldi."
                + " Öncelikli müşteri sayısı : " + priorityCustomerQueue.size());

        sitCustomers();
        int doluMasa = 0;
        //masalarda kim oturuyor ? 
>>>>>>> 28dc2e164323e5e6e1fc5370cad0fe5f138a7de0
        for (Table table : tables) {
            if (table.getCustomer() != null) {
                occupiedTables++;
                System.out.println("Table " + table.getId() + ": " + table.getCustomer().getName() + " is sitting.");
            } else {
                System.out.println("Table " + table.getId() + ": empty");
            }
        }
<<<<<<< HEAD
        System.out.println("Step 2: " + occupiedTables + " customers seated at tables. " + (priorityCustomerQueue.size() + customerQueue.size())
                + " customers in waiting.");

        simulateRestaurantProcesses();



        int tableCount = totalCustomerCount / 4;
        int waiterCount = tableCount / 2;
        int chefCount = waiterCount / 4;

        int totalCost = (tableCount * TABLE_COST) + (waiterCount * WAITER_COST) + (chefCount * CHEF_COST);
        int totalProfit = totalCustomerCount * CUSTOMER_PROFIT;
        int profit = totalProfit - totalCost;

        System.out.println("\nTotal Customer Count: " + totalCustomerCount);
        System.out.println("Total Priority Customer Count: " + totalPriorityCustomerCount);
        System.out.println("Total Table Count: " + tableCount);
        System.out.println("Total Waiter Count: " + waiterCount);
        System.out.println("Total Chef Count: " + chefCount);
        System.out.println("Total Cost: " + totalCost);
        System.out.println("Total Profit: " + totalProfit);
        System.out.println("Profit: " + profit);
=======
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
>>>>>>> 28dc2e164323e5e6e1fc5370cad0fe5f138a7de0
    }
    private static void createCustomers() {
<<<<<<< HEAD
        Random random = new Random();
        int customerId = 1;
        int numberOfCustomers = random.nextInt(10) + 1;
=======

        Random random = new Random();
        // rastgele müşteri sayısı üret    max:10
        int numberOfCustomers = random.nextInt(10) + 1;

        for (int i = 0; i < numberOfCustomers; i++) {

            // rastgele müşteri
            Customer customer = new Customer(customerId, "Customer" + customerId, random.nextBoolean());
>>>>>>> 28dc2e164323e5e6e1fc5370cad0fe5f138a7de0

        // Ensure that we have enough tables for all customers
        if (numberOfCustomers > tables.size()) {
            System.out.println("Not enough tables for all customers.");
            return;
        }

        for (int i = 0; i < numberOfCustomers; i++) {
            Customer customer = new Customer(customerId, "Customer" + customerId, random.nextBoolean());
            Thread customerThread = new Thread(customer);
            customerThread.start();

            if (customer.isPriority()) {
                addToPriorityCustomerQueue(customer);
            } else {
                addToCustomerQueue(customer);
            }

            // Assign the customer to a table
            tables.get(i).setCustomer(customer);
            System.out.println(customer.getName() + " joined the queue and is sitting at Table " + tables.get(i).getId());

            customerId++;
        }
    }


    private static void addToCustomerQueue(Customer customer) {
        customerQueue.add(customer);
        System.out.println(customer.getName() + " joined the queue.");
    }

    private static void addToPriorityCustomerQueue(Customer customer) {
        priorityCustomerQueue.add(customer);
        System.out.println(customer.getName() + " joined the priority queue.");
    }

<<<<<<< HEAD
    private static void seatCustomers() {
=======
    //müşteriler kuyruğa göre masalara yerleştirildi.
    private static void sitCustomers() {
>>>>>>> 28dc2e164323e5e6e1fc5370cad0fe5f138a7de0
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getCustomer() == null) {
                if (!priorityCustomerQueue.isEmpty()) {
                    tables.get(i).setCustomer(priorityCustomerQueue.poll());
                    tables.get(i).getCustomer().setOturduMu(true);
                } else if (!customerQueue.isEmpty()) {
                    tables.get(i).setCustomer(customerQueue.poll());
<<<<<<< HEAD
                }
            }
        }
    }

    private static void takeOrders() {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getCustomer() != null) {
                Order order = new Order();
                order.setTable(tables.get(i));
                for (Waiter waiter : waiters) {
                    if (!waiter.isIsActive()) {
                        order.setWaiter(waiter);
                        break;
                    }
=======
                    tables.get(i).getCustomer().setOturduMu(true);
>>>>>>> 28dc2e164323e5e6e1fc5370cad0fe5f138a7de0
                }
            }
        }
    }

<<<<<<< HEAD
    private static void simulateRestaurantProcesses() {
        for (int time = 0; time < SIMULATION_TIME; time += ARRIVAL_INTERVAL) {
            int arrivingCustomerCount = (int) (Math.random() * 5) + 1;
            int priorityCustomerCount = arrivingCustomerCount / PRIORITY_CUSTOMER_RATIO;

            totalCustomerCount += arrivingCustomerCount;
            totalPriorityCustomerCount += priorityCustomerCount;

            System.out.println("Time: " + time + " - Arriving Customer Count: " + arrivingCustomerCount +
                    " - Priority Customer Count: " + priorityCustomerCount);

            for (Table table : tables) {
                Customer customer = table.getCustomer();
                if (customer != null) {
                    // Customer waiting time
                    try {
                        Thread.sleep(CUSTOMER_WAITING_TIME * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Order taking time
                    takeOrder(table);

                    // Cooking time
                    try {
                        Thread.sleep(COOKING_TIME * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Eating time
                    try {
                        Thread.sleep(EATING_TIME * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Payment time
                    try {
                        Thread.sleep(PAYMENT_TIME * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Clear the table
                    table.setCustomer(null);

                    // Decrease the customer count after payment
                    totalCustomerCount--;
                    if (customer.isPriority()) {
                        totalPriorityCustomerCount--;
                    }
                }
            }
        }
    }

    private static void takeOrder(Table table) {
        Order order = new Order();
        order.setTable(table);
        for (Waiter waiter : waiters) {
            if (!waiter.isIsActive()) {
                order.setWaiter(waiter);
                break;
            }
        }
    }

    private static int getTotalCustomerCount() {
        int totalCustomerCount = 0;
        for (Table table : tables) {
            if (table.getCustomer() != null) {
                totalCustomerCount++;
            }
        }
        return totalCustomerCount;
    }

    private static int getTotalPriorityCustomerCount() {
        int totalPriorityCustomerCount = 0;
        for (Table table : tables) {
            if (table.getCustomer() != null && table.getCustomer().isPriority()) {
                totalPriorityCustomerCount++;
            }
        }
        return totalPriorityCustomerCount;
    }
=======
>>>>>>> 28dc2e164323e5e6e1fc5370cad0fe5f138a7de0
}
