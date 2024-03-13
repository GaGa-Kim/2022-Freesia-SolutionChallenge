package com.freesia.imyourfreesia.dto.emoticon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class EmoticonCountResponseDto {
    @ApiModelProperty(notes = "이모티콘 1 개수", dataType = "Long", example = "1")
    private final Long emoticon1;

    @ApiModelProperty(notes = "이모티콘 2 개수", dataType = "Long", example = "1")
    private final Long emoticon2;

    @ApiModelProperty(notes = "이모티콘 3 개수", dataType = "Long", example = "1")
    private final Long emoticon3;

    @ApiModelProperty(notes = "이모티콘 4 개수", dataType = "Long", example = "1")
    private final Long emoticon4;

    @ApiModelProperty(notes = "이모티콘 5 개수", dataType = "Long", example = "1")
    private final Long emoticon5;

    public EmoticonCountResponseDto(Long emoticon1, Long emoticon2, Long emoticon3, Long emoticon4, Long emoticon5) {
        this.emoticon1 = emoticon1;
        this.emoticon2 = emoticon2;
        this.emoticon3 = emoticon3;
        this.emoticon4 = emoticon4;
        this.emoticon5 = emoticon5;
    }
}
