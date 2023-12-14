
package restaurantmanagament;

public class Chef implements Runnable{

    private int id;
    private String name;

    public Chef(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void run() {

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
    
    
    
    
}
