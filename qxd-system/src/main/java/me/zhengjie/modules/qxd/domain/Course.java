package me.zhengjie.modules.qxd.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @description /
* @author kankan
* @date 2020-12-17
**/
@Entity
@Data
@Table(name="sys_course")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    @ApiModelProperty(value = "ID")
    private Long courseId;

    @Column(name = "course_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @Column(name = "checker_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "审核时间")
    private Timestamp checkerTime;

    @Column(name = "owner")
    @ApiModelProperty(value = "拥有者")
    private Long owner;

    @Column(name = "checker")
    @ApiModelProperty(value = "审核者")
    private Long checker;

    @Column(name = "cover")
    @ApiModelProperty(value = "课程封面")
    private String cover;

    @Column(name = "introduction")
    @ApiModelProperty(value = "课程介绍")
    private String introduction;

    @Column(name = "deadline_time")
    @ApiModelProperty(value = "停售时间")
    private Timestamp deadlineTime;

    @Column(name = "money",nullable = false)
    @NotNull
    @ApiModelProperty(value = "课程金额")
    private Double money;

    @Column(name = "pre_money",nullable = false)
    @NotNull
    @ApiModelProperty(value = "预售金额")
    private Double preMoney;

    @Column(name = "commission",nullable = false)
    @NotNull
    @ApiModelProperty(value = "单次佣金")
    private Double commission;

    @Column(name = "course_form",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "开课形式")
    private String courseForm;

    @Column(name = "source_url",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "资源链接")
    private String sourceUrl;

    @Column(name = "accout_number",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "老师账号")
    private String accoutNumber;

    @Column(name = "contact_way",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "联系方式")
    private String contactWay;

    @Column(name = "pay_way",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "付费方式")
    private String payWay;

    @Column(name = "enabled",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "课程状态")
    private String enabled;

    @Column(name = "course_sort")
    @ApiModelProperty(value = "排序")
    private Long courseSort;

    @Column(name = "check_status")
    @ApiModelProperty(value = "审核状态")
    private String checkStatus;

    public void copy(Course source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}