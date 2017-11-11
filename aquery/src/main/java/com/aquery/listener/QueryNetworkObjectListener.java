package com.aquery.listener;

/**
 * Created by ocittwo on 11/11/17.
 */

public interface QueryNetworkObjectListener<T> {
    void response(T response, Throwable error);
}
