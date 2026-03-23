package models;

public class Supplier {
    private int id;
    private String name;
    private String contactInfo;

    public int getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else{
            throw new IllegalArgumentException("Имя должно быть");
        }
    }
    public String getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(String contactInfo) {
        if (contactInfo != null && !contactInfo.isEmpty()) {
            this.contactInfo = contactInfo;
        }
        else{
            throw new IllegalArgumentException("У поставщика должна быть контактная информация");
        }
    }

    public Supplier(int id, String name, String contactInfo) {
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Имя должно быть");
        }
        if (contactInfo == null || contactInfo.isEmpty()){
            throw new IllegalArgumentException("У поставщика должна быть контактная информация");
        }
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }
}
