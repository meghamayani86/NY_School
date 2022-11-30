package com.app.nycschools.util;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class Resource<T> {

    public final Status status;
    @Nullable
    public final T data;

    @Nullable
    public final String message;

    public Resource(Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    @NonNull
    public static <T> Resource<T> loading() {
        return new Resource(Status.LOADING, null, null);
    }

    @NonNull
    public static <T> Resource<T> success(T data) {
        return new Resource(Status.SUCCESS, data, null);
    }

    @NonNull
    public static <T> Resource<T> error(String message, T data) {
        return new Resource(Status.ERROR, data, message);
    }

}
