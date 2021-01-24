package tr.com.kafein.dashboard.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ErrorDto {
    private int resultCode;
    private String result;
    private String errorMessage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time = new Date();

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
}
