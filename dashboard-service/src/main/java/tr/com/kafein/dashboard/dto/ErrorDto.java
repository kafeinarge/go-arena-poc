package tr.com.kafein.dashboard.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class ErrorDto {
    private int resultCode;
    private String result;
    private String errorMessage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time = new Date();

    public ErrorDto() {
    }

    public ErrorDto(int resultCode, String result, String errorMessage) {
        this.resultCode = resultCode;
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDto errorDto = (ErrorDto) o;
        return resultCode == errorDto.resultCode &&
                Objects.equals(result, errorDto.result) &&
                Objects.equals(errorMessage, errorDto.errorMessage) &&
                Objects.equals(time, errorDto.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultCode, result, errorMessage, time);
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "resultCode=" + resultCode +
                ", result='" + result + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", time=" + time +
                '}';
    }
}
