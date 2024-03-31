package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Entity()
@Data
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "role")
    private Set<Users> users;
    public Roles() {}
}
