package com.limitlessmoviesapp.repositories;

import com.limitlessmoviesapp.models.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAll();
    List<Comment> findByMovieIdIs(Long id);
}