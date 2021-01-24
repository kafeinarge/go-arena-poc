package tr.com.kafein.dashboard.data;

import javax.persistence.*;

@Entity
@Table(name = "dashboard", schema = "dashboardservice")
public class Dashboard {

    @Id
    @GeneratedValue
    private Long id;

}
