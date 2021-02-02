package tr.com.kafein.dashboard.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class UserDto {
    public Long id;
    public String name;
    private String surname;
    private String username;
}
