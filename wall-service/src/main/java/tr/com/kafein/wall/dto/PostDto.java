package tr.com.kafein.wall.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import tr.com.kafein.wall.type.ApprovalType;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class PostDto {
    private Long id;

    private Long userId;

    private UserDto user;

    private String preview;

    private String text;

    private Date creationDate;

    private ApprovalType approval;
}
