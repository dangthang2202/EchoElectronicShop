package echo.tdtu.internal.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;

import java.util.List;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String phoneNumber;
    @Column
    private String email;
    @Column
    private String userName;
    @Column
    private String address;
    @Column
    private String passWord;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE, CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    @JsonBackReference
    private List<Order> orders;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable( name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

}
