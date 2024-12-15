package com.streaming.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VideoMapperTest {

    private VideoMapper sut;

    @BeforeEach
    void setUp() {
        sut = new VideoMapper();
    }

    @Test
    void toVideo_shouldMapFromUpsertVideoRequestToVideo() {
        UpsertVideoRequest request = new UpsertVideoRequest();

        request.setTitle("Demo Title");
        request.setPath("/path/to/video");
        request.setDirector("Test Director");
        request.setRelease(LocalDateTime.now());
        request.setGenre("Test Genre");
        request.setRunningTime(120);
        request.setMainActor("Test Actor");
        request.setRunningTime(200);

        Video video = sut.toVideo(request);

        assertEquals(request.getTitle(), video.getTitle());
        assertEquals(request.getPath(), video.getPath());
        assertEquals(request.getDirector(), video.getDirector());
        assertEquals(request.getRelease(), video.getRelease());
        assertEquals(request.getGenre(), video.getGenre());
        assertEquals(request.getRunningTime(), video.getRunningTime());
        assertEquals(request.getMainActor(), video.getMainActor());
    }

    @Test
    void toVideoModel_shouldMapFromVideoToVideoModel() {
        Video video = new Video();

        // Populate the video object with dummy test data
        video.setId(123L);
        video.setTitle("Demo Title");
        video.setPath("/path/to/video");
        video.setDirector("Test Director");
        video.setRelease(LocalDateTime.now());
        video.setGenre("Test Genre");
        video.setRunningTime(120);
        video.setMainActor("Test Actor");

        VideoModel videoModel = sut.tovideoModel(video);

        assertEquals(video.getId(), videoModel.getId());
        assertEquals(video.getTitle(), videoModel.getTitle());
        assertEquals(video.getPath(), videoModel.getPath());
        assertEquals(video.getDirector(), videoModel.getDirector());
        assertEquals(video.getRelease(), videoModel.getRelease());
        assertEquals(video.getGenre(), videoModel.getGenre());
        assertEquals(video.getRunningTime(), videoModel.getRunningTime());
        assertEquals(video.getMainActor(), videoModel.getMainActor());
    }

    @Test
    void toVideoMetadata_shouldMapFromVideoToVideoMetadata() {
        Video video = new Video();

        // Populate the video object with dummy test data
        video.setTitle("Demo Title");
        video.setDirector("Test Director");
        video.setRelease(LocalDateTime.now());
        video.setGenre("Test Genre");
        video.setRunningTime(120);
        video.setMainActor("Test Actor");

        VideoMetadata videoMetadata = sut.toVideMetadata(video);

        assertEquals(video.getTitle(), videoMetadata.getTitle());
        assertEquals(video.getDirector(), videoMetadata.getDirector());
        assertEquals(video.getGenre(), videoMetadata.getGenre());
        assertEquals(video.getRunningTime(), videoMetadata.getRunningTime());
        assertEquals(video.getMainActor(), videoMetadata.getMainActor());
    }

}
