package com.kingmeter.single.smartlock.server.rest.dto;


import lombok.Data;

@Data
public class LockData {
    String data;
    int times;
    long perLock;
}
