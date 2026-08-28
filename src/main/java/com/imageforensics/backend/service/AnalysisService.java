package com.imageforensics.backend.service;

import com.imageforensics.backend.entity.Analysis;
import com.imageforensics.backend.repository.AnalysisRepository;
import com.imageforensics.backend.client.MlServiceClient;
import com.imageforensics.backend.dto.MlPredictionResponse;
import com.imageforensics.backend.exception.ResourceNotFoundException;
import com.imageforensics.backend.exception.InvalidImageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service //this tells spring that it is the bussiness logic and manage this for me.
public class AnalysisService {

    //create repository instance.
    private final AnalysisRepository analysisRepository;
    private final MlServiceClient mlServiceClient;

    //create constructor.
    public AnalysisService(AnalysisRepository analysisRepository, MlServiceClient mlServiceClient){
        this.analysisRepository = analysisRepository;
        this.mlServiceClient = mlServiceClient;
    }

    //save() findById() findAll() deleteById() needed to be created.

    public Analysis createAnalysis(MultipartFile image) throws IOException{

        //checking if we have a proper image before calling ML-Service
        if(image.isEmpty()) throw new InvalidImageException("Image file can't be empty");

        //making sure that our image has proper format.
        String contentType = image.getContentType();
        if(!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)){
            throw new InvalidImageException("Only JPEG and PNG are supported");
        }

        //sends image to our ML-Service.
        MlPredictionResponse prediction = mlServiceClient.predict(image.getBytes(), image.getOriginalFilename());
        //create new Analysis.
        Analysis analysis = new Analysis();
        analysis.setImageName(image.getOriginalFilename());
        analysis.setPrediction(prediction.getPrediction());
        analysis.setConfidence(prediction.getConfidence());
        analysis.setCreatedAt(LocalDateTime.now());
        //Save result to the database.
        return analysisRepository.save(analysis);
    }

    public Analysis saveAnalysis(Analysis analysis){
        //save the analysis.
        return analysisRepository.save(analysis);
    }

    public Optional<Analysis> getAnalysisById(Long id){
        Optional<Analysis> analysis = analysisRepository.findById(id);
        if(analysis.isEmpty()){
            throw new ResourceNotFoundException("Analysis not found with id: " + id);
        }
        return analysis;
    }

    public List<Analysis> getAllAnalysis(){
        return analysisRepository.findAll();
    }

    public void deleteById(Long id){
        analysisRepository.deleteById(id);
    }  
}
