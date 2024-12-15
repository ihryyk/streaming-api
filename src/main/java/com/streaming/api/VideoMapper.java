package com.streaming.api;

import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public Video toVideo(UpsertVideoRequest upsertVideoRequest) {
        Video video = new Video();
        video.setTitle(upsertVideoRequest.getTitle());
        video.setPath(upsertVideoRequest.getPath());
        video.setDirector(upsertVideoRequest.getDirector());
        video.setRelease(upsertVideoRequest.getRelease());
        video.setGenre(upsertVideoRequest.getGenre());
        video.setRunningTime(upsertVideoRequest.getRunningTime());
        video.setMainActor(upsertVideoRequest.getMainActor());
        return video;
    }

    public VideoModel tovideoModel(Video video) {
        VideoModel videoModel = new VideoModel();
        videoModel.setId(video.getId());
        videoModel.setTitle(video.getTitle());
        videoModel.setPath(video.getPath());
        videoModel.setDirector(video.getDirector());
        videoModel.setRelease(video.getRelease());
        videoModel.setGenre(video.getGenre());
        videoModel.setRunningTime(video.getRunningTime());
        videoModel.setMainActor(video.getMainActor());
        return videoModel;
    }

    public VideoMetadata toVideMetadata(Video video) {
        VideoMetadata videoMetadata = new VideoMetadata();
        videoMetadata.setDirector(video.getDirector());
        videoMetadata.setGenre(video.getGenre());
        videoMetadata.setTitle(video.getTitle());
        videoMetadata.setRunningTime(video.getRunningTime());
        videoMetadata.setMainActor(video.getMainActor());
        return videoMetadata;
    }

}
