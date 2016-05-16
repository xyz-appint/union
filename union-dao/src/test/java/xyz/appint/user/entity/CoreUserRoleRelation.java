package xyz.appint.user.entity;

import java.io.Serializable;

/**
 * Created by justin on 16/3/3.
 */
public class CoreUserRoleRelation implements Serializable {
    private int id;
    private String roleId;
    private int uid;

    public CoreUserRoleRelation() {

    }

    public CoreUserRoleRelation(int uid, String roleId) {
        this.uid = uid;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}
