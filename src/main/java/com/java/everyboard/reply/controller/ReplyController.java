package com.java.everyboard.reply.controller;

import com.java.everyboard.reply.dto.ReplyPatchDto;
import com.java.everyboard.reply.dto.ReplyPostDto;
import com.java.everyboard.reply.dto.ReplyResponseDto;
import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.reply.mapper.ReplyMapper;
import com.java.everyboard.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {
    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    // 대댓글 생성 //
    @PostMapping
    public ResponseEntity postReply(@Valid @RequestBody ReplyPostDto requestBody){

        Reply reply = replyService.createReply(
                replyMapper.replyPostDtoToReply(requestBody),
                requestBody.getContentId(), requestBody.getCommentId()
        );

        ReplyResponseDto replyResponseDto = replyMapper.replyToReplyResponseDto(reply);

        return new ResponseEntity(replyResponseDto, HttpStatus.CREATED);
    }

    // 대댓글 수정 //
    @PatchMapping("/{replyId}")
    public ResponseEntity patchReply(@Valid @RequestBody ReplyPatchDto requestBody,
                                     @PathVariable("replyId") @Positive Long replyId){

        Reply reply = replyService.updateReply(replyMapper.replyPatchDtoToReply(requestBody), replyId);

        reply.setReplyId(replyId);
        ReplyResponseDto replyResponseDto = replyMapper.replyToReplyResponseDto(reply);

        return new ResponseEntity(replyResponseDto, HttpStatus.OK);
    }

    // 대댓글 조회 //
    @GetMapping("/{replyId}")
    public ResponseEntity getReply(@PathVariable("replyId") @Positive Long replyId){

        Reply reply = replyService.findReply(replyId);
        ReplyResponseDto replyResponseDto = replyMapper.replyToReplyResponseDto(reply);

        return new ResponseEntity(replyResponseDto, HttpStatus.OK);
    }

    // 대댓글 전체 조회 //
    @GetMapping
    public ResponseEntity getReplies(){
        List<Reply> replies = replyService.findReplies();
        return new ResponseEntity<>(replyMapper.repliesToRepliesResponseDto(replies), HttpStatus.OK);
    }

    // 대댓글 삭제 //
    @DeleteMapping("/{replyId}")
    public ResponseEntity removeReply(@PathVariable("replyId") @Positive Long replyId){
        replyService.deleteReply(replyId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}