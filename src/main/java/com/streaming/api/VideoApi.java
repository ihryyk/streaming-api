package com.streaming.api;

import com.streaming.exception.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/videos")
public interface VideoApi {

    @Operation(summary = "Publish a video",
            description = "Publishes a new video to the platform.")
    @ApiResponse(responseCode = "200", description = "Successfully published the video",
            content = @Content(schema = @Schema(implementation = UpsertVideoRequest.class)))
    @ApiResponse(responseCode = "400", description = "Invalid video data",
            content = @Content(schema = @Schema(implementation = ApiException.class)))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VideoModel publishVideo(
            @Parameter(description = "The video data to be published")
            @RequestBody UpsertVideoRequest video);

    @Operation(summary = "Update a video",
            description = "Updates the metadata of an existing video.")
    @ApiResponse(responseCode = "200", description = "Successfully updated the video",
            content = @Content(schema = @Schema(implementation = UpsertVideoRequest.class)))
    @ApiResponse(responseCode = "400", description = "Invalid video data",
            content = @Content(schema = @Schema(implementation = ApiException.class)))
    @ApiResponse(responseCode = "404", description = "Video not found",
            content = @Content(schema = @Schema(implementation = ApiException.class)))
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VideoModel updateVideo(
            @Parameter(description = "The ID of the video to update")
            @PathVariable(value = "id") Long id,
            @Parameter(description = "The new metadata for the video")
            @RequestBody UpsertVideoRequest upsertVideoRequest);

    @Operation(summary = "Soft delete a video",
            description = "Marks a video as deleted. The video is not actually removed from the database, but is excluded from most operations.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted the video")
    @ApiResponse(responseCode = "404", description = "Video not found",
            content = @Content(schema = @Schema(implementation = ApiException.class)))
    @DeleteMapping("/{id}")
    void deleteVideo(
            @Parameter(description = "The ID of the video to delete")
            @PathVariable(value = "id") Long id);

    @Operation(summary = "Get a video",
            description = "Returns the metadata and content of a video.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the video",
            content = @Content(schema = @Schema(implementation = UpsertVideoRequest.class)))
    @ApiResponse(responseCode = "404", description = "Video not found",
            content = @Content(schema = @Schema(implementation = ApiException.class)))
    @GetMapping("/{id}")
    Video getVideo(
            @Parameter(description = "The ID of the video to retrieve")
            @PathVariable(value = "id") Long id);

    @Operation(summary = "Play a video",
            description = "Returns the content of a video.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the video content",
            content = @Content(schema = @Schema(implementation = PlayedVideoModel.class)))
    @ApiResponse(responseCode = "404", description = "Video not found or has been deleted",
            content = @Content(schema = @Schema(implementation = ApiException.class)))
    @GetMapping("{id}/play")
    PlayedVideoModel playVideo(
            @Parameter(description = "The ID of the video to play")
            @PathVariable(value = "id") Long id);

    @Operation(summary = "Search for videos",
            description = "Returns a list of videos that match the specified search criteria.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of videos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Video.class))))
    @GetMapping("/search")
    List<VideoMetadata> searchVideos(
            @Parameter(description = "The title to search for")
            @RequestParam(required = false) String title,
            @Parameter(description = "The director to search for")
            @RequestParam(required = false) String director,
            @Parameter(description = "The genre to search for")
            @RequestParam(required = false) String genre,
            @Parameter(description = "The maximum running time to search for")
            @RequestParam(required = false) Integer maxRunningTime);

    @Operation(summary = "Get engagement statistics for a video",
            description = "Returns the engagement statistics (impressions and views) for a video.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the engagement statistics",
            content = @Content(schema = @Schema(implementation = VideoEngagement.class)))
    @ApiResponse(responseCode = "404", description = "Video not found or has been deleted",
            content = @Content(schema = @Schema(implementation = ApiException.class)))
    @GetMapping("/{id}/engagement")
    VideoEngagement getVideoEngagement(
            @Parameter(description = "The ID of the video to retrieve engagement statistics for")
            @PathVariable(value = "id") Long id);

}
