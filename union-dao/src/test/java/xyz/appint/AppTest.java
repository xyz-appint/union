package xyz.appint;

import com.alibaba.fastjson.JSON;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.codec.digest.DigestUtils;
import xyz.appint.union.utils.DESUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public String replaceUserName(String email) {
        String userName = email.substring(0, email.indexOf("@"));
        StringBuilder sb = new StringBuilder(userName);
        for (int i = 0; i < userName.length(); i++) {
            if (i > 2) {
                break;
            }
            sb.setCharAt(i, '*');
        }
        return sb.toString() + email.substring(email.indexOf("@"), email.length());
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws Exception {
//        System.err.println(replaceUserName("123213123@test.com"));
//
//        System.err.println();
//
//        String email = "13499992222";
//        Pattern p = Pattern.compile("(\\w{3})(\\w+)(\\w{4})");
//        Matcher m = p.matcher(email);
//        System.out.println(m.replaceAll("$1****$3"));
//
//
//        assertTrue(true);
//
//
//        System.err.println("======" + Defaults.defaultValue(Timestamp.class));

        System.err.println("======>" + DESUtil.encrypt("100031", "GEGEJIE2015"));
//        System.err.println("======>" + DESUtil.decrypt("AJJU1a6U0CE=", "GEGEJIE2015"));

        System.err.println(UUID.randomUUID());

        long a = 6515587872L;
        long b = 28004;
        System.err.println(((a + b) % 2999) + 10000001);


        String api_ticket = "E0o2-at6NcC2OsJiQTlwlOBW_sr1vgtyte8m7InR9JG4RTUjjncHiEb8s5ECpgwFwKADsHNilxw-3nkD4aywBA";
        String code = "";
        String openId = "o42stuHKIJ-8ZLNaxIvSEE3KN6co";
        int timestamp = 1461147547;
        String nonce_str = "b078e1ed-9acc-4031-b5f4-f95fe96";
        String cardId = "pYBq7s4iEv-luQnRjjN0oerzMnck";
        String[] array = { api_ticket, code, openId, String.valueOf(timestamp), nonce_str, cardId };
        Arrays.sort(array);
        String signStr = String.join("", array);
        System.err.println(signStr);

        System.err.println(nonce_str.length());


        Map<String, String> cardExt = new HashMap<>();
        cardExt.put("code", code);
        cardExt.put("openid", openId);
        cardExt.put("timestamp", String.valueOf(timestamp));
        cardExt.put("nonce_str", nonce_str);
        cardExt.put("signature", DigestUtils.sha1Hex(signStr));
        cardExt.put("outer_id", "0");

        System.err.println(JSON.toJSONString(cardExt));



    }
}
