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

@Entity
@Getter
@NoArgsConstructor
public class CommunityFile extends File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "communityFileId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "communityId")
    private Community community;

    @Builder
    public CommunityFile(Long id, String origFileName, String filePath, Long fileSize) {
        super(origFileName, filePath, fileSize);
        this.id = id;
    }

    public void setCommunity(Community community) {
        this.community = community;
        if (!community.getFiles().contains(this)) {
            community.getFiles().add(this);
        }
    }
}
