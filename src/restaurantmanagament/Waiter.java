
package restaurantmanagament;

public class Waiter implements Runnable{

    private int id;
    private String name;
    private boolean isActive = false;

    public Waiter(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    @Override
    public void run() {
       siparisAl();
    }

    public void siparisAl(){
        System.out.println("Sipariş alınıyor...");
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

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    
 
    
}
