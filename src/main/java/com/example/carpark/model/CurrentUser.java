package com.example.carpark.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
@NoArgsConstructor
public class CurrentUser {

    private static final String ANONYMOUS = "Gosho neizvestniq";

    private String name = ANONYMOUS;
    private boolean isAnonymous;

    public void setAnonymous(boolean anonymous) {
        if (anonymous){
            this.name = ANONYMOUS;
        }
        isAnonymous = anonymous;
    }
}
