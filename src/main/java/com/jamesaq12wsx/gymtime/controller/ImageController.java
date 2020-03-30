package com.jamesaq12wsx.gymtime.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/img")
public class ImageController {

    private static String UPLOAD_ROOT = "upload-dir";

    private final ResourceLoader resourceLoader;

    public ImageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {

        String extension = FilenameUtils.getExtension(filename);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", String.format("image/%s", extension));
        Resource resource = resourceLoader.getResource("file:"+UPLOAD_ROOT+"/"+filename);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }

}
