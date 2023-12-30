
package restaurantmanagament;

public class Table {
    
    private int id;
    private String name;
    private Customer customer = null;
    private int orderStatus = 0;

    /*
    order status = sipariş durumu
    0 -> beklemede = yeni geldi sipariş için beklemede.
    1 -> siparişi alındı = garson tarafından siparişi alındı.
    2 -> hazırlanıyor ?? 
    3 -> eating
    !!! enum yapısı kazandırılabilir.
     */

    public Table(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    

}
