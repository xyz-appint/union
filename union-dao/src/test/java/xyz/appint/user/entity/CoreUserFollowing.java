package xyz.appint.user.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by justin on 15/8/24.
 */
public class CoreUserFollowing implements Serializable {
    private int followingId;
    private int uid;
    private int followingUid;
    private byte status;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    public int getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFollowingUid() {
        return followingUid;
    }

    public void setFollowingUid(int followingUid) {
        this.followingUid = followingUid;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

}
