package tr.com.kafein.uaaserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
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
