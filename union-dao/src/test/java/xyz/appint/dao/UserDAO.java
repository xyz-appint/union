package xyz.appint.dao;

import org.springframework.stereotype.Repository;
import xyz.appint.entity.User;
import xyz.appint.union.dao.dao.BaseDaoSupport;
import xyz.appint.union.dao.dao.annotation.Col;
import xyz.appint.union.dao.page.CursorPage;
import xyz.appint.union.dao.page.CursorPageRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by justin on 15/3/30.
 */
@Repository
public class UserDAO extends BaseDaoSupport<User> {

    public int save(User user) {
        return this.insertAndReturnKey(user);
    }

    public boolean updateFirstNameByUid(@Col(name = "id") int id,
                                        @Col(name = "firstName") String firstName) {
        return this.update(id, firstName);
    }


    public int count() {
        return this.queryInt();
    }

    public int queryUid(int uid) {
        return this.queryInt(uid);
    }

    public User queryByName(@Col(name = "name") String name) {
        return queryEntity(name);
    }

    public User queryByNames(@Col(name = "firstName", include = Col.Include.ALWAYS) String firstName,
                             @Col(name = "lastname") String lastname,
                             @Col(name = "ts", include = Col.Include.NON_DEFAULT) long ts,
                             @Col(name = "list", include = Col.Include.NON_EMPTY) List<String> list) {
        return queryEntity(firstName, lastname, ts, list);
    }

    public User queryByNamesNoCol(String firstName,
                                  String lastname) {
        return queryEntity(firstName, lastname, 0, new ArrayList<>());
    }


    public CursorPage<User> queryUsers(CursorPageRequest pageRequest, @Col(name = "id") int id, @Col(name = "ts") Timestamp ts) {
        return queryCursorPage(pageRequest, id , ts);
    }

//    public p queryEscalatorPage() {
//
//    }
}
