/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.qxd.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @website https://el-admin.vip
* @description /
* @author kankan
* @date 2020-12-12
**/
@Entity
@Data
@Table(name="sys_course")
public class Courses implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    @ApiModelProperty(value = "ID")
    private Long courseId;

    @Column(name = "course_name")
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @CreationTimestamp
    @Column(name = "create_time",insertable =false, updatable = false)
    @ApiModelProperty(value = "创建日期", hidden = true)
    private Timestamp createTime;

    @UpdateTimestamp
    @Column(name = "checker_time")
    @ApiModelProperty(value = "更新时间", hidden = true)
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


    @UpdateTimestamp
    @Column(name = "deadline_time")
    @ApiModelProperty(value = "课程预售截止日期", hidden = true)
    private Timestamp deadlineTime;

    @Column(name = "money")
    @ApiModelProperty(value = "课程金额")
    private Double money;

    @Column(name = "pre_money")
    @ApiModelProperty(value = "预售金额")
    private Double preMoney;

    @Column(name = "commission")
    @ApiModelProperty(value = "单人次佣金")
    private Double commission;

    @Column(name = "course_form")
    @ApiModelProperty(value = "开课形式")
    private Long courseForm;

    @Column(name = "source_url")
    @ApiModelProperty(value = "资源链接")
    private String sourceUrl;

    @Column(name = "accout_number")
    @ApiModelProperty(value = "开课老师账号")
    private String accoutNumber;

    @Column(name = "contact_way")
    @ApiModelProperty(value = "紧急联系方式")
    private String contactWay;

    @Column(name = "pay_way")
    @ApiModelProperty(value = "付费方式")
    private String payWay;

    @Column(name = "enabled")
    @ApiModelProperty(value = "状态：1启用、0禁用")
    private Boolean enabled;

    @Column(name = "course_sort")
    @ApiModelProperty(value = "排序")
    private Integer courseSort;

    public void copy(Courses source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}