package de.electi.academy.usermanagement.microservice.commentsmanagement.endpoints.v1.models;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentAddModel {
    private UUID userId;
    private String message;
}
