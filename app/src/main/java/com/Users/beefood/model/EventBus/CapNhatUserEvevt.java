package com.Users.beefood.model.EventBus;

import com.Users.beefood.model.User;

public class CapNhatUserEvevt {
    User user;

    public CapNhatUserEvevt(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
