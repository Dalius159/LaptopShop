package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Set;
@Entity
@Data
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String address;
    private String email;

    @NotEmpty
    private String fullName;

    @JsonIgnore
    private String password;

    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="user_role",
            joinColumns=@JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName = "id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Roles> role;

    @Transient
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Orders> listOrders;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    public Users() {}

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
