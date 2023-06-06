package ru.khalkechev.springsecuritycrud.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class UserSessionInfo {
    private String name;
    private List<String> roles = new ArrayList<>();

    public UserSessionInfo(Authentication authentication) {
        name = authentication.getName();
        final List<? extends GrantedAuthority> authorities = authentication.getAuthorities().stream()
                .sorted(Comparator.comparing((GrantedAuthority a) -> a.getAuthority())).toList();
        for (final GrantedAuthority grantedAuthority : authorities) {
            roles.add(grantedAuthority.getAuthority().substring(5));
        }
    }

        public String getName () {
            return name;
        }

        public void setName (String name){
            this.name = name;
        }

        public List<String> getRoles () {
            return roles;
        }

        public void setRoles (List < String > roles) {
            this.roles = roles;
        }


    }
