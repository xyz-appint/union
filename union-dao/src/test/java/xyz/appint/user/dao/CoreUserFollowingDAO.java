package xyz.appint.user.dao;

import org.springframework.stereotype.Repository;
import xyz.appint.union.dao.dao.BaseDaoSupport;
import xyz.appint.union.utils.TimestampUtil;
import xyz.appint.user.entity.CoreUserFollowing;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by justin on 15/8/24.
 */
@Repository
public class CoreUserFollowingDAO extends BaseDaoSupport<CoreUserFollowing> {
    /**
     * 保存粉丝
     *
     * @param uid          关注人uid
     * @param followingUid 被关注人uid
     * @return
     */
    public boolean save(int uid, int followingUid) {
        CoreUserFollowing coreUserFollowing = new CoreUserFollowing();
        coreUserFollowing.setUid(uid);
        coreUserFollowing.setFollowingUid(followingUid);
        coreUserFollowing.setStatus((byte) 0);
        coreUserFollowing.setCreatedTime(TimestampUtil.now());
        coreUserFollowing.setUpdatedTime(TimestampUtil.now());
        return save(coreUserFollowing);
    }

    public boolean save(CoreUserFollowing coreUserFollowing) {
        return insert(coreUserFollowing);
    }

    public boolean isExists(int uid, int followingUid) {
        return this.queryInt(uid, followingUid) > 0;
    }

    /**
     * ing
     * 更新关注状态
     *
     * @param uid
     * @param followingUid
     * @param status       1：取消关注
     * @return
     */
    public boolean updateFollowingStatus(int uid, int followingUid, int status) {
        return this.update(uid, followingUid, status);
    }

    public int countByUid(int uid) {
        return this.queryInt(uid);
    }


    public boolean isFollowing(int fromUid, int uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("fromUid", fromUid);
        map.put("uid", uid);
        return queryInt(map) > 0;
    }
}
