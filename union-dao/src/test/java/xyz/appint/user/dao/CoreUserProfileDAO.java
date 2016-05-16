package xyz.appint.user.dao;

import org.springframework.stereotype.Repository;
import xyz.appint.union.dao.dao.BaseDaoSupport;
import xyz.appint.union.dao.page.Page;
import xyz.appint.union.dao.page.PageRequest;
import xyz.appint.union.utils.StringUtils;
import xyz.appint.union.utils.TimestampUtil;
import xyz.appint.user.entity.CoreUserProfile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by justin on 15/8/14.
 */
@Repository
public class CoreUserProfileDAO extends BaseDaoSupport<CoreUserProfile> {

    public boolean isExists(int uid) {
        return this.queryInt(uid) > 0;
    }

    public boolean save(CoreUserProfile coreUserProfile) {
        return this.insert(coreUserProfile);
    }

    public boolean updateUserProfile(CoreUserProfile coreUserProfile) {
        return update(coreUserProfile);
    }

    public boolean updateUserAvatar(int uid, String avatarFileUrl) {
        return this.update(uid, avatarFileUrl);
    }

    /**
     * 查询简单用户信息：uid、nickname、gender、avatar
     *
     * @param uid
     * @return
     */
    public CoreUserProfile queryBaseProfileByUid(int uid) {
        return queryEntity(uid);
    }

    public CoreUserProfile queryProfileByUid(int uid, long ts) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        if (ts > 0) {
            params.put("ts", TimestampUtil.toTimestamp(ts));
        }
        return this.queryEntity(params);
    }

    public List<CoreUserProfile> queryUserProfileByUids(List<Integer> uidList) {
        Map<String, Object> params = new HashMap<>();
        params.put("list", uidList);
        return this.queryList(params);
    }

    public boolean updateSingleField(int uid, String field, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("field", field);
        map.put("val", value);
        return this.update(map);
    }

    /**
     * 查询系统预留用户帐号
     *
     * @param pageRequest
     * @param queryString
     * @return
     */
    public Page<CoreUserProfile> queryRobotUserPage(PageRequest pageRequest, String queryString, int isMentor) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasText(queryString)) {
            params.put("queryString", queryString.trim());
        }
        params.put("isMentor", isMentor);
        return queryNavPage(pageRequest, params);
    }

    public List<CoreUserProfile> queryAllRobotUser() {
        return this.queryList();
    }

    public String queryNameByUid(int uid) {
        return queryString(uid);
    }
}
