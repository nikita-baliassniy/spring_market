package ru.geekbrains.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.geekbrains.market.beans.Cart;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "total_cost")
    private double cost;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Order(Cart cart, User user, Address address) {
        this.cost = cart.getTotalCost();
        this.owner = user;
        this.items = new ArrayList<>();
        this.address = address;
        cart.getItems().forEach((oi) -> {
            oi.setOrder(this);
            items.add(oi);
        });
    }
}
