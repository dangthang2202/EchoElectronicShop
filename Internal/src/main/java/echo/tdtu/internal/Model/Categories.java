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
@Table(name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column
    private String Name;
    @Column
    private String image;
    @Column
    private String des;
    @OneToMany(mappedBy = "categories", fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE, CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    private List<Product> productList;

    public Categories(String name,String des){
        this.Name = name;
        this.des = des;
    }

    public boolean checkEmpty(){
        return this.Name == null || this.des == null;
    }
    @Override
    public String toString() {
        return "Categories{" +
                "id='" + id + '\'' +
                ", Name='" + Name + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
