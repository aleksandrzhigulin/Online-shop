package com.example.store.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {

  public Product() {
  }

  public Product(String name, String description, Integer price) {
    this.name = name;
    this.description = description;
    this.price = price;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private String description;
  private Integer price;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    Product product = (Product) o;
    return getId().equals(product.getId()) && Objects.equals(getName(), product.getName())
        && Objects.equals(getDescription(), product.getDescription()) && Objects.equals(getPrice(),
        product.getPrice());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getDescription(), getPrice());
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        '}';
  }
}
