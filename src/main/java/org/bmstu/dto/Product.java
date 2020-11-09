package org.bmstu.dto;

import java.util.regex.*;

/**
 * Model of product data transfer object
 */
public class Product {
    /**
     * String link used when we make products GET request
     * This variable need to get product id
     */
    private String link;
    private long id;
    private String name;
    private String brand;
    private int price;
    private int quantity;

    public Product() {
    }

    /**
     * Product constructor by product link and other fields
     * @param link String url for product rest request
     * @param name String product name
     * @param brand String product brand
     * @param price int product price
     * @param quantity int product quantity
     */
    public Product(String link, String name, String brand, int price, int quantity) {
        this.id = getIdByLink(link);
        this.link = link;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Product constructor by product entity fields
     * @param name String product name
     * @param brand String product brand
     * @param price int product price
     * @param quantity int product quantity
     */
    public Product (String name, String brand, int price, int quantity) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * This method allows to get product id by product rest url by regular expressions technologies
     * @param link String url for product rest request
     * @return int product id
     */
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
