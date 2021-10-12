package com.example.carpark.model;

import com.example.carpark.entity.RoleEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;


@Component
@SessionScope
@Data
@NoArgsConstructor
public class CurrentUser {

    private static final String ANONYMOUS_NAME = "Anonymous";

    private String name = ANONYMOUS_NAME;
    private boolean isAnonymous = true;
    private List<RoleEntity> roleList = new ArrayList<>();

    public void setRoleList(List<RoleEntity> roleList) {
        roleList.clear();
        roleList.addAll(this.roleList);

    }

    public boolean isLoggedIn() {
        return !isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        if (anonymous) {
            this.name = ANONYMOUS_NAME;
            this.roleList.clear();
        }
        isAnonymous = anonymous;
    }
}