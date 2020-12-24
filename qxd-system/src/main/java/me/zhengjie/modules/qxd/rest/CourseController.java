package me.zhengjie.modules.qxd.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.qxd.domain.Course;
import me.zhengjie.modules.qxd.service.CourseService;
import me.zhengjie.modules.qxd.service.dto.CourseQueryCriteria;
import me.zhengjie.modules.system.service.DataService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
* @author kankan
* @date 2020-12-17
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "课程管理")
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;
    private final RoleService roleService;
    private final UserService userService;
    private final DataService dataService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('course:list')")
    public void download(HttpServletResponse response, CourseQueryCriteria criteria) throws IOException {
        courseService.download(courseService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询课程")
    @ApiOperation("查询课程")
    @PreAuthorize("@el.check('course:list')")
    public ResponseEntity<Object> query(CourseQueryCriteria criteria, Pageable pageable){
        Integer currentLevel =  Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        System.out.println(SecurityUtils.getCurrentUser().toString());
        System.out.println(currentLevel);
        return new ResponseEntity<>(courseService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增课程")
    @ApiOperation("新增课程")
    @PreAuthorize("@el.check('course:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Course resources){

        return new ResponseEntity<>(courseService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改课程")
    @ApiOperation("修改课程")
    @PreAuthorize("@el.check('course:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Course resources){
        courseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除课程")
    @ApiOperation("删除课程")
    @PreAuthorize("@el.check('course:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        courseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}