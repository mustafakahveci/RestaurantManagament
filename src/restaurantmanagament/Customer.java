package restaurantmanagament;

import java.util.logging.Level;
import java.util.logging.Logger;
import static restaurantmanagament.RestaurantManagament.*;

public class Customer implements Runnable {

    private int id;
    private String name;
    private boolean priority;
    private boolean oturduMu = false;

    public Customer(int id, String name, boolean priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    @Override
    public void run() {
        //System.out.println(getName() + " restorana giriş yaptı...    Öncelik :" + isPriority());
        if (!oturduMu) {
            try {
                Thread.sleep(20000);
                if (!oturduMu) {
                    if (priority) {
                        priorityCustomerQueue.remove(this);
                    } else {
                        customerQueue.remove(this);
                    }
                    System.out.println(this.name + " 20 saniye bekledi, yer yok ve ayrılıyor...");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
            }
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

}
