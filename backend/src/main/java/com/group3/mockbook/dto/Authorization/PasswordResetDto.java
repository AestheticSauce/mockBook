package com.group3.mockbook.dto.Authorization;

public class PasswordResetDto {
    private String email;
    private String securityAnswer;
    private String newPassword;
    
    public PasswordResetDto() {
    }

    public PasswordResetDto(String email, String securityAnswer, String newPassword) {
        this.email = email;
        this.securityAnswer = securityAnswer;
        this.newPassword = newPassword;
    }
    
    // Getters and setters for the fields
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
