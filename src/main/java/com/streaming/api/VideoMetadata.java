package com.streaming.api;

import lombok.Data;

@Data
public class VideoMetadata {

    private String title;
    private String director;
    private String mainActor;
    private String genre;
    private int runningTime;

}
