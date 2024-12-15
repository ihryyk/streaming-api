package integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streaming.StreamingApiApplication;
import com.streaming.api.UpsertVideoRequest;
import com.streaming.api.Video;
import com.streaming.api.VideoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = StreamingApiApplication.class)
@AutoConfigureMockMvc
@Transactional
public class VideoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VideoRepository videoRepository;

    private Long videoId;

    @BeforeEach
    public void setUp() throws Exception {
        Video video = new Video();
        video.setTitle("Test Video");
        video.setPath("sample/path/to/video");
        video.setRunningTime(120);

        Video savedVideo = videoRepository.save(video);
        videoId = savedVideo.getId();

    }

    @Test
    public void shouldCreateVideo() throws Exception {
        // Create a sample video request
        UpsertVideoRequest videoRequest = new UpsertVideoRequest();
        videoRequest.setTitle("Sample Video");
        videoRequest.setRunningTime(120);
        videoRequest.setPath("sample/path/to/video");

        mockMvc.perform(post("/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(videoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Video"))
                .andExpect(jsonPath("$.path").value("sample/path/to/video"))
                .andExpect(jsonPath("$.runningTime").value(120));
    }

    @Test
    public void shouldUpdateVideo() throws Exception {
        UpsertVideoRequest videoRequest = new UpsertVideoRequest();
        videoRequest.setTitle("Updated Video Title");
        videoRequest.setDirector("Updated Director");
        videoRequest.setGenre("Comedy");
        videoRequest.setRunningTime(130);
        videoRequest.setPath("updated/path/to/video");

        mockMvc.perform(put("/videos/{id}", videoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(videoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Video Title"))
                .andExpect(jsonPath("$.director").value("Updated Director"))
                .andExpect(jsonPath("$.runningTime").value(130))
                .andExpect(jsonPath("$.path").value("updated/path/to/video"));
    }

    @Test
    public void shouldDeleteVideo() throws Exception {
        System.out.println(videoRepository.findAll());
        mockMvc.perform(delete("/videos/{id}", videoId))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetVideo() throws Exception {
        System.out.println(videoRepository.findAll());
        mockMvc.perform(get("/videos/{id}", videoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.runningTime").exists())
                .andExpect(jsonPath("$.path").exists());
    }

    @Test
    public void shouldPlayVideo() throws Exception {
        mockMvc.perform(get("/videos/{id}/play", videoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.runningTime").exists())
                .andExpect(jsonPath("$.views").exists());
    }

    @Test
    public void shouldSearchVideos() throws Exception {
        String title = "Test Video";

        mockMvc.perform(get("/videos/search")
                        .param("title", title))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Test Video"));
    }

    @Test
    public void shouldGetVideoEngagement() throws Exception {
        mockMvc.perform(get("/videos/{id}/engagement", videoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.views").exists())
                .andExpect(jsonPath("$.impressions").exists());
    }

}
