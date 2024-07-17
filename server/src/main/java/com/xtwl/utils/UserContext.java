package com.xtwl.utils;

public class UserContext {

    public static ThreadLocal<Long> idThreadLocal = new ThreadLocal<Long>();
    public static ThreadLocal<String> usernameThreadLocal = new ThreadLocal<String>();

    public static void setCurrentId(Long id) {
        idThreadLocal.set(id);
    }

    public static Long getCurrentId() {
        return idThreadLocal.get();
    }

    public static void setUsername(String username) {
        usernameThreadLocal.set(username);
    }

    public static String getUsername() {
        return usernameThreadLocal.get();
    }
}
