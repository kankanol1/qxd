package me.zhengjie.modules.qxd.repository;

import me.zhengjie.modules.qxd.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author kankan
* @date 2020-12-17
**/
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
}