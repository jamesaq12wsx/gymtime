package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.Post;
import com.jamesaq12wsx.gymtime.model.entity.SimplePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<SimplePost, UUID> {

    List<Post> findAllByUserEmail(String username);

    @Query(value = "select * from post where created_by = ?1 and cast(extract(year from post_time) as text) = ?2", nativeQuery = true)
    List<Post> findAllByAudit_CreatedByAndYear(String username, String year);

}
