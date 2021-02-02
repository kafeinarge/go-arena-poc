package tr.com.kafein.userservice.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "user", schema = "userservice")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private String password;
    @Column(unique=true)
    private String username;
    private boolean isAdmin;
    private String title;
}
