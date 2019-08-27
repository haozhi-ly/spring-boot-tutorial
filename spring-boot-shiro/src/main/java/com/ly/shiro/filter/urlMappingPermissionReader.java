package com.ly.shiro.filter;

import java.io.IOException;
import java.util.Map;

public interface urlMappingPermissionReader {
    Map<String,String> getMappingMap() throws IOException;
}
