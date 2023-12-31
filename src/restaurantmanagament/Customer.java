package restaurantmanagament;

import java.util.logging.Level;
import java.util.logging.Logger;
import static restaurantmanagament.RestaurantManagament.*;

public class Customer implements Runnable {

    private int id;
    private String name;
    private boolean priority;
    private boolean oturduMu = false;
    private int orderStatus = 0;
    private Table table = null;

    /*
    order status = sipariş durumu
    0 -> beklemede = yeni geldi sipariş için beklemede.
    1 -> siparişi alındı = garson tarafından siparişi alındı.
    2 -> hazırlanıyor = mutfakta sipariş hazırlanıyor
    3 -> eating = yemek yiyor
    4 -> yemeğini yedi kasa sırasında
    5 -> hesabını ödedi ayrılıyor...
    !!! enum yapısı kazandırılabilir.
     */
    public Customer(int id, String name, boolean priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(20000);
            kuyruktaMi();
        } catch (InterruptedException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isOturduMu() {
        return oturduMu;
    }

    public void setOturduMu(boolean oturduMu) {
        this.oturduMu = oturduMu;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void eating() throws InterruptedException {
        if (getOrderStatus() == 3) {
            System.out.println(getName() + " yemek yiyor...");
            Thread.sleep(3000);
            System.out.println(getName() + " yemeğini bitirdi.");
            kasaKuyrugu.add(this);
            this.setOrderStatus(4);
        }
    }

    private synchronized void kuyruktaMi() {
        if (this.priority == true) {
            if (priorityCustomerQueue.contains(this)) {
                priorityCustomerQueue.remove(this);
                System.out.println(this.getName() + " 20 saniye geçti ayrılıyor...");
            }
        } else if (this.priority == false) {
            if (customerQueue.contains(this)) {
                customerQueue.remove(this);
                System.out.println(this.getName() + " 20 saniye geçti ayrılıyor...");
            }
        }
    }

}
