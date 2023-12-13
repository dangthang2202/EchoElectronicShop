package echo.tdtu.internal.Model;


import echo.tdtu.internal.Model.Enum.SysEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column
    private long totalPrice;
    @Column
    private int totalQuantity;
    @Column
    private int status;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER ,
            cascade = {CascadeType.REMOVE, CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    private List<OrderDetail> orderDetails;
    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Order(int totalPrice, int totalQuantity, int status) {
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", totalQuantity='" + totalQuantity + '\'' +
                ", status='" + SysEnum.Status.values()[status]  + '\'' +
                '}';
    }
}
