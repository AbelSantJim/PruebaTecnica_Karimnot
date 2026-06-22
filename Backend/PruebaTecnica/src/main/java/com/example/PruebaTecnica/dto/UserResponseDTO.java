package com.example.PruebaTecnica.dto;
public class UserResponseDTO {
    private Integer idUsuario;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
    private String status;
    private String profilePicture;
    // address si lo necesitas

    // Constructor desde Users
    public UserResponseDTO(com.example.PruebaTecnica.modelos.Users user) {
        this.idUsuario = user.getIdUsuario();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.profilePicture = user.getProfilePicture();
    }

    // Getters...
    public Integer getIdUsuario() { return idUsuario; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getProfilePicture() { return profilePicture; }
}