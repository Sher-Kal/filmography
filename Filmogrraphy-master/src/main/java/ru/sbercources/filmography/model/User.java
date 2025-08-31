package ru.sbercources.filmography.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "user_seq", allocationSize = 1)
public class User extends GenericModel {

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "created_when")
    private LocalDateTime createdWhen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "role_id",
            foreignKey = @ForeignKey(name = "FK_USER_ROLES"))
    //@JsonIgnore
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + birthDate +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", createdWhen=" + createdWhen +
                ", role=" + role.getId() +
                '}';
    }

    @Builder
    public User(Long id, LocalDateTime createdWhen, String createdBy, LocalDateTime updatedWhen, String updatedBy, LocalDateTime deletedWhen, String deletedBy, boolean isDeleted, String login, String password, String firstName, String lastName, String middleName, LocalDate birthDate, String phone, String address, String email, LocalDateTime createdWhen1, Role role) {
        super(id, createdWhen, createdBy, updatedWhen, updatedBy, deletedWhen, deletedBy, isDeleted);
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.createdWhen = createdWhen1;
        this.role = role;
    }
}