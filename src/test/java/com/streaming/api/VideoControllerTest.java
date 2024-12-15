package com.streaming.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VideoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService videoService;

    @Test
    void shouldPublishVideo() throws Exception {
        UpsertVideoRequest model = new UpsertVideoRequest();
        VideoModel video = new VideoModel();

        when(videoService.save(any(UpsertVideoRequest.class))).thenReturn(video);

        mockMvc.perform(post("/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(model)))
                .andExpect(status().isOk());

        verify(videoService).save(any(UpsertVideoRequest.class));
    }

    @Test
    void shouldUpdateVideo() throws Exception {
        Long id = 123L;
        UpsertVideoRequest model = new UpsertVideoRequest();
        VideoModel video = new VideoModel();

        when(videoService.update(any(UpsertVideoRequest.class), anyLong())).thenReturn(video);

        mockMvc.perform(put("/videos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(model)))
                .andExpect(status().isOk());

        verify(videoService).update(any(UpsertVideoRequest.class), eq(id));
    }

    @Test
    void shouldDeleteVideo() throws Exception {
        Long id = 123L;

        mockMvc.perform(delete("/videos/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(videoService).delete(id);
    }

    @Test
    void shouldGetVideo() throws Exception {
        Long id = 123L;
        Video video = new Video();

        when(videoService.get(id)).thenReturn(video);

        mockMvc.perform(get("/videos/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(videoService).get(id);
    }

    @Test
    void shouldPlayVideo() throws Exception {
        Long id = 123L;
        Video video = new Video();
        // set up video fields
        when(videoService.playVideo(id)).thenReturn(video);

        mockMvc.perform(get("/videos/" + id + "/play")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(videoService).playVideo(id);
    }

    @Test
    void shouldSearchVideos() throws Exception {
        String title = "test title", director = "test director", genre = "test genre";
        Integer maxRunningTime = 120;
        List<VideoMetadata> videoList = new ArrayList<>();

        when(videoService.search(title, director, genre, maxRunningTime)).thenReturn(videoList);

        mockMvc.perform(get("/videos/search")
                        .param("title", title)
                        .param("director", director)
                        .param("genre", genre)
                        .param("maxRunningTime", String.valueOf(maxRunningTime))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(videoService).search(title, director, genre, maxRunningTime);
    }

    @Test
    void shouldGetVideoEngagement() throws Exception {
        Long id = 123L;
        VideoEngagement videoEngagement = new VideoEngagement(1, 2);

        when(videoService.getEngagement(id)).thenReturn(videoEngagement);

        mockMvc.perform(get("/videos/" + id + "/engagement")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(videoService).getEngagement(id);
    }

}
