package com.eardefender.constants;

public class EarDefenderConstants {

    public static final String STATUS_INITIALIZING = "INITIALIZING";
    public static final String STATUS_SCRAPPING = "SCRAPING";
    public static final String STATUS_PROCESSING = "PROCESSING";
    public static final String STATUS_FINISHED = "FINISHED";
    public static final String STATUS_ABORTED = "ABORTED";

    public static final Integer LABEL_POSITIVE = 1;

    public static final String MODEL_MESONET = "mesonet";
    public static final String MODEL_WAV2VEC = "wav2vec";

    public static final String URL_PATH_BEGIN_SCRAPING = "/scraping/start";
    public static final String URL_PATH_RUN_MODEL = "/model/run";
}
