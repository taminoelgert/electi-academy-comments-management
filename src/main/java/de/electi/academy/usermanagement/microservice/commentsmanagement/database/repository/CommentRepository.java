package de.electi.academy.usermanagement.microservice.commentsmanagement.database.repository;

import de.electi.academy.usermanagement.microservice.commentsmanagement.database.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findCommentByUserIdEquals(UUID userId);
}
