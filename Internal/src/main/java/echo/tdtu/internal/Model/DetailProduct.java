package echo.tdtu.internal.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DetailProduct")
public class DetailProduct {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int color;
    private String des;
    private String image;
    private int quantity;
    private int ram;
    private int rom;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "detailProduct", fetch = FetchType.EAGER ,
            cascade = {CascadeType.REMOVE, CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    private List<OrderDetail> orderDetail;

    public boolean checkEmpty(){
        return this.quantity <= 0 || this.des == null || this.color == 0;
    }
    public DetailProduct( int color, String des) {
        this.color = color;
        this.des = des;
    }
}
