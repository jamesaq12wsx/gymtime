package com.jamesaq12wsx.gymtime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesaq12wsx.gymtime.model.Exercise;
import com.jamesaq12wsx.gymtime.model.SimpleExercise;
import com.jamesaq12wsx.gymtime.model.SimpleExerciseAudit;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class jsonSerializeTest {

    @Test
    public void testSerialize() throws JsonProcessingException {
        List<Exercise> exerciseList = new ArrayList<>();

        exerciseList.add(new SimpleExercise(1,"test", "test", "test"));
        exerciseList.add(new SimpleExerciseAudit(2,"test", "test", "test", "test", LocalDateTime.now(), LocalDateTime.now()));

        ObjectMapper mapper = new ObjectMapper();

        String jsonResult = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString( exerciseList);

        System.out.println(jsonResult);
    }

}
