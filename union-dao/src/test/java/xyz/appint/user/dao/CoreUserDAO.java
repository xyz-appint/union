package xyz.appint.user.dao;

import xyz.appint.user.entity.CoreUser;
import org.springframework.stereotype.Repository;
import xyz.appint.union.dao.dao.BaseDaoSupport;
import xyz.appint.union.dao.dao.annotation.Col;

import java.sql.Timestamp;

/**
 * Created by justin on 15/8/14.
 */
@Repository
public class CoreUserDAO extends BaseDaoSupport<CoreUser> {
    public boolean register(CoreUser coreUser) {
        return this.insertAndReturnKey(coreUser) > 0;
    }

    public boolean isExists(String username) {
        return queryInt(username) > 0;
    }

    public boolean isExistsOpenid(String openid) {
        return queryInt(openid) > 0;
    }

    public CoreUser queryUserByUsername(String username) {
        return this.queryEntity(username);
    }

    public CoreUser queryUserByUsernameAndPassword(String username, String password) {
        return queryEntity(username, password);
    }

    public void updatePassword(String username, String password) {
        update(username, password);
    }

    public void updateLastLogin(String username, int lastLoginIp, Timestamp lastLoginTime) {
        update(username, lastLoginIp, lastLoginTime);
    }

    public void updateStatus(int uid, int status) {
        update(uid, status);
    }

    public int countByDate(@Col(name = "formDate") String fromDate, @Col(name = "endDate") String endDate) {
        return queryInt(fromDate, endDate);
    }

    public void updateScore(@Col(name = "uid") int uid, @Col(name = "score") int score) {
        update(uid, score);
    }

    public String queryLevelTitleByUid(int uid) {
        return queryString(uid);
    }

    public boolean updateMentor(@Col(name = "uid") int uid, @Col(name = "isMentor") int isMentor) {
        return update(uid, isMentor);
    }

    public int countPostsByUid(int uid) {
        return queryInt(uid);
    }

    public CoreUser queryUserByUid(int uid) {
        return queryEntity(uid);
    }

    public int queryMaxUid() {
        return queryInt();
    }
}
