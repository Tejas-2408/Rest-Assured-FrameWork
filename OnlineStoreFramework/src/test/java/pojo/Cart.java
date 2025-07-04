package pojo;

import java.util.Date;
import java.util.List;

public class Cart {
	private int userId;
    private Date date;
    private List<ProductCart> products;

    // Constructors
    
    public Cart() {
    	
    }

    public Cart(int userId, Date date, List<ProductCart> products) {
        this.userId = userId;
        this.date = date;
        this.products = products;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ProductCart> getProducts() {
        return products;
    }

    public void setProducts(List<ProductCart> products) {
        this.products = products;
    }
   }

