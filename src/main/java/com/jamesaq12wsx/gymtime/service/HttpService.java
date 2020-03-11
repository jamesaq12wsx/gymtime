package com.jamesaq12wsx.gymtime.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesaq12wsx.gymtime.model.LAFitnessClubsResponse;
import com.jamesaq12wsx.gymtime.model.LaFitnessClubInfoRequest;
import com.jamesaq12wsx.gymtime.model.LaFitnessClubsRequest;
import com.jamesaq12wsx.gymtime.model.LaFitnessInfoResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

public class HttpService {

    public static void main(String[] args) {

        HttpService service = new HttpService();

//        String urlStr = "https://www.lafitness.com/Pages/GetClubLocations.aspx/GetClubLocationsByStateAndZipCode";
//
//        LaFitnessClubsRequest request = new LaFitnessClubsRequest(null, "CA");
//
//        LAFitnessClubsResponse responses = service.httpRestPost(urlStr, null, request, new ParameterizedTypeReference<LAFitnessClubsResponse>() {});

//        String clubInfoUrl = "https://www.lafitness.com/Pages/findclubresultszip.aspx/GetClubInformation";
//
//        LaFitnessClubInfoRequest request2 = new LaFitnessClubInfoRequest("1151", "AGOURA HILLS", "CA", "1");
//
//        LaFitnessInfoResponse response = service.httpRestPost(clubInfoUrl, null, request2, new ParameterizedTypeReference<LaFitnessInfoResponse>() {});
//
//        System.out.println(response);

        String html = "<table width='200' role='presentation'>" +
                "<tr><td colspan='2' class='th'><b>Club Hours</b></td></tr>" +
                "<tr><td class='thRowHeader' rowspan='1'><b>Mon - Thu</b></td><td>4:00am - 11:00pm</td></tr>" +
                "<tr><td class='thRowHeader' rowspan='1'><b>Fri</b></td><td>4:00am - 10:00pm</td></tr>" +
                "<tr><td class='thRowHeader' rowspan='1'><b>Sat - Sun</b></td><td>6:00am - 8:00pm</td></tr></table>";

        Document doc = Jsoup.parse(html);

        Element tBody = doc.body().child(0).child(0);

        for (Element row: tBody.children()){
            if (row.tagName() == "tr" && row.childNodeSize() > 1){
                System.out.println(row.childNode(1).toString());
            }
        }

    }

    RestTemplate restTemplate;

    public HttpService() {
        this.restTemplate = new RestTemplate();

        // This help server deserialize jackson properly
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
    }

    public <T> T httpRestPost(String url, Map<String, String> headers, Object body, ParameterizedTypeReference<T> type) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        if (headers != null && !headers.isEmpty()){

            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);


            for (Map.Entry<String, String> header: headers.entrySet()) {
                httpHeaders.add(header.getKey(), header.getValue());
            }
        }

        HttpEntity requestBody = new HttpEntity(body, httpHeaders);

//        ResponseEntity<T> response = restTemplate.postForEntity(url, requestBody, type);

        ResponseEntity<T> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestBody,
                type
        );

        T responseBody = responseEntity.getBody();

        return responseBody;

    }

}
