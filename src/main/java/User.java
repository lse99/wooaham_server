import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;
}
