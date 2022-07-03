package com.ly.session;

import io.protostuff.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
@Data
public class SessionEntity implements Serializable {
    @Tag(1)
    private String id;
    @Tag(2)
    private long timeOut;
    @Tag(3)
    private long lastAccessed;
    @Tag(4)
    private int version;
    @Tag(5)
    private Map<String,Object> data;
}
