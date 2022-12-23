package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Address address;
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    public Long getId(){
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    private void setAddress(Address address) {
        this.address = address;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    private void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    private void setOrder(Order order) {
        this.order = order;
    }
}
