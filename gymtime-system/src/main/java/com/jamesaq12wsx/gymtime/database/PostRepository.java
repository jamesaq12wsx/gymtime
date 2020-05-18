package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from post where created_by = ?1", nativeQuery = true)
    List<Post> findAllByUsername(String username);

    @Query(value = "select * from post where created_by = ?1 and cast(extract(year from post_time) as text) = ?2", nativeQuery = true)
    List<Post> findAllByAudit_CreatedByAndYear(String username, String year);

}
