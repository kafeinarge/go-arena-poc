package tr.com.kafein.wall.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private boolean isAdmin;
    private String title;
}
