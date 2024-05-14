package model;

public class Products {
    private int prod_id;
    private String prod_title;
    private int prod_prct_id;
    private int prod_hsnc_id;
    private String prod_brand;
    private String prod_image;
    private double prod_price; // New attribute for product price


    // Getters and setters
    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_title() {
        return prod_title;
    }

    public void setProd_title(String prod_title) {
        this.prod_title = prod_title;
    }

    public int getProd_prct_id() {
        return prod_prct_id;
    }

    public void setProd_prct_id(int prod_prct_id) {
        this.prod_prct_id = prod_prct_id;
    }

    public int getProd_hsnc_id() {
        return prod_hsnc_id;
    }

    public void setProd_hsnc_id(int prod_hsnc_id) {
        this.prod_hsnc_id = prod_hsnc_id;
    }

    public String getProd_brand() {
        return prod_brand;
    }

    public void setProd_brand(String prod_brand) {
        this.prod_brand = prod_brand;
    }

    public String getProd_image() {
        return prod_image;
    }

    public void setProd_image(String prod_image) {
        this.prod_image = prod_image;
    }

    public double getProd_price() {
        return prod_price;
    }

    public void setProd_price(double prod_price) {
        this.prod_price = prod_price;
    }

}
