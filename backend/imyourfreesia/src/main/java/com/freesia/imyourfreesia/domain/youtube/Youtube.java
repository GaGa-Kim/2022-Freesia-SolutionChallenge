package com.freesia.imyourfreesia.domain.youtube;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Youtube extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "youtubeId")
    private Long id;

    @Column
    private String title;

    @Column
    private String videoId;

    @Column
    private String thumbnail;

    @Builder
    public Youtube(String title, String videoId, String thumbnail) {
        this.title = title;
        this.videoId = videoId;
        this.thumbnail = thumbnail;
    }
}
