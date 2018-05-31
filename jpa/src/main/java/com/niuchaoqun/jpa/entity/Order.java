package com.niuchaoqun.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "`order`")
@ToString(exclude = "user")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JsonBackReference
    private User user;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "order_product",
//        joinColumns = @JoinColumn(name = "order_id"),
//        inverseJoinColumns = @JoinColumn(name = "product_id"))
//    @JsonManagedReference
//    private List<Product> products;
}
