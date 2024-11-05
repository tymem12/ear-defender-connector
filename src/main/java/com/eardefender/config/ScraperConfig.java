package com.eardefender.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ScraperConfig {

    @Value("${scraper.api.config.max-depth}")
    private int maxDepth;

    @Value("${scraper.api.config.max-files}")
    private int maxFiles;

    @Value("${scraper.api.config.max-pages}")
    private int maxPages;

    @Value("${scraper.api.config.max-time-per-file}")
    private int maxTimePerFile;

    @Value("${scraper.api.config.max-total-time}")
    private int maxTotalTime;
}
