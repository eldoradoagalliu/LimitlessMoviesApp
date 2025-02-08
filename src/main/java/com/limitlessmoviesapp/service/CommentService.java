package com.limitlessmoviesapp.service;

import com.limitlessmoviesapp.model.Comment;
import com.limitlessmoviesapp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getMovieComments(Long id) {
        return commentRepository.findByMovieIdIs(id);
    }

    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
