package tr.kafein.com.userservice.data;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "postgres")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
