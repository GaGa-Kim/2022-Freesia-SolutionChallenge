package com.freesia.imyourfreesia.domain.youtube;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "유튜브 아이디", dataType = "Long", example = "1")
    private Long id;

    @Column
    @ApiModelProperty(notes = "동영상 제목", dataType = "String", example = "제목")
    private String title;

    @Column
    @ApiModelProperty(notes = "동영상 번호", dataType = "String", example = "동영상 번호")
    private String videoId;

    @Column
    @ApiModelProperty(notes = "동영상 썸네일", dataType = "String", example = "썸네일.png")
    private String thumbnail;

    @Builder
    public Youtube(Long id, String title, String videoId, String thumbnail) {
        this.id = id;
        this.title = title;
        this.videoId = videoId;
        this.thumbnail = thumbnail;
    }
}
