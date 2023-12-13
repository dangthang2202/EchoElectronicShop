package echo.tdtu.internal.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column
    private String Name;
    @Column
    private long price;
    @Column
    private String image;
    @Column
    private String Brand;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categories_id")
    private Categories categories;
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER ,
            cascade = {CascadeType.REMOVE, CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH} )
    private List<DetailProduct> detailProduct;

    public Product(String name, long price) {
        this.Name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", price=" + price +
                ", categories=" + categories +
                ", detailProduct=" + detailProduct +
                '}';
    }
}
