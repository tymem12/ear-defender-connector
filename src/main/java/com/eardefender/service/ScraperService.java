package com.eardefender.service;

import com.eardefender.model.request.BeginScrapingRequest;
import com.eardefender.model.request.ScraperReportRequest;

public interface ScraperService {
    void beginScraping(BeginScrapingRequest beginScrapingRequest);
    void reportScrapingResults(ScraperReportRequest scraperReportRequest);
}
