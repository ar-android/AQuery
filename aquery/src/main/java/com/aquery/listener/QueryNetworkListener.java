package com.aquery.listener;

/**
 * Created by ocittwo on 11/11/17.
 */

public interface QueryNetworkListener {
    void response(String response, Throwable error);
}
