package com.aquery.listener;

/**
 * Created by ocittwo on 11/11/17.
 */

public interface QueryNetworkByteListener {
    void response(byte[] bytes, Throwable throwable);
}
