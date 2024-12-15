package com.streaming.api;

import org.springframework.data.jpa.domain.Specification;

public class VideoSpecifications {

    public static Specification<Video> isNotDeleted() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isFalse(root.get("isDeleted"));
    }

    public static Specification<Video> titleContains(String title) {
        return (root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Video> directorEquals(String director) {
        return (root, query, criteriaBuilder) ->
                director == null ? null : criteriaBuilder.equal(root.get("director"), director);
    }

    public static Specification<Video> genreEquals(String genre) {
        return (root, query, criteriaBuilder) ->
                genre == null ? null : criteriaBuilder.equal(root.get("genre"), genre);
    }

    public static Specification<Video> runningTimeLessThan(Integer maxRunningTime) {
        return (root, query, criteriaBuilder) ->
                maxRunningTime == null ? null
                        : criteriaBuilder.lessThanOrEqualTo(root.get("runningTime"), maxRunningTime);
    }

}
