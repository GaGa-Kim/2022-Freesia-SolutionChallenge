package com.freesia.imyourfreesia.domain.challenge;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import com.freesia.imyourfreesia.domain.emoticon.Emoticon;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Challenge extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challengeId")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallengeFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emoticon> emoticons = new ArrayList<>();

    @Builder
    public Challenge(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Challenge update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public void removeAllFiles() {
        this.files.clear();
    }
}
