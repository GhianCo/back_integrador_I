package shared.models;

public class Person {

    private int person_id;
    private String name;
    private String lastname;
    private String dni;
    private String email;
    private String phone;
    private String address;
    private String active;

    public Person() {
    }

    public Person(int person_id, String name, String lastname, String dni, String email, String phone, String address, String active) {
        this.person_id = person_id;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.active = active;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
