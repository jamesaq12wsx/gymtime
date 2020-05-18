//package com.jamesaq12wsx.gymtime.database;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jamesaq12wsx.gymtime.model.entity.Exercise;
//import com.jamesaq12wsx.gymtime.model.SimpleExercise;
//import com.jamesaq12wsx.gymtime.model.SimpleExerciseAudit;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.stereotype.Repository;
//
//import java.sql.*;
//import java.time.LocalDateTime;
//import java.util.*;
//
//@Repository
//public class ExerciseDaoImpl implements ExerciseDao {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Autowired
//    public ExerciseDaoImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//
//    @Override
//    public List<Exercise> getSimpleAll() {
//
//        String sql = "select * from exercise";
//
//        return jdbcTemplate.query(sql, ((resultSet, i) -> {
//
//            int id = resultSet.getInt("id");
//
//            String name = resultSet.getString("name");
//            String description = resultSet.getString("description");
//            String category = resultSet.getString("category");
//
//            String imagesStr = resultSet.getString("images");
//            List<String> images = new ArrayList<>();
//            try {
//                String[] imagesArr = mapper.readValue(imagesStr, String[].class);
//                images = Arrays.asList(imagesArr);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//
//            return new SimpleExercise(id, name, description, category, images);
//
//        }));
//
//
//    }
//
//    @Override
//    public Optional<Exercise> get(UUID id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public List<Exercise> getAll() {
//
//        String sql = "select * from exercise";
//
//        return jdbcTemplate.query(sql, ((resultSet, i) -> {
//            int id = resultSet.getInt("id");
//            String name = resultSet.getString("name");
//            String description = resultSet.getString("description");
//            String category = resultSet.getString("category");
//
//            String imagesStr = resultSet.getString("images");
//            List<String> images = new ArrayList<>();
//            try {
//                String[] imagesArr = mapper.readValue(imagesStr, String[].class);
//                images = Arrays.asList(imagesArr);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//
//            String createdBy = resultSet.getString("created_by");
//            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
//            LocalDateTime updatedBy = resultSet.getTimestamp("updated_at").toLocalDateTime();
//
//            return new SimpleExerciseAudit(id, name, description, category, images, createdBy, createdAt, updatedBy);
//        }));
//
//    }
//
//    @Override
//    public void save(Exercise exercise) {
//
//        SimpleExerciseAudit exerciseAudit = (SimpleExerciseAudit) exercise;
//
//        String sqlUpdate = "insert into exercise(name, description, category, images, created_by)\n" +
//                "select \n" +
//                "       name, " +
//                "description, " +
//                "category,\n" +
//                "images,\n" +
//                "created_by \n" +
//                "from (values (?,?,?::exercise_category,?::jsonb,?)) t(name, description, category, images, created_by)";
//
////        String sqlUpdate = "insert into exercise(name, description, category, images, created_by)\n" +
////                "values (?, ?, ?::exercise_category, ?::text[], ?)\n";
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            jdbcTemplate.update(
//                    sqlUpdate,
//                    new Object[]{
//                            exerciseAudit.getName(),
//                            exercise.getDescription(),
//                            exercise.getCategory().toString(),
//                            mapper.writeValueAsString(exercise.getImages()),
//                            exerciseAudit.getCreatedBy()
//                    });
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void saveAll(List<? extends Exercise> tList) {
//
//    }
//
//    @Override
//    public void update(Exercise exercise) {
//        SimpleExerciseAudit exerciseAudit = (SimpleExerciseAudit) exercise;
//
//        String sqlUpdate = "update exercise\n" +
//                "set \n" +
//                "name = n, \n" +
//                "description = des, \n" +
//                "category = c,\n" +
//                "images = imgs,\n" +
//                "updated_at =  ua \n" +
//                "from (values (?,?,?::exercise_category,?::jsonb,?)) t(n, des, c, imgs, ua)\n" +
//                "where id= ?";
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            jdbcTemplate.update(
//                    sqlUpdate,
//                    new Object[]{
//                            exerciseAudit.getName(),
//                            exerciseAudit.getDescription(),
//                            exerciseAudit.getCategory(),
//                            mapper.writeValueAsString(exerciseAudit.getImages()),
//                            LocalDateTime.now(),
//                            exerciseAudit.getId()
//                    });
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void delete(Integer id) {
//        String deleteSql = "delete from exercise\n" +
//                "where id = ?";
//
//        jdbcTemplate.update(deleteSql, new Object[]{id});
//
//    }
//}
