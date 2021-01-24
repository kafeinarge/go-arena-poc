package tr.com.kafein.wall.dto;

import tr.com.kafein.wall.type.ApprovalType;

import java.util.Date;

public class PostDto {
    private Long id;

    private Long userId;

    private UserDto user;

    private String preview;

    private String text;

    private Date creationDate;

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
