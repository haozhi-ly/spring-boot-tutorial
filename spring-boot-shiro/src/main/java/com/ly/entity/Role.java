package com.ly.entity;

import java.util.Date;

public class Role {
    private Long id;

    private String roleName;

    private String remark;

    private Byte hasDelete;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getHasDelete() {
        return hasDelete;
    }

    public void setHasDelete(Byte hasDelete) {
        this.hasDelete = hasDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}