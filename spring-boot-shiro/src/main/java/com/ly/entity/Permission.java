package com.ly.entity;

import lombok.Data;

import java.util.List;

@Data
public class Permission {
    private Long id;

    private Long parentId;

    private String name;

    private String url;

    private Integer type;

    private Integer orderNum;

    private Byte hasDelete;

    private String createTime;

    private Integer rid;

    private List<Permission> childrenPermission;

    /**
     * 用于判断是否有权限
     */
    private Integer rpId;
    /**
     * 查询逗号分隔的权限主键
     */
    private String ids;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", orderNum=" + orderNum +
                ", hasDelete=" + hasDelete +
                ", createTime='" + createTime + '\'' +
                ", rid=" + rid +
                ", childrenPermission=" + childrenPermission +
                ", rpId=" + rpId +
                ", / nids='" + ids + '\'' +
                '}'+"\n";
    }
}