package restaurantmanagament;

//müşteriler customer sıraya girecek
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

// garson sipariş alacka
// aşçı sipariş hazırlar
// kasiyer hesap alır
public class RestaurantManagament {

    //nesneler ve threadleri burada oluşturulacak senkronizasyon olacak.
    //müşteri threadleri arasında masa seçimi ve oturma sırası senkronize olacak.
    //garson threadleri sipariş alırken senkronize olacak
    //aşçı threadleri kendi arasında senkronize olacak
    //kasa threadi tek zaten
    private static Queue<Customer> customerQueue = new LinkedList<>();    // müşteri sırası için bir kuyruk
    private static Queue<Customer> priorityCustomerQueue = new LinkedList<>(); // öncelikli müşteri sırası için bir kuyruk
    private static List<Table> tables = new ArrayList<>();        // boş masalardan oluşan bir liste
    private static List<Waiter> waiters = new ArrayList<>();
    
    public static void main(String[] args) {
        
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
        System.out.println("Adım 2 : " + doluMasa + " müşteri masalara yerşeitirildi. " + (priorityCustomerQueue.size() + customerQueue.size())
                + " müşteri beklemede.");

        /*
        System.out.println("");
        System.out.println("");

        System.out.println("normal liste");
        Iterator<Customer> iterator = customerQueue.iterator();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            System.out.println("Customer Name: " + customer.getName() + "customer önceliği : " + customer.isPriority());
        }

        System.out.println("öncelikli liste");
        Iterator<Customer> iterator2 = priorityCustomerQueue.iterator();
        while (iterator2.hasNext()) {
            Customer customer = iterator2.next();
            System.out.println("Customer Name: " + customer.getName() + "customer önceliği : " + customer.isPriority());
        }*/
    }

    //bir seferde en fazla 10 müşteri oluşturan fonksiyon.
    private static void createCustomers() {
        
        Random random = new Random();
        int customerId = 1;

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
    
    private static void sitCustomers() {
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
    
    private static void siparisleriAl() {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getCustomer() != null) {
                Order order = new Order();
                order.setTable(tables.get(i));
                for (Waiter waiter : waiters) {
                    if (waiters.get(i).isIsActive() == false) {
                        order.setWaiter(waiters.get(i));
                    }
                }
            }
        }
    }
    
}

/*  5-10 sn arasında max 10 tane olmak üzere müşteri üretiyor.

Random random = new Random();
        int customerId = 1;

        while (true) {
            // 5 ile 10 saniye arasında müşteri ürettiğimiz kod parçası 
            int waitTime = random.nextInt(10) + 5;

            // rastgele müşteri sayısı üret    max:10
            int numberOfCustomers = random.nextInt(10) + 1;

            for (int i = 0; i < numberOfCustomers; i++) {
                // rastgele müşteri
                Customer customer = new Customer(customerId, "Customer" + customerId, random.nextBoolean());

                // Müşteri thread'ini başlatın
                Thread customerThread = new Thread(customer);
                customerThread.start();

                //gelen müşteri kuyruğa eklendi
                addCustomerToQueue(customer);

                // Müşteri ID'sini artırın
                customerId++;
            }

            // Belirlenen süre boyunca bekleme yapın
            try {
                Thread.sleep(waitTime * 1000); // Milisaniye cinsinden beklenen süre
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
 */
