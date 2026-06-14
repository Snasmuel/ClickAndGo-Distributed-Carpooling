package it.unipi.dii.lsmsdb.lsmsdb_project.dto;

/**
 * Data Transfer Object (DTO) per le richieste di Login.
 */
public class LoginRequest {
    private String email;
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}