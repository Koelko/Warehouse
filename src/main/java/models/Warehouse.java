package models;

public class Warehouse {
    private int id;
    private String name;
    private String address;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Название должно быть  заполнено");
        }
        this.name = name;
    }

    public void setAddress(String address) {
        if (address == null || address.isEmpty()){
            throw new IllegalArgumentException("Адресс должно быть  заполнено");
        }
        this.address = address;
    }

    public Warehouse(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
