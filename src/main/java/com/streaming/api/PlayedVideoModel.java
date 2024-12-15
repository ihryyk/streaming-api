package com.streaming.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayedVideoModel {

    private String title;
    private int runningTime;
    private int views;

}
