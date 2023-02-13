package com.sl.mecm.core.commons.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class UUIDToolTest {

    @Test
    void applyUUID36() {
        String actual = UUIDTool.applyUUID36();
        log.info("UUID 36:" + actual);
        Assertions.assertNotNull(actual);
    }

    @Test
    void applyUUID32() {
        String actual = UUIDTool.applyUUID32();
        log.info("UUID 32:" + actual);
        Assertions.assertNotNull(actual);
    }
}