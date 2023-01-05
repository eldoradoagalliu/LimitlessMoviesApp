package com.limitlessmoviesapp.services;

import com.limitlessmoviesapp.models.Comment;
import com.limitlessmoviesapp.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepo;

    public CommentService(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    public List<Comment> getAllComments() {
        return commentRepo.findAll();
    }

    public List<Comment> getMovieComments(Long id) {
        return commentRepo.findByMovieIdIs(id);
    }

    public void updateComment(Comment comment) {
        commentRepo.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepo.delete(comment);
    }
}