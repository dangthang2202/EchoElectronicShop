package echo.tdtu.internal.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column
    private String Name;
    @Column
    private long price;
    @Column
    private int quantity;
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private java.time.LocalDateTime timeStart;
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private java.time.LocalDateTime timeEnd;


    @Override
    public String toString() {
        return "Voucher{" +
                "id='" + id + '\'' +
                ", Name='" + Name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", timeEnd='" + timeEnd + '\'' +
                '}';
    }
}
