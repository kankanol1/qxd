package me.zhengjie.modules.qxd.service.mapstruct;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.qxd.domain.Course;
import me.zhengjie.modules.qxd.service.dto.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author kankan
* @date 2020-12-17
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper extends BaseMapper<CourseDto, Course> {

}