package tr.com.kafein.wall.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.kafein.wall.dto.UserDto;
import tr.com.kafein.wall.type.ApprovalType;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "post", schema = "wallservice")
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @Transient
    private UserDto user;

    @Lob
    @Column(columnDefinition="text")
    private String preview;

    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    private ApprovalType approval;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
