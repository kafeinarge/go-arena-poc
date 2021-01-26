package tr.com.kafein.wall.data;

import tr.com.kafein.wall.dto.UserDto;
import tr.com.kafein.wall.type.ApprovalType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "post", schema = "wallservice")
public class Post implements Serializable {

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

    public ApprovalType getApproval() {
        return approval;
    }

    public void setApproval(ApprovalType approval) {
        this.approval = approval;
    }
}
