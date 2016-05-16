package xyz.appint;

/**
 * Created by justin on 16/3/18.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:application_context.xml"})
//public class MockDataService extends AbstractTransactionalJUnit4SpringContextTests {
//    private static final Logger log = LoggerFactory.getLogger(MockDataService.class);
//
//    @Autowired
//    private CoreUserDAO coreUserDAO;
//
//    @Autowired
//    private CoreUserProfileDAO coreUserProfileDAO;
//
//    @Autowired
//    private CoreUserFollowingDAO coreUserFollowingDAO;
//    @Autowired
//    private CoreUserFollowerDAO coreUserFollowerDAO;
//
//    //    @Autowired
//    private MyBatisBatchItemWriter myBatisBatchItemWriter;
//
//    @Autowired
//    private DataSourceTransactionManager txManager;
//
//
//    public String encodePassword(String username, String password) {
//        return DigestUtils.md5Hex(username + password);
//    }
//
////    @Before
////    public void before() {
////        DataSourceContextHandler.setDataSourceType(DataSourceType.READ);
////    }
//
//    @Test
//    @Rollback(false)
//    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
//    public void testQueryUser() {
//        StopWatch clock = new StopWatch("query user by uid");
//        clock.start();
//        for (int i = 0; i < 100; i++) {
//            CoreUser coreUser = coreUserDAO.queryUserByUid(10000 + i);
//        }
//
//        clock.stop();
//        System.out.println(clock.prettyPrint());
//    }
//
//
//    @Test
//    @Rollback(false)
//    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
//    public void testFillUserData() {
//
//
//        for (int i = 0; i < 1; i++) {
//            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//            def.setReadOnly(false);
//            TransactionStatus status = txManager.getTransaction(def);
//
//            try {
//                int lastUid = 100;
//                for (int j = 0; j < 100; j++) {
//                    String username = UUID.randomUUID().toString().replaceAll("-", "");
//
//                    CoreUser coreUser = new CoreUser();
//                    coreUser.setUid((10000 * (i + 1)) + (j * (i + 1)));
//                    coreUser.setUsername(username);
//                    coreUser.setPassword(encodePassword(coreUser.getUsername(), username));
//                    coreUser.setCreatedTime(TimestampUtil.now());
//                    coreUser.setUpdatedTime(TimestampUtil.now());
//                    coreUser.setLastLoginTime(TimestampUtil.now());
//                    coreUser.setIsRobot((byte) 0);
//                    coreUser.setStatus((byte) 1);
//                    coreUser.setScore(new Random().nextInt((100000000 - 999) + 1) + 999);
//                    coreUser.setLevelScore(new Random().nextInt((100000000 - 999) + 1) + 999);
//                    coreUser.setLevelTitle("测试级别[" + i + "]");
//                    coreUser.setIsMentor((byte) 0);
//                    coreUser.setLevelScore(0);
//
//                    coreUserDAO.register(coreUser);
//
//                    CoreUserProfile coreUserProfile = new CoreUserProfile();
//                    coreUserProfile.setUpdatedTime(TimestampUtil.now());
//                    coreUserProfile.setCreatedTime(TimestampUtil.now());
//                    coreUserProfile.setAvatar("http://gegejie.oss-cn-shenzhen.aliyuncs.com/20160104/13753503235544aa815a13964691e507.jpg");
//                    coreUserProfile.setNickname("用户[" + coreUser.getUid() + "]号");
//                    coreUserProfile.setUid(coreUser.getUid());
//                    coreUserProfile.setGender((byte) 1);
//                    coreUserProfile.setEmail("user_" + coreUser.getUid() + "@test.com");
//                    coreUserProfile.setCompany("测试公司[" + coreUser.getUid() % 9 + "]");
//                    coreUserProfile.setHometownId(10003);
//                    coreUserProfile.setBirthday(DateTool.strToDate(DateTool.getNow()));
//                    coreUserProfile.setPhoneNumber("13" + new Random().nextInt((9 - 1) + 1) + 1 + new Random().nextInt((99999999 - 10000000) + 1) + 10000000);
//                    coreUserProfile.setIntro("谓众介并列，相继传话。古代传递宾主之言者称介。绍，继，接续。");
//                    coreUserProfileDAO.save(coreUserProfile);
//
////                    if (j % 100 == 0) {
////                        lastUid = coreUser.getUid();
////                    }
////
////                    coreUserFollowingDAO.save(lastUid, coreUser.getUid());
////                    coreUserFollowerDAO.save(coreUser.getUid(), lastUid);
//                }
//            } catch (Exception ex) {
//                txManager.rollback(status);
//                throw ex;
//            }
//            txManager.commit(status);
//
//        }
//
//
//    }
//
//}
