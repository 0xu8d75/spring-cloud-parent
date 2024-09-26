package com.u8d75.user.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author create by  develop team
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id; // 主键
    private String username; // 用户名
    private Long createTime; // 创建时间
    private Long updateTime; // 操作时间
    private Long operator; // 操作人员
    private String operatorName; // 操作人姓名

}
