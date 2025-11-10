package com.example.demosqlite.DTO;

public class ProductDTO {
    private int id;
    private String name;
    private double price;
    private int id_cat;

    public ProductDTO() {
    }

    public ProductDTO(int id, String name, double price, int id_cat) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.id_cat = id_cat;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }
}
