package modules.service.models;

/**
 *
 * @author ghianco
 */
public class Service {

    private int service_id;
    private String name;
    private String description;
    private double price;
    private String active;

    public Service() {
    }

    public Service(int service_id, String name, String description, double price, String active) {
        this.service_id = service_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
