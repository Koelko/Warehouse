package models;

public class Customer {
    private int id;
    private String name;
    private String contactInfo;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }else {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Customer(int id, String name, String contactInfo) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Имя должно быть");
        }
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }
}
