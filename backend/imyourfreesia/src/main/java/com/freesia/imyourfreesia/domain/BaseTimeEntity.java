package com.freesia.imyourfreesia.domain;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    @ApiModelProperty(notes = "생성 날짜", dataType = "LocalDate", example = "20XX.XX.XX")
    private LocalDate createdDate;

    @Setter // for dto test
    @LastModifiedDate
    @ApiModelProperty(notes = "수정 날짜", dataType = "LocalDate", example = "20XX.XX.XX")
    private LocalDate modifiedDate;
}