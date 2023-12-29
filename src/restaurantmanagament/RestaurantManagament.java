package restaurantmanagament;

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
        Cashier cashier1 = new Cashier(1, "cashier1");

        createCustomers();
        System.out.println("Step 1: " + (priorityCustomerQueue.size() + customerQueue.size()) + " customers arrived."
                + " Priority customer count: " + priorityCustomerQueue.size());

        seatCustomers();
        int occupiedTables = 0;
        for (Table table : tables) {
            if (table.getCustomer() != null) {
                occupiedTables++;
                System.out.println("Table " + table.getId() + ": " + table.getCustomer().getName() + " is sitting.");
            } else {
                System.out.println("Table " + table.getId() + ": empty");
            }
        }
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
    }
    private static void createCustomers() {
        Random random = new Random();
        int customerId = 1;
        int numberOfCustomers = random.nextInt(10) + 1;

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

    private static void seatCustomers() {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getCustomer() == null) {
                if (!priorityCustomerQueue.isEmpty()) {
                    tables.get(i).setCustomer(priorityCustomerQueue.poll());
                } else {
                    tables.get(i).setCustomer(customerQueue.poll());
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
                }
            }
        }
    }

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
}
