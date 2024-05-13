package page.pango.mathmarathon.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role(String id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return id;
    }
}
