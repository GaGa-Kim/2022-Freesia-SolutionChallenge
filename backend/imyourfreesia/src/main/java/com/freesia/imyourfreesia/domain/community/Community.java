package com.freesia.imyourfreesia.domain.community;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import com.freesia.imyourfreesia.domain.comment.Comment;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.like.Like;
import com.freesia.imyourfreesia.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @ApiModelProperty(notes = "커뮤니티 아이디", dataType = "Long", example = "1")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "userId")
    @ApiModelProperty(notes = "커뮤니티 작성 회원", dataType = "User")
    private User user;

    @Column(length = 100, nullable = false)
    @ApiModelProperty(notes = "커뮤니티 제목", dataType = "String", example = "제목")
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    @ApiModelProperty(notes = "커뮤니티 내용", dataType = "String", example = "내용")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ApiModelProperty(notes = "커뮤니티 카테고리", dataType = "Category", example = "worries")
    private Category category;

    @OneToMany(mappedBy = "community", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CommunityFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Community(Long id, String title, String content, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = Category.findByCategoryName(category);
    }

    public Community update(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = Category.findByCategoryName(category);
        return this;
    }

    public void removeAllFiles() {
        this.files.clear();
    }
}
