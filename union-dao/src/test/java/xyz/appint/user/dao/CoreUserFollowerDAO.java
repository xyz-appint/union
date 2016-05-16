package xyz.appint.user.dao;

import org.springframework.stereotype.Repository;
import xyz.appint.union.dao.dao.BaseDaoSupport;
import xyz.appint.union.utils.TimestampUtil;
import xyz.appint.user.entity.CoreUserFollower;

/**
 * Created by justin on 15/8/24.
 */
@Repository
public class CoreUserFollowerDAO extends BaseDaoSupport<CoreUserFollower> {
    /**
     * 保存粉丝
     *
     * @param uid         被关注人uid
     * @param followerUid 关注人，即粉丝uid
     * @return
     */
    public boolean save(int uid, int followerUid) {
        CoreUserFollower coreUserFollower = new CoreUserFollower();
        coreUserFollower.setUid(uid);
        coreUserFollower.setFollowerUid(followerUid);
        coreUserFollower.setStatus((byte) 0);
        coreUserFollower.setCreatedTime(TimestampUtil.now());
        coreUserFollower.setUpdatedTime(TimestampUtil.now());
        return save(coreUserFollower);
    }

    public boolean save(CoreUserFollower coreUserFollower) {
        return insert(coreUserFollower);
    }

    public boolean isExists(int uid, int followerUid) {
        return this.queryInt(uid, followerUid) > 0;
    }

    /**
     * 更新粉丝状态
     *
     * @param uid
     * @param followerUid
     * @param status      1：取消关注
     * @return
     */
    public boolean updateFollowerStatus(int uid, int followerUid, int status) {
        return this.update(uid, followerUid, status);
    }

    public int countByUid(int uid) {
        return queryInt(uid);
    }
}
