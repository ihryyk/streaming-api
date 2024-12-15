package com.streaming.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VideoController implements VideoApi {

    private final VideoService videoService;

    @Override
    public VideoModel publishVideo(UpsertVideoRequest upsertVideoRequest) {
        return videoService.save(upsertVideoRequest);
    }

    @Override
    public VideoModel updateVideo(Long id, UpsertVideoRequest upsertVideoRequest) {
        return videoService.update(upsertVideoRequest, id);
    }

    @Override
    public void deleteVideo(Long id) {
        videoService.delete(id);
    }

    @Override
    public Video getVideo(Long id) {
        return videoService.get(id);
    }

    @Override
    public PlayedVideoModel playVideo(Long id) {
        Video video = videoService.playVideo(id);
        return new PlayedVideoModel(video.getTitle(), video.getRunningTime(), video.getViews());
    }

    @Override
    public List<VideoMetadata> searchVideos(String title, String director, String genre, Integer maxRunningTime) {
        return videoService.search(title, director, genre, maxRunningTime);
    }

    @Override
    public VideoEngagement getVideoEngagement(Long id) {
        return videoService.getEngagement(id);
    }

}
