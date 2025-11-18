package com.streaming.api;

import com.streaming.exception.VideoNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class VideoServiceImpl implements VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
    private final VideoRepository videoRepository;
    public final VideoMapper videoMapper;

    @Override
    public VideoModel save(UpsertVideoRequest upsertVideoRequest) {
        log.info("Saving video:  title={} ...", upsertVideoRequest.getTitle());
        Video savedVideo = videoRepository.save(videoMapper.toVideo(upsertVideoRequest));
        log.info("Saved video:  title={}, id={}.", savedVideo.getTitle(), savedVideo.getId());
        log.info("Test git workflow-1");
        log.info("Test git workflow-2");
        return videoMapper.tovideoModel(savedVideo);
    }

    @Transactional
    @Override
    public VideoModel update(UpsertVideoRequest upsertVideoRequest, Long videoId) {
        log.info("Updating video: videoId={} ...", videoId);
        Video video = findById(videoId);
        video.setTitle(upsertVideoRequest.getTitle());
        video.setSynopsis(upsertVideoRequest.getSynopsis());
        video.setDirector(upsertVideoRequest.getDirector());
        video.setRelease(upsertVideoRequest.getRelease());
        video.setGenre(upsertVideoRequest.getGenre());
        video.setRunningTime(upsertVideoRequest.getRunningTime());
        video.setPath(upsertVideoRequest.getPath());
        log.info("Updated video: videoId={}.", videoId);
        return videoMapper.tovideoModel(video);
    }

    @Transactional
    @Override
    public void delete(Long videoId) {
        log.info("Deleting video: videoId={} ...", videoId);
        Video video = findById(videoId);
        video.setDeleted(true);
        log.info("Deleted video: videoId={}.", videoId);
    }

    @Transactional
    @Override
    public Video get(Long videoId) {
        log.info("Getting video: videoId={} ...", videoId);
        Video video = findById(videoId);
        video.setImpressions(video.getImpressions() + 1);
        log.info("Got video: videoId={}.", videoId);
        return video;
    }

    @Transactional
    @Override
    public Video playVideo(Long videoId) {
        log.info("Playing video: videoId={} ...", videoId);
        Video video = findById(videoId);
        video.setViews(video.getViews() + 1);
        log.info("Played video: videoId={}.", videoId);
        return video;
    }

    @Override
    public List<VideoMetadata> search(String title, String director, String genre, Integer maxRunningTime) {
        Specification<Video> spec = Specification.where(VideoSpecifications.titleContains(title))
                .and(VideoSpecifications.isNotDeleted())
                .and(VideoSpecifications.directorEquals(director))
                .and(VideoSpecifications.genreEquals(genre))
                .and(VideoSpecifications.runningTimeLessThan(maxRunningTime));
        return videoRepository.findAll(spec).stream().map(videoMapper::toVideMetadata).toList();
    }

    @Override
    public VideoEngagement getEngagement(Long videoId) {
        log.info("Getting videos engagement: videoId={} ...", videoId);
        Video video = findById(videoId);
        log.info("Got videos engagement: videoId={} ...", videoId);
        return new VideoEngagement(video.getImpressions(), video.getViews());
    }

    private Video findById(Long videoId) {
        return videoRepository.findByIdAndIsDeletedFalse(videoId)
                .orElseThrow(() -> new VideoNotFoundException(String.format("Video not found: videoId=%s", videoId)));
    }

}
