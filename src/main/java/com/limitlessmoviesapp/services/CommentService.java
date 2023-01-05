package com.limitlessmovies.services;

import com.limitlessmovies.models.Comment;
import com.limitlessmovies.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepo;

    public CommentService(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    public List<Comment> getAllComments(){
        return commentRepo.findAll();
    }

    public List<Comment> getMovieComments(Long id){
        return commentRepo.findByMovieIdIs(id);
    }

    public void updateComment(Comment comment) {
        commentRepo.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepo.delete(comment);
    }
}