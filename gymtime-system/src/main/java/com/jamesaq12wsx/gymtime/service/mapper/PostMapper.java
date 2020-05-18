package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.base.BaseMapper;
import com.jamesaq12wsx.gymtime.model.entity.Post;
import com.jamesaq12wsx.gymtime.service.dto.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {FitnessClubMapper.class, PostRecordMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper extends BaseMapper<PostDto, Post> {
}
