package ecinema.booking.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
public class EmailRequestDto {

    @JsonProperty("email")
    private String email;

    @JsonProperty("verificationCode")
    private String verificationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}