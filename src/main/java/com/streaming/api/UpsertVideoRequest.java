package com.streaming.api;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UpsertVideoRequest {

    private String title;
    private String synopsis;
    private String director;
    private String mainActor;
    private LocalDateTime release;
    private String genre;
    private int runningTime;
    private String path;

}
