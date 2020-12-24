package me.zhengjie.modules.qxd.service.impl;

import me.zhengjie.config.FileProperties;
import me.zhengjie.modules.qxd.domain.Course;
import me.zhengjie.modules.security.service.OnlineUserService;
import me.zhengjie.modules.security.service.UserCacheClean;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.mapstruct.UserMapper;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.qxd.repository.CourseRepository;
import me.zhengjie.modules.qxd.service.CourseService;
import me.zhengjie.modules.qxd.service.dto.CourseDto;
import me.zhengjie.modules.qxd.service.dto.CourseQueryCriteria;
import me.zhengjie.modules.qxd.service.mapstruct.CourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

/**
* @description 服务实现
* @author kankan
* @date 2020-12-17
**/
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileProperties properties;
    private final RedisUtils redisUtils;
    private final UserCacheClean userCacheClean;
    private final OnlineUserService onlineUserService;

    @Override
    public Map<String,Object> queryAll(CourseQueryCriteria criteria, Pageable pageable){
        Page<Course> page = courseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(courseMapper::toDto));
    }

    @Override
    public List<CourseDto> queryAll(CourseQueryCriteria criteria){
        return courseMapper.toDto(courseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CourseDto findById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseGet(Course::new);
        ValidationUtil.isNull(course.getCourseId(),"Course","courseId",courseId);
        return courseMapper.toDto(course);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CourseDto create(Course resources) {


        return courseMapper.toDto(courseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Course resources) {
        Course course = courseRepository.findById(resources.getCourseId()).orElseGet(Course::new);
        ValidationUtil.isNull( course.getCourseId(),"Course","id",resources.getCourseId());
        course.copy(resources);
        courseRepository.save(course);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long courseId : ids) {
            courseRepository.deleteById(courseId);
        }
    }

    @Override
    public void download(List<CourseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CourseDto course : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("课程名称", course.getCourseName());
            map.put("创建日期", course.getCreateTime());
            map.put("审核时间", course.getCheckerTime());
            map.put("拥有者", course.getOwner());
            map.put("审核者", course.getChecker());
            map.put("课程封面", course.getCover());
            map.put("课程介绍", course.getIntroduction());
            map.put("停售时间", course.getDeadlineTime());
            map.put("课程金额", course.getMoney());
            map.put("预售金额", course.getPreMoney());
            map.put("单次佣金", course.getCommission());
            map.put("开课形式", course.getCourseForm());
            map.put("资源链接", course.getSourceUrl());
            map.put("老师账号", course.getAccoutNumber());
            map.put("联系方式", course.getContactWay());
            map.put("付费方式", course.getPayWay());
            map.put("课程状态", course.getEnabled());
            map.put("排序", course.getCourseSort());
            map.put("审核状态", course.getCheckStatus());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    /*@Transactional(rollbackFor = Exception.class)
    public Map<String, String> updateCover(MultipartFile multipartFile) {
        Course course = courseRepository.findById(1L);
        File file = FileUtil.upload(multipartFile, properties.getPath().getCover());
        course.setCover("/cover/"+file.getName());
        course.setCover(file.getName());
        courseRepository.save(course);
        return new HashMap<String, String>(1) {{
            put("cover", file.getName());
        }};
    }*/


}