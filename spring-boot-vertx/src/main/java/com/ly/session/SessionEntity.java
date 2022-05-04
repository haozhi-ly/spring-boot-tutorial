package com.ly.session;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
@Data
public class SessionEntity implements Serializable {
    private String id;
    private long timeOut;
    private long lastAccessed;
    private int version;
    private Map<String,Object> data;
}
