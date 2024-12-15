package com.streaming.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.streaming.exception.VideoNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class VideoServiceImplTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMapper videoMapper;

    @InjectMocks
    private VideoServiceImpl sut;

    private UpsertVideoRequest upsertVideoRequest;
    private Video video;
    private VideoModel videoModel;
    private List<Video> videoList;
    private List<VideoMetadata> videoMetadataList;

    @BeforeEach
    void setUp() {
        sut = new VideoServiceImpl(videoRepository, videoMapper);

        upsertVideoRequest = new UpsertVideoRequest();
        video = new Video();
        videoModel = new VideoModel();
        videoList = Collections.singletonList(video);
        videoMetadataList = Collections.singletonList(new VideoMetadata());
    }

    @Test
    void save_shouldCallRepositorySaveAndReturnVideoModel() {
        when(videoMapper.toVideo(upsertVideoRequest)).thenReturn(video);
        when(videoRepository.save(video)).thenReturn(video);
        when(videoMapper.tovideoModel(video)).thenReturn(videoModel);

        VideoModel result = sut.save(upsertVideoRequest);

        assertEquals(videoModel, result);
        verify(videoRepository).save(video);
    }

    @Test
    void update_shouldUpdateExistingVideoAndReturnVideoModel() {
        Long id = 123L;
        when(videoRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(video));
        when(videoMapper.tovideoModel(video)).thenReturn(videoModel);

        VideoModel result = sut.update(upsertVideoRequest, id);

        assertEquals(videoModel, result);
        verify(videoRepository).findByIdAndIsDeletedFalse(id);
    }

    @Test
    void update_shouldThrowExceptionWhenVideoNotFound() {
        Long id = 123L;
        when(videoRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.update(upsertVideoRequest, id))
                .isInstanceOf(VideoNotFoundException.class)
                .hasMessageContaining("Video not found");
    }

    @Test
    void delete_shouldMarkVideoAsDeleted() {
        Long id = 123L;
        when(videoRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(video));

        sut.delete(id);

        assertTrue(video.isDeleted());
        verify(videoRepository).findByIdAndIsDeletedFalse(id);
    }

    @Test
    void delete_shouldThrowExceptionWhenVideoNotFound() {
        Long id = 123L;
        when(videoRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.delete(id))
                .isInstanceOf(VideoNotFoundException.class)
                .hasMessageContaining("Video not found");
    }

    @Test
    void get_shouldReturnVideo() {
        Long id = 123L;
        when(videoRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(video));

        Video result = sut.get(id);

        assertEquals(video, result);
        verify(videoRepository).findByIdAndIsDeletedFalse(id);
    }

    @Test
    void get_shouldThrowExceptionWhenVideoNotFound() {
        Long id = 123L;
        when(videoRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.get(id))
                .isInstanceOf(VideoNotFoundException.class)
                .hasMessageContaining("Video not found");
    }

    @Test
    void playVideo_shouldIncrementViewsAndReturnVideo() {
        Long id = 123L;
        when(videoRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(video));

        Video result = sut.playVideo(id);

        assertEquals(video, result);
        assertEquals(1, video.getViews()); // assumes initial views count is 0
        verify(videoRepository).findByIdAndIsDeletedFalse(id);
    }

    @Test
    void playVideo_shouldThrowExceptionWhenVideoNotFound() {
        Long id = 123L;
        when(videoRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.playVideo(id))
                .isInstanceOf(VideoNotFoundException.class)
                .hasMessageContaining("Video not found");
    }

    @Test
    void search_shouldReturnVideoMetadataList() {
        String title = "TestTitle", director = "TestDirector", genre = "TestGenre";
        Integer maxRunningTime = 120;
        when(videoRepository.findAll(any(Specification.class))).thenReturn(videoList);
        when(videoMapper.toVideMetadata(video)).thenReturn(videoMetadataList.get(0));

        List<VideoMetadata> result = sut.search(title, director, genre, maxRunningTime);

        assertEquals(videoMetadataList, result);
    }

    @Test
    void getEngagement_shouldReturnVideoEngagement() {
        Long id = 123L;
        when(videoRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(video));

        VideoEngagement result = sut.getEngagement(id);

        assertEquals(video.getImpressions(), result.getImpressions());
        assertEquals(video.getViews(), result.getViews());
        verify(videoRepository).findByIdAndIsDeletedFalse(id);
    }

    @Test
    void getEngagement_shouldThrowExceptionWhenVideoNotFound() {
        Long id = 123L;
        when(videoRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.getEngagement(id))
                .isInstanceOf(VideoNotFoundException.class)
                .hasMessageContaining("Video not found");
    }

}
