package com.streaming.api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "Videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String synopsis;

    private String director;

    private LocalDateTime release;

    private String genre;

    private String mainActor;

    @Column(nullable = false)
    private int runningTime; // In minutes

    private boolean isDeleted;

    private int impressions;

    private int views;

    @Column(nullable = false)
    private String path;

    private LocalDateTime uploadedAt = LocalDateTime.now();

}
