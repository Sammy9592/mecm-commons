package com.sl.mecm.core.commons.utils;

import com.sl.mecm.core.commons.exception.MECMServiceException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

    private FileUtils(){}

    public static String readFile(String path){
        Assert.hasText(path, "file path must be not empty!");
        StringBuilder textBuilder = new StringBuilder();
        try (
                InputStream ins = new ClassPathResource(path).getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(ins, (StandardCharsets.UTF_8));
                Reader reader = new BufferedReader(inputStreamReader))
        {
            int c;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }catch (Exception e){
            log.error("error to read file:" + path, e);
            throw new MECMServiceException("503", "error to read file from resource path", null, e);
        }
        return textBuilder.toString();
    }
}
