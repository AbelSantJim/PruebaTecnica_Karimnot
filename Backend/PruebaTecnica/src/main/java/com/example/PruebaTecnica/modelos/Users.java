// src/main/java/com/example/PruebaTecnica/modelos/Users.java
package com.example.PruebaTecnica.modelos;

import jakarta.validation.*;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    @Column(nullable = false)
    @NotBlank(message = "El password es requerido")
    private String password;



    @Column(name="firstName", columnDefinition = "text", nullable = false)
    @NotBlank(message = "El nombre es requerido")
    private String firstName;
    @Column(name="lastName", columnDefinition = "text", nullable = false)
    @NotBlank(message = "El apellido requerido")
    private String lastName;

    @Column( name="email", unique = true, nullable = false, columnDefinition = "text")
    @Email(message = "Formato de email inválido")
    @NotBlank(message = "El email es requerido")
    private String email;
    @Column(name = "phoneNumber" , columnDefinition = "varchar")
    private String phoneNumber;

    @Column( name="role", length = 10, columnDefinition = "text")
    @Pattern(regexp = "Admin|User", message = "Role debe ser Admin o User")
    private String role;  // "Admin" o "User"

    @Column( name="status", length = 10)
    @Pattern(regexp = "Active|Inactive", message = "Status debe ser Active o Inactive")
    private String status;  // "Active" o "Inactive"

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn()
    private Address address;

    @Column(name = "profilePicture", columnDefinition = "text")
    private String profilePicture;

    // Getters y Setters
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
}