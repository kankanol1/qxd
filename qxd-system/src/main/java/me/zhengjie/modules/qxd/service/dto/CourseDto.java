package me.zhengjie.modules.qxd.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @description /
* @author kankan
* @date 2020-12-17
**/
@Data
public class CourseDto implements Serializable {

    /** ID */
    private Long courseId;

    /** 课程名称 */
    private String courseName;

    /** 创建日期 */
    private Timestamp createTime;

    /** 审核时间 */
    private Timestamp checkerTime;

    /** 拥有者 */
    private Long owner;

    /** 审核者 */
    private Long checker;

    /** 课程封面 */
    private String cover;

    /** 课程介绍 */
    private String introduction;

    /** 停售时间 */
    private Timestamp deadlineTime;

    /** 课程金额 */
    private Double money;

    /** 预售金额 */
    private Double preMoney;

    /** 单次佣金 */
    private Double commission;

    /** 开课形式 */
    private String courseForm;

    /** 资源链接 */
    private String sourceUrl;

    /** 老师账号 */
    private String accoutNumber;

    /** 联系方式 */
    private String contactWay;

    /** 付费方式 */
    private String payWay;

    /** 课程状态 */
    private String enabled;

    /** 排序 */
    private Long courseSort;

    /** 审核状态 */
    private String checkStatus;
}