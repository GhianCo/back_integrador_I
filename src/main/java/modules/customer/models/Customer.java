package modules.customer.models;

import shared.models.Person;

/**
 *
 * @author ghianco
 */
public class Customer extends Person {

    private int customer_id;
    private String active;
    
    public Customer() {
    }

    public Customer(int customer_id, int person_id) {
        this.customer_id = customer_id;
        this.setPerson_id(person_id);
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
