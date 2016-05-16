package xyz.appint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.appint.dao.UserDAO;
import xyz.appint.entity.User;
import xyz.appint.union.dao.dao.annotation.Col;
import xyz.appint.union.dao.page.CursorPage;
import xyz.appint.union.dao.page.CursorPageRequest;
import xyz.appint.union.utils.TimestampUtil;

import java.util.ArrayList;

/**
 * Created by Justin on 2014/8/11.
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public boolean updateFirstNameByUid(@Col(name = "id") int id,
                                        @Col(name = "firstName") String firstName) {
        return userDAO.updateFirstNameByUid(id, firstName);
    }

    public int count() {
        return userDAO.count();
    }

    public int queryUid(int uid) {
        return userDAO.queryUid(uid);
    }

    public User queryByName(String name) {
        return userDAO.queryByName(name);
    }

    public User queryByNames(String firstName, String lastName) {
        return userDAO.queryByNames(firstName, lastName, 0, new ArrayList<String>());
    }

    public User queryByNamesNoCol(String firstName, String lastName) {
        return userDAO.queryByNamesNoCol(firstName, lastName);
    }

    public CursorPage<User> queryUsers(int pageNumber) {
        return userDAO.queryUsers(new CursorPageRequest(pageNumber) , 1 , TimestampUtil.now());
    }

}
