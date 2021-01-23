package tr.kafein.com.uaaserver.dto;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class SecurityUser extends User {

    private Long id;

    private String name;

    public SecurityUser(String username, String password) {
        super(username, password, true, true, true, true, new ArrayList<>());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
