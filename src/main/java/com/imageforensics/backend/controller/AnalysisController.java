package com.imageforensics.backend.controller;

import com.imageforensics.backend.entity.Analysis;
import com.imageforensics.backend.service.AnalysisService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/analyses")
public class AnalysisController {

    //create instance of AnalysisService
    private AnalysisService analysisService;

    //create constructor
    public AnalysisController(AnalysisService analysisService){
        this.analysisService = analysisService;
    }
    //create the api endpoints.

    @GetMapping
    public ResponseEntity<List<Analysis>> getAllAnalysis(){ //returns all the analysis.
        return ResponseEntity.ok(analysisService.getAllAnalysis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analysis> getAnalysisById(@PathVariable Long id){
        return ResponseEntity.ok(analysisService.getAnalysisById(id).get());
    }

    @PostMapping(consumes="multipart/form-data")
    public ResponseEntity<Analysis> createAnalysis(@RequestParam("image") MultipartFile image) throws IOException{
        Analysis savedAnalysis = analysisService.createAnalysis(image);
        return ResponseEntity.ok(savedAnalysis);   
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id){
        analysisService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
