package de.electi.academy.usermanagement.microservice.commentsmanagement.service.v1.impl;

import de.electi.academy.usermanagement.microservice.commentsmanagement.database.entity.Comment;
import de.electi.academy.usermanagement.microservice.commentsmanagement.database.repository.CommentRepository;
import de.electi.academy.usermanagement.microservice.commentsmanagement.endpoints.v1.models.CommentAddModel;
import de.electi.academy.usermanagement.microservice.commentsmanagement.endpoints.v1.models.CommentResponseModel;
import de.electi.academy.usermanagement.microservice.commentsmanagement.service.v1.api.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<CommentResponseModel> list(UUID userId) {
        List<Comment> comments = commentRepository.findCommentByUserIdEquals(userId);
        return comments.stream().map(comment -> new CommentResponseModel(comment.getMessage(), comment.getCreationDate())).sorted(Comparator.comparingLong(c -> c.getCreationDate().getTime())).collect(Collectors.toList());
    }

    @Override
    public void delete(UUID userId) {
        List<Comment> comments = commentRepository.findCommentByUserIdEquals(userId);
        commentRepository.deleteAll(comments);
    }

    @Override
    public List<CommentResponseModel> add(CommentAddModel commentAddModel) {
        if(commentAddModel.getUserId() == null) throw new IllegalArgumentException("UserId cannot be null!");
        if(commentAddModel.getMessage() == null || commentAddModel.getMessage().isEmpty()) throw new IllegalArgumentException("Message cannot be null or empty!");
        Comment newComment = new Comment();
        newComment.setMessage(commentAddModel.getMessage());
        newComment.setCreationDate(new Date());
        newComment.setUserId(commentAddModel.getUserId());
        commentRepository.save(newComment);
        List<Comment> comments = commentRepository.findCommentByUserIdEquals(commentAddModel.getUserId());
        return comments.stream().map(comment -> new CommentResponseModel(comment.getMessage(), comment.getCreationDate())).sorted(Comparator.comparingLong(c -> c.getCreationDate().getTime())).collect(Collectors.toList());
    }
}
