package tr.com.kafein.uaaserver.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class AccessTokenDto {
    private String token;
    private String expireDate;
    private Long userId;
}
