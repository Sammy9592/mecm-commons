package com.sl.mecm.core.commons.utils;

import java.util.UUID;

public class UUIDTool {

    private UUIDTool(){}

    public static String applyUUID36(){
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    public static String applyUUID32(){
        UUID id = UUID.randomUUID();
        return id.toString().replace("-", "");
    }
}
