package xyz.appint.service.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;
import xyz.appint.AbstractTestCase;
import xyz.appint.entity.User;
import xyz.appint.service.UserService;
import xyz.appint.union.dao.page.CursorPage;
import xyz.appint.union.utils.JsonUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest extends AbstractTestCase {

    @Resource
    private UserService userService;

    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void testQueryUid() {
        int uid = userService.queryUid(1);
        assertTrue(uid == 1);
    }


    @Test
    public void testCount() {
        int qty = userService.count();
        assertTrue(qty > 0);
    }

    public int rand() {
        int max = 1;
        int min = 10;
        double d = Math.random();
        return (int) (d * (max - min) + min);
    }


    @Test
    public void testUpdateFirstNameByUid() {
        assertTrue(userService.updateFirstNameByUid(2, "test"));
        User user = userService.queryByNames("test", "Smith");
        assertNotNull(user);
        System.err.println(JsonUtils.toJSONString(user));
    }

    @Test
    public void testThreadPool() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("CURD");
        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            Future<String> future = threadPool.submit(new StringTask());
            if (future.get() != null) {
                countDownLatch.countDown();
            }
        }
        countDownLatch.await();
        threadPool.shutdown();
        stopWatch.stop();
        System.err.println("共消耗:" + stopWatch.getLastTaskTimeMillis() + "毫秒");

    }

    private final class StringTask implements Callable<String> {
        int index = rand();
        public String call() {
            switch (index % 2) {
                case 0:
                    userService.queryUsers(13);
                case 1:
                    userService.queryByNamesNoCol("Bob", "Smith");
                default:
//                    userService.updateFirstNameByUid(2, "test");
            }
            return String.valueOf(userService.queryUsers(10));
        }
    }

    @Test
    public void testQueryByName() {
        User user = userService.queryByName("Bob");
        assertNotNull(user);
        System.err.println(JsonUtils.toJSONString(user));
    }

    @Test
    public void testQueryByNames() {
        User user = userService.queryByNames("Bob", "Smith");
        assertNotNull(user);
        System.err.println(JsonUtils.toJSONString(user));
    }

    @Test
    public void testQueryByNamesNoCol() {
        User user = userService.queryByNamesNoCol("Bob", "Smith");
        assertNotNull(user);
        System.err.println(JsonUtils.toJSONString(user));
    }

    @Test
    public void testQueryUserPage() {
        CursorPage<User> userPage = userService.queryUsers(13);
        System.err.println(JsonUtils.toJSONString(userPage));
    }

    @Test
    public void testThreadCurd() {
        StopWatch stopWatch = new StopWatch();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            final int index = i;
            cachedThreadPool.execute(new Runnable() {
                public void run() {

                }
            });
        }
        System.err.println(stopWatch.getTotalTimeSeconds());
    }

    @Test
    public void testQueryUsers() {

    }

}