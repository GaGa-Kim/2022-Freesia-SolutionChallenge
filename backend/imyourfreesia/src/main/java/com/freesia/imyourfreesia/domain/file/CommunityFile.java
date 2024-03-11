package com.freesia.imyourfreesia.domain.file;

import com.freesia.imyourfreesia.domain.community.Community;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class CommunityFile extends File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "communityPhotoId")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "communityId")
    private Community community;

    @Builder
    public CommunityFile(Long id, String origFileName, String filePath, Long fileSize) {
        super(origFileName, filePath, fileSize);
        this.id = id;
    }
}
