package com.freesia.imyourfreesia.domain.center;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
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
    private Long id;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(nullable = false)
    private String contact;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String address;

    @Column(length = 500)
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
