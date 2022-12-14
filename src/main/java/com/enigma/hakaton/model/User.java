package com.enigma.hakaton.model;

import com.enigma.hakaton.model.enums.Role;
import com.enigma.hakaton.model.enums.UserStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serial;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "e_user")
public class User implements UserDetails {

    @Serial
    private static final long serialVersionUID = 3896064634943267445L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "e_user_seq")
    @SequenceGenerator(name = "e_user_seq", sequenceName = "e_user_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Role role;

    @Column(name = "user_status")
    private UserStatus userStatus;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "locked")
    public Boolean locked;

    @OneToMany(targetEntity = Bill.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Bill> bills;

    @Transient
    private Set<SimpleGrantedAuthority> authorities;


    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(locked != null && locked) && !UserStatus.BLOCKED.equals(userStatus);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Set<Bill> getBills() {
        if (bills == null) {
            bills = new HashSet<>();
        }
        return bills;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, role);
    }
}
