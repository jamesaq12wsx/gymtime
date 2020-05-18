package com.jamesaq12wsx.gymtime.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HTMLParse {

    public static void main(String[] args) {

        String title ="";
        Document doc;
        try {
            doc = Jsoup.connect("http://google.com/").get();
            title = doc.title();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Jsoup Can read HTML page from URL, title : " + title);
    }
}
