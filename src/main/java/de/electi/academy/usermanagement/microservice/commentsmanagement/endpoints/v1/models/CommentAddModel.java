package de.electi.academy.usermanagement.microservice.commentsmanagement.endpoints.v1.models;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentAddModel {
    UUID userId;
    String message;
}
