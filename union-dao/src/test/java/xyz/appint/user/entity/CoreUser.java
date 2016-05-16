package xyz.appint.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by justin on 15/8/14.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoreUser implements Serializable {
    private int uid;
    private String username;
    private String password;
    private int score;
    private int levelScore;
    private String levelTitle;
    private Byte status;
    private Timestamp lastLoginTime;
    private int lastLoginIp;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private String openid;
    private Byte isRobot;
    private Byte isMentor;

    public Byte getIsMentor() {
        return isMentor;
    }

    public void setIsMentor(Byte isMentor) {
        this.isMentor = isMentor;
    }

    public Byte getIsRobot() {
        return isRobot;
    }

    public void setIsRobot(Byte isRobot) {
        this.isRobot = isRobot;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(int lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevelScore() {
        return levelScore;
    }

    public void setLevelScore(int levelScore) {
        this.levelScore = levelScore;
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }
}
