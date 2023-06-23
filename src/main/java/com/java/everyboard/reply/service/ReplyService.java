package com.java.everyboard.reply.service;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.comment.service.CommentService;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.service.ContentService;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.reply.repository.ReplyRepository;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.repository.UserRepository;
import com.java.everyboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CommentService commentService;
    private final ContentService contentService;

    public Reply createReply(Reply reply, Long contentId, Long commentId){
        // 등록된 게시글인지 확인
        Content content = contentService.findContent(contentId);

        // 등록된 댓글인지 확인
        Comment comment = commentService.findComment(commentId);

        // 회원가입한 유저인지 확인
        User user = userService.getLoginUser();

        reply.setUser(user);
//        comment.setNickname(user.getNickname());
        reply.setContent(content);

        return replyRepository.save(reply);
    }

    // 대댓글 수정 //
    public Reply updateReply(Reply reply, Long replyId){

        Reply findReply = findVerifiedReply(replyId);

        User writer = userService.findUser(findReply.getUser().getUserId()); // 작성자 찾기
        if (userService.getLoginUser().getUserId() != writer.getUserId()) // 작성자와 로그인한 사람이 다를 경우
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);

        Optional.ofNullable(reply.getReply())
                .ifPresent(findReply::setReply);

        return replyRepository.save(findReply);
    }

    public Reply findReply(long replyId){ return findVerifiedReply(replyId);}

    public List<Reply> findReplies(){
        return replyRepository.findAll(Sort.by("replyId").descending());
    }

    // 대댓글 삭제 //
    public void deleteReply(long replyId){
        Reply findReply = findVerifiedReply(replyId);

        User writer = userService.findUser(findReply.getUser().getUserId()); // 작성자 찾기
        if (userService.getLoginUser().getUserId() != writer.getUserId()) // 작성자와 로그인한 사람이 다를 경우
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);

        replyRepository.delete(findReply);
    }

    public Reply findVerifiedReply(long replyId){
        Optional<Reply> optionalReply = replyRepository.findById(replyId);

        Reply findReply =
                optionalReply.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

        return findReply;
    }
}
