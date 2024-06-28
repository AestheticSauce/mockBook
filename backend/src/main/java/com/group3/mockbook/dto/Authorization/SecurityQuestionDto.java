package com.group3.mockbook.dto.Authorization;

public class SecurityQuestionDto {
    private String email;
    private String securityQuestion;

    public SecurityQuestionDto(String email, String securityQuestion) {
        this.email = email;
        this.securityQuestion = securityQuestion;
    }

    public SecurityQuestionDto() {
    }

    public String getEmail() {
        return email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    @Override
    public String toString() {
        return "SecurityQuestionDto [email=" + email + ", securityQuestion=" + securityQuestion + "]";
    }
}
