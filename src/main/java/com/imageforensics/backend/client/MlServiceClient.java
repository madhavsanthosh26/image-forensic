package com.imageforensics.backend.client;

import com.imageforensics.backend.dto.MlPredictionResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

@Component
public class MlServiceClient {
    //create instance of RESTclient.
    private final RestClient restClient;
    
    //creating constructor
    private MlServiceClient(@Value("${ml.service.url}") String mlServiceUrl){
        this.restClient = RestClient.builder().baseUrl(mlServiceUrl).build();
    }

    //creating function
    public MlPredictionResponse predict(byte[] imageBytes, String fileName){

        ByteArrayResource imageResource = new ByteArrayResource(imageBytes){
            
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", imageResource);

        return restClient.post().uri("/predict").contentType(MediaType.MULTIPART_FORM_DATA).body(body).retrieve().body(MlPredictionResponse.class);


    }
    
}