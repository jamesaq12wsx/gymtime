package com.jamesaq12wsx.gymtime.service;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@Service
public class ImageService {

    private Logger logger = LoggerFactory.getLogger(ImageService.class);

    private static String IMAGE_URL_ROOT = "https://localhost:8080/api/v1/img";

    private static String UPLOAD_ROOT = "upload-dir";

    private ResourceLoader resourceLoader;

    @Autowired
    public ImageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<String> saveImages(MultipartFile[] files) {

        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }

        List<String> resultList = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
//                redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
                System.out.println("file not existed");
            }

            try {

                // Get the file and save it somewhere
//                Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));

                String fileExtention = FilenameUtils.getExtension(file.getOriginalFilename());

                String newFilename = UUID.randomUUID().toString();

                resultList.add(IMAGE_URL_ROOT + "/" + newFilename + "." + fileExtention);

                String filepath = FilenameUtils.concat(UPLOAD_ROOT, newFilename + "." + fileExtention);
                String smallFilepath = FilenameUtils.concat(UPLOAD_ROOT, newFilename + "-sm." + fileExtention);

                Files.copy(file.getInputStream(), Paths.get(filepath));

                BufferedImage img = ImageIO.read(file.getInputStream());
                Dimension newMaxSize = new Dimension(255, 255);
                BufferedImage resizeImg = Scalr.resize(img, Scalr.Method.QUALITY, newMaxSize.width, newMaxSize.height);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(resizeImg, fileExtention, baos);
                InputStream is = new ByteArrayInputStream(baos.toByteArray());

                Files.copy(is, Paths.get(smallFilepath));


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return resultList;
    }

    public String saveImage(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            System.out.println("file not existed");
        }

        String fileExtention = FilenameUtils.getExtension(file.getOriginalFilename());

        String newFilename = UUID.randomUUID().toString();

        String filepath = FilenameUtils.concat(UPLOAD_ROOT, newFilename + "." + fileExtention);
        String smallFilepath = FilenameUtils.concat(UPLOAD_ROOT, newFilename + "-sm." + fileExtention);

        String imageUrl = IMAGE_URL_ROOT + "/" + newFilename + "." + fileExtention;

        Files.copy(file.getInputStream(), Paths.get(filepath));

        BufferedImage img = ImageIO.read(file.getInputStream());
        Dimension newMaxSize = new Dimension(255, 255);
        BufferedImage resizeImg = Scalr.resize(img, Scalr.Method.QUALITY, newMaxSize.width, newMaxSize.height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizeImg, fileExtention, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());

        Files.copy(is, Paths.get(smallFilepath));

        return imageUrl;

    }

    public void deleteImage(String filename) {

        Resource resource = resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename);

        if (!resource.exists()) {
            logger.info(String.format("File %s not exist", filename));
        }

        try {
            File imageFile = resource.getFile();

            imageFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage());
        }

    }


    public Resource loadResource(String filename) {

        Resource resource = resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename);

        return resource;
    }

    @Bean
    @Profile("demo")
    CommandLineRunner setUpDirectory() {

        return (args -> {

            File rootDir = new File(UPLOAD_ROOT);

            if (!rootDir.exists() || rootDir.isFile()) {
                Files.createDirectory(Paths.get(UPLOAD_ROOT));
            }

        });
    }
}
