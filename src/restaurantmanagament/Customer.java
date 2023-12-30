package restaurantmanagament;

public class Customer implements Runnable {

    private int id;
    private String name;
    private boolean priority; //öncelik

    public Customer(int id, String name, boolean priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    @Override
    public void run() {
        //System.out.println(getName() + " restorana giriş yaptı...    Öncelik :" + isPriority());
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
        return  name ;
    }
    
    
    

}
