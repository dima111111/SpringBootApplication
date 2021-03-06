package org.bmstu.model;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Model of ProductEntity object
 */
@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String brand;
    @Column
    private int price;
    @Column
    private int quantity;

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
