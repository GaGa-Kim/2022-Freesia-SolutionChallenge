package com.freesia.imyourfreesia.domain.center;

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

@Entity
@Getter
@NoArgsConstructor
public class Center extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "centerId")
    @ApiModelProperty(notes = "센터 아이디", dataType = "Long", example = "1")
    private Long id;

    @Column(length = 500, nullable = false)
    @ApiModelProperty(notes = "센터 이름", dataType = "String", example = "용산새일센터")
    private String name;

    @Column(nullable = false)
    @ApiModelProperty(notes = "센터 전화번호", dataType = "String", example = "02-XXXX-XXXX")
    private String contact;

    @Column(columnDefinition = "TEXT", nullable = false)
    @ApiModelProperty(notes = "센터 주소", dataType = "String", example = "서울시 용산구")
    private String address;

    @Column(length = 500)
    @ApiModelProperty(notes = "센터 웹 사이트", dataType = "String", example = "www.saeil.com")
    private String websiteUrl;

    @Builder
    public Center(Long id, String name, String contact, String address, String websiteUrl) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.websiteUrl = websiteUrl;
    }

    public void update(String address) {
        this.address = address;
    }
}
