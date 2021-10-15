package com.example.carpark.model;

import com.example.carpark.entity.RoleEnumType;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;


@Component
@SessionScope
@Data
public class CurrentUser {

    private static final String ANONYMOUS_NAME = "Anonymous";

    private String name = ANONYMOUS_NAME;
    private boolean anonymous = true;
    private Set<RoleEnumType> roleList = EnumSet.noneOf(RoleEnumType.class);

    public CurrentUser setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
        if (anonymous) {
            this.name = "anonymous";
            this.roleList.clear();
        }
        return this;
    }

    public Set<RoleEnumType> getUserRoles() {
        return roleList;
    }

    public CurrentUser setUserRoles(
            Set<RoleEnumType> userRoles) {
        this.roleList = userRoles;
        return this;
    }

    public CurrentUser addUserRoles(List<RoleEnumType> userRoles) {
        this.roleList.addAll(userRoles);
        return this;
    }

    public boolean isAdmin() {
        return roleList.contains(RoleEnumType.ADMIN);
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public boolean isLoggedIn() {
        return !isAnonymous();
    }
}