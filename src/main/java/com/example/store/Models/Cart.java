package com.example.store.Models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Cart {
    @Id
    private Long id;

    @ElementCollection
    private Map<Product, Integer> Cart = new LinkedHashMap<>();

    @OneToOne(cascade = CascadeType.REMOVE)
    @MapsId
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Product, Integer> getCart() {
        return Cart;
    }

    public void setCart(Map<Product, Integer> cart) {
        Cart = cart;
    }
}
