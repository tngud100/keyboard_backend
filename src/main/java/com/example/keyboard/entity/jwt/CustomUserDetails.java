package com.example.keyboard.entity.jwt;

import com.example.keyboard.entity.member.MemberEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CustomUserDetails implements UserDetails {
    private final MemberEntity userEntity;

    public CustomUserDetails(MemberEntity userEntity) {

        System.out.println(userEntity);

        this.userEntity = userEntity;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return userEntity.getROLE();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPASSWORD();
    }

    @Override
    public String getUsername() {
        return userEntity.getLOGIN_ID();
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