package com.freesia.imyourfreesia.domain.community;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import com.freesia.imyourfreesia.domain.comment.Comment;
import com.freesia.imyourfreesia.domain.likes.Likes;
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
public class Community extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "communityId")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "community", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CommunityPhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Community(Long id, String title, String content, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Community update(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
        return this;
    }

    public void addPhoto(CommunityPhoto communityPhoto) {
        this.photos.add(communityPhoto);
        if (communityPhoto.getCommunity() != this) {
            communityPhoto.setCommunity(this);
        }
    }

    public void addLike(Likes likes) {
        this.likes.add(likes);
        if (likes.getCommunity() != this) {
            likes.setCommunity(this);
        }
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        if (comment.getCommunity() != this) {
            comment.setCommunity(this);
        }
    }
}
