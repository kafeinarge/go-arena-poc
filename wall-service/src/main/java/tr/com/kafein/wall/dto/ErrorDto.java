package tr.com.kafein.wall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorDto {
    private int resultCode;
    private String result;
    private String errorMessage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time = new Date();

    public ErrorDto(int resultCode, String result, String errorMessage) {
        this.resultCode = resultCode;
        this.result = result;
        this.errorMessage = errorMessage;
    }
}
