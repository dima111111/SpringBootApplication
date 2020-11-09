package org.example;

import java.util.regex.*;

public class Product {
    private long id;
    private String link;
    private String name;
    private String brand;
    private int price;
    private int quantity;

    Product() {

    }

    Product(String link, String name, String brand, int price, int quantity) {
        this.id = getIdByLink(link);
        this.link = link;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    Product (String name, String brand, int price, int quantity) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    private int getIdByLink(String link) {
        Pattern patProd = Pattern.compile("products/(\\d+)");
        Pattern patId = Pattern.compile("(\\d+)");
        Matcher matcherProd = patProd.matcher(link);
        if (matcherProd.find()) {
            Matcher matcherId = patId.matcher(matcherProd.group());
            if (matcherId.find()) {
                return Integer.parseInt(matcherId.group());
            }
        }
        return -1;
    }

    public long getId() {
        return id;
    }
    public String getLink() {
        return link;
    }
    public String getName() {
        return name;
    }
    public String getBrand() {
        return brand;
    }
    public int getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
