package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.dto.comment.CommentListResponseDto;
import com.freesia.imyourfreesia.dto.comment.CommentSaveRequestDto;
import com.freesia.imyourfreesia.dto.comment.CommentUpdateRequestDto;
import com.freesia.imyourfreesia.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Comment API (댓글 API) "})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comments")
    @ApiOperation(value = "댓글 등록", notes = "댓글 등록 API")
    public ResponseEntity<List<CommentListResponseDto>> save(@RequestBody @Valid CommentSaveRequestDto requestDto) {
        return ResponseEntity.ok().body(commentService.saveComment(requestDto));
    }

    @GetMapping("/comments")
    @ApiOperation(value = "커뮤니티 별 댓글 조회", notes = "커뮤니티 별 댓글 조회 API")
    @ApiImplicitParam(name = "communityId", value = "게시글 아이디", dataType = "Long", example = "1")
    public ResponseEntity<List<CommentListResponseDto>> list(@RequestParam @NotNull Long communityId) {
        return ResponseEntity.ok().body(commentService.getCommentListByCommunity(communityId));
    }

    @PutMapping("/api/comments")
    @ApiOperation(value = "댓글 수정", notes = "댓글 수정 API")
    @ApiImplicitParam(name = "commentId", value = "댓글 아이디", dataType = "Long", example = "1")
    public ResponseEntity<List<CommentListResponseDto>> update(@RequestParam @NotNull Long commentId,
                                                               @RequestBody @Valid CommentUpdateRequestDto requestDto) {
        return ResponseEntity.ok().body(commentService.updateComment(commentId, requestDto));
    }

    @DeleteMapping("/api/comments")
    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제 API")
    @ApiImplicitParam(name = "commentId", value = "댓글 아이디", dataType = "Long", example = "1")
    public ResponseEntity<?> delete(@RequestParam @NotNull Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
