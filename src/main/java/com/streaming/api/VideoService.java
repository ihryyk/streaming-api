package com.streaming.api;

import java.util.List;

public interface VideoService {

    VideoModel save(UpsertVideoRequest upsertVideoRequest);

    VideoModel update(UpsertVideoRequest upsertVideoRequest, Long videoId);

    void delete(Long videoId);

    Video get(Long videoId);

    Video playVideo(Long videoId);

    List<VideoMetadata> search(String title, String director, String genre, Integer maxRunningTime);

    VideoEngagement getEngagement(Long videoId);

}
