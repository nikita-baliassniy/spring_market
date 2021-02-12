package ru.geekbrains.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "title")
    private String title;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "cost_per_product")
    private double costPerProduct;

    @Column(name = "cost")
    private double cost;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public OrderItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.costPerProduct = product.getCost();
        this.cost = this.costPerProduct;
    }

    public void incrementQuantity() {
        quantity++;
        cost = quantity * costPerProduct;
    }

    public void decrementQuantity() {
        quantity--;
        cost = quantity * costPerProduct;
    }
}
