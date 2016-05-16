package xyz.appint.user.form;

/**
 * Created by justin on 15/8/14.
 */
public class User {
    private int uid;
    private String username;
    private int score;
    private int levelScore;
    private String levelTitle;
    private String openid;
    private int status;
    private int isMentor;

    public int getIsMentor() {
        return isMentor;
    }

    public void setIsMentor(int isMentor) {
        this.isMentor = isMentor;
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevelScore(int levelScore) {
        this.levelScore = levelScore;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getLevelScore() {
        return levelScore;
    }

    public String getOpenid() {
        return openid;
    }
}
