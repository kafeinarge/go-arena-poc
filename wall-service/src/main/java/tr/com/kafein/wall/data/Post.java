package tr.com.kafein.wall.data;

import tr.com.kafein.wall.dto.UserDto;

import javax.persistence.*;
import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
