package com.eardefender.service;

import com.eardefender.model.request.StartProcessingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    @Override
    public void startProcessing(StartProcessingRequest request) {
        //TODO: Call Model API
    }
}
