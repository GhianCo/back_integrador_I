package modules.pet.models;

/**
 *
 * @author ghianco
 */
public class Pet {

    private int pet_id;
    private int customer_id;
    private String name;
    private String especie;
    private String breed;
    private String birthdate;
    private String gender;
    private String active;

    public Pet() {
    }

    public Pet(int pet_id, int customer_id, String name, String especie, String breed, String birthdate, String gender, String active) {
        this.pet_id = pet_id;
        this.customer_id = customer_id;
        this.name = name;
        this.especie = especie;
        this.breed = breed;
        this.birthdate = birthdate;
        this.gender = gender;
        this.active = active;
    }

    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
