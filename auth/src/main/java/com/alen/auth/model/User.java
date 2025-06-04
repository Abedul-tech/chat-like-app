package com.alen.auth.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //Person data-----------
    @Column(name="first_name",
            nullable = false,
            length = 50)
    private String firstName;

    @Column(name="last_name",
            nullable = false,
            length = 200)
    private String lastName;

    @Column(name="birth_date",
            nullable = false,
            updatable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(
            name="phone_number",
            nullable = false,
            unique = true,
            length = 10)
    private String phoneNumber;
    //User data--------------
    @Column(
            nullable = false,
            length = 50,
            unique = true)
    private String username;

    @Column(
            nullable = false,
            length = 200)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private List<Role> roles;// If you don't initialize it, you'll get a NullPointerException when you try to add roles.

    @CreationTimestamp
    @Column(
            name="created_at",
            updatable = false)
    private LocalDateTime created_at;

    @Column(
            name="last_login",
            nullable = false)
    private LocalDateTime lastLogin;//user.setLastLogin(LocalDateTime.now());

    //-----------
    public void addRole(Role role) {
        this.roles = new ArrayList<>();
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }
    //-----------
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(role-> new SimpleGrantedAuthority(role.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

