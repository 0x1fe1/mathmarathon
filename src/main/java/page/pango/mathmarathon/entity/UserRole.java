package page.pango.mathmarathon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_roles")
public class UserRole {
    @Column(nullable = false)
    public Role role;
    @Id
    public Long id;

    public enum Role {
        USER
    }
}
