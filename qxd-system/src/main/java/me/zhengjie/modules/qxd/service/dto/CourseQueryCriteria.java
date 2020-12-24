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
package me.zhengjie.modules.qxd.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @author kankan
* @date 2020-12-17
**/
@Data
public class CourseQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String courseName;

    /** 精确 */
    @Query
    private String courseForm;

    /** 精确 */
    @Query
    private String payWay;

    /** 精确 */
    @Query
    private String enabled;

    /** 精确 */
    @Query
    private String checkStatus;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> checkerTime;
}