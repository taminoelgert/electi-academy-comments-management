package de.electi.academy.usermanagement.microservice.commentsmanagement.endpoints.v1.rest;

import de.electi.academy.usermanagement.microservice.commentsmanagement.endpoints.v1.models.CommentAddModel;
import de.electi.academy.usermanagement.microservice.commentsmanagement.service.v1.api.CommentService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/comment")
public class CommentController {

    private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    //lists all comments of user
    @GetMapping()
    public ResponseEntity<?> list(@RequestParam(name = "userId") UUID userId){
        try{
            return new ResponseEntity<>(commentService.list(userId), HttpStatus.OK);
        }catch (Exception e){
            LOG.error("Could not list all comments of user {}", userId, e);
            return new ResponseEntity<>("Could not list all comments: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //adds comment to user
    @PostMapping()
    public ResponseEntity<?> add(@RequestBody CommentAddModel commentPostModel){
        try{
            return new ResponseEntity<>(commentService.add(commentPostModel), HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            LOG.error("Could not add comment to user {}", commentPostModel.getUserId(), e);
            return new ResponseEntity<>("Could not add comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //deletes comments of user
    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam(name = "userId") UUID userId){
        try{
            commentService.delete(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            LOG.error("Could not delete comment of user {}",userId, e);
            return new ResponseEntity<>("Could not delete comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
