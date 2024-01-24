package com.example.keyboard.entity.jwt;

import com.example.keyboard.entity.member.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final MemberEntity memberEntity;

    public CustomUserDetails(MemberEntity memberEntity) {

        System.out.println(memberEntity);

        this.memberEntity = memberEntity;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return memberEntity.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return memberEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return memberEntity.getLoginId();
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