package com.testt.productlist.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * {@link ProductEntity} is a POJO
 * <p>
 * what it means for us is: it is
 * our one and only database table
 * <p>
 * we give this mask to our pojo with the @Entity annotation
 * if table structure will require change in the future,
 * we will do it here in this very class
 * <p>
 * <p>
 * Created by alicembaser@gmail.com on 2019-04-27.
 * <p>
 * Project : ProductList
 * <p>
 * Package : com.testt.productlist.entity
 */
@Entity(tableName = "products")
public class ProductEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long identifier;
    private String name;
    private String brand;
    private double original_price;
    private double current_price;
    private String currency;
    private String url;

    public ProductEntity(long identifier, String name, String brand, double original_price, double current_price, String currency, String url) {
        this.identifier = identifier;
        this.name = name;
        this.brand = brand;
        this.original_price = original_price;
        this.current_price = current_price;
        this.currency = currency;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(double current_price) {
        this.current_price = current_price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
