package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.service.ImageService;
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

    private final ImageService imageService;

    public ImageController(ResourceLoader resourceLoader, ImageService imageService) {
        this.resourceLoader = resourceLoader;
        this.imageService = imageService;
    }

    @GetMapping("/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {

        String extension = FilenameUtils.getExtension(filename);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", String.format("image/%s", extension));
        Resource resource = imageService.loadResource(filename);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }

}
