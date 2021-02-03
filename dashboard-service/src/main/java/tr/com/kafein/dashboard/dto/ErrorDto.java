package tr.com.kafein.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Builder
public class ErrorDto {
    private int resultCode;
    private String result;
    private String errorMessage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time = new Date();
}
