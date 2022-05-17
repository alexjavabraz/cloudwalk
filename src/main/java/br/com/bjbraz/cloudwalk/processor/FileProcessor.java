package br.com.bjbraz.cloudwalk.processor;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

@Log4j2
public class FileProcessor {

    public BufferedInputStream start() throws FileNotFoundException {
        String URL = "https://gist.githubusercontent.com/cloudwalk-tests/be1b636e58abff14088c8b5309f575d8/raw/df6ef4a9c0b326ce3760233ef24ae8bfa8e33940/qgames.log";

        try {
            log.info("Downloading file from URL {} ", URL);
            BufferedInputStream bu = new BufferedInputStream(new URL(URL).openStream());
            log.info("File Downloaded successfully");
            return bu;
        } catch (Exception e) {
            log.error("File not found {} ", e.getMessage());
            throw new FileNotFoundException("File Invalid");
        }
    }

}
