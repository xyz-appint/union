package xyz.appint.user.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by justin on 15/8/24.
 */
public class CoreUserFollower implements Serializable {
    private int followerId;
    private int uid;
    private int followerUid;
    private byte status;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFollowerUid() {
        return followerUid;
    }

    public void setFollowerUid(int followerUid) {
        this.followerUid = followerUid;
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
