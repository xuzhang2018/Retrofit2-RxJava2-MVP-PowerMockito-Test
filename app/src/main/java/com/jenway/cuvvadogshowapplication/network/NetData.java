package com.jenway.cuvvadogshowapplication.network;

import java.util.List;
import java.util.TreeMap;

/**
 * by Xu
 * Description: the receive data
 */
public class NetData {
    private TreeMap<String, List<String>> message = new TreeMap<>();
    private String status;

    public TreeMap<String, List<String>> getMessage() {
        return message;
    }

    public void setMessage(TreeMap<String, List<String>> message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
