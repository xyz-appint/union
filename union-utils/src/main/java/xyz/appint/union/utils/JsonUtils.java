package xyz.appint.union.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.*;
import java.util.Map.Entry;

public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    //private static SimpleModule jsonModule = new SimpleModule("jsonModule");
    static {
        // jsonModule.addSerializer(IResult.class , new ResultSerializer());
        // mapper.registerModule(jsonModule);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES , false);

    }


    public static String toPrettyJSONString(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to serialize to json: " + object, e);
        }
        return new String();
    }

    public static String toJSONString(Object object) {
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, object);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to serialize to json: " + object, e);
            return null;
        }
        return writer.toString();
    }

    public static <T> T parse(String json, Class<T> type) {
        T object;
        try {
            object = mapper.readValue(json, type);
        } catch (RuntimeException e) {
            log.error("Runtime exception during deserializing ");
            throw e;
        } catch (Exception e) {
            log.error("Exception during deserializing[" + json + "]", e);
            return null;
        }
        return object;
    }

    public static List<Map<String, Object>> toArray(JsonParser jp) {
        Assert.notNull(jp);
        try {
            return mapper.readValue(jp, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            log.error("Runtime exception ", e);
        }
        return null;
    }

    public static <T> List<T> toArray(JsonParser jp, Class<T> type) {
        Assert.notNull(jp);
        try {
            return mapper.readValue(jp, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            log.error("Runtime exception ", e);
        }
        return null;
    }

    public static List<Map<String, Object>> toArray(String jsonArrayString) {
        Assert.notNull(jsonArrayString);
        try {
            return mapper.readValue(jsonArrayString, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            log.error("Runtime exception", e);
        }
        return null;
    }


    public static void main(String args[]) {
        String json = "{\"sn\":\"12345678\",\"cmd\":\"Innings\",\"args\":[{\"levelScore\":\"1900\",\"playerId\":\"402881f738b288da0138e04e070c051c\",\"score\":\"2000\",\"win\":\"1\"},{\"lose\":\"1\",\"levelScore\":\"-1000\",\"playerId\":\"402881eb31f455b20131f618fc72022c\",\"score\":\"-1000\"},{\"lose\":\"1\",\"levelScore\":\"-1000\",\"playerId\":\"402881eb3357b541013357b546a00003\",\"score\":\"-1000\"}],\"gameId\":\"lord\",\"seq\":\"7B22636F6465223A\",\"roomId\":\"lord_room_0\",\"endTime\":\"2013-01-05 00:03:00\",\"matchTime\":\"100\",\"deskId\":\"lord_room_0.desk1_0\"}";
        long start = System.currentTimeMillis();
        JsonNode node = JsonUtils.parse(json, JsonNode.class);
        //JsonNode argsNode = JsonUtils.parse(node.get("data").toString(),JsonNode.class);
        //String arg = node.get("args").toString();
        try {
            //System.out.println("=============" + node.get("args").traverse());
            List<Map<String, Object>> destMap = mapper.readValue(node.get("args").traverse(), new TypeReference<List<Map<String, Object>>>() {
            });
            for (Map<String, Object> map : destMap) {
                for (String obj : map.keySet()) {
                    System.out.println(obj + "=======" + map.get(obj));
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.err.println(System.currentTimeMillis() - start);

		/*
        start = System.currentTimeMillis();
		for(int i = 0; i < 10000; i++) {
			JSONObject jsonObject = JSONObject.fromObject( json );
            jsonObject.getString("code");
        	jsonObject.getString("sn");
        	jsonObject.getJSONArray("data");
		}
	    System.err.println(System.currentTimeMillis() - start);
	    */
    }

    public static void main1(String args[]) {
        String noname = "没有名字";

        System.out.println(JsonUtils.toJSONString(noname));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "test");


        String json = JsonUtils.toJSONString(map);
        System.out.println(json);
        System.out.println(JsonUtils.parse(json, Map.class));


        String msg = "{\"cmd\": \"uCoin\",\"args\": [{\"nickname\": \"昵称\",\"loginId\": \"loginId\",\"avatar\": \"avatar\"}]}";

        JsonNode node = JsonUtils.parse(msg, JsonNode.class);
        System.out.println(node.toString());

        System.out.println(node.get("cmd").asText());

        System.out.println(JsonUtils.parse(node.get("args").toString(),
                JsonNode.class));

        node = JsonUtils.parse(msg, JsonNode.class);
        System.out.println(node.toString());
        System.out.println(node.get("cmd").asText());

        JsonNode argsNode = JsonUtils.parse(node.get("args").toString(),
                JsonNode.class);
        System.out.println(argsNode.toString());
        List<JsonNode> argsList = node.findValues("args");
        System.out.println("size：" + argsList.size());

		/*
		 * Entry<String, JsonNode> fields = ""; while
		 * (element.getFields().hasNext()){ Entry<String, JsonNode> el =
		 * element.getFields().next();
		 * 
		 * fields = element.getFields().next();
		 * System.out.println(node.get("args").getFields().next());
		 * System.out.println(fieldname.getTextValue()); } JsonNode fieldname =
		 * element.get("playerId");
		 * System.out.println(fieldname.getTextValue());
		 */

        JsonNode results = node.get("args");
        System.out.println(results.isArray());
        // loop until token equal to "}"
        for (JsonNode element : results) {
            Iterator<Entry<String, JsonNode>> elementsIterator = element
                    .fields();
            // System.out.println("name:" + elementsIterator.hasNext());
            while (elementsIterator.hasNext()) {
                Entry<String, JsonNode> el = elementsIterator.next();
                String name = el.getKey();
                System.out.println(name + "：" + el.getValue());
            }

        }

        System.out.println("===============================================");
        msg = "{\"cmd\" : \"innings\",\"id\" : \"对局ID\",\"gameId\" : \"游戏ID\",\"roomId\" : \"房间Id\",\"deskId\" : \"桌子号\",\"matchTime\" : \"对局时长\",\"args\":[{\"playerId\":\"play001\",\"score\":\"分数\"},{\"playerId\":\"play002\",\"score\":\"分数\"},{\"playerId\":\"play003\",\"score\":\"分数\"}]}";

        Map<String, Object> userData = new HashMap<String, Object>();
        List<Map<String, String>> names = new ArrayList<Map<String, String>>();
        Map<String, String> nameStruct = new HashMap<String, String>();
        nameStruct.put("first", "Joe");
        nameStruct.put("last", "Sixpack");
        names.add(nameStruct);

        Map<String, String> nameStruct2 = new HashMap<String, String>();
        nameStruct2.put("first", "Joe2");
        nameStruct2.put("last", "Sixpack2");
        names.add(nameStruct2);

        userData.put("name", names);
        userData.put("gender", "MALE");
        userData.put("verified", Boolean.FALSE);
        userData.put("userImage", "Rm9vYmFyIQ==");
        userData.put("object", nameStruct2);

        System.out.println(JsonUtils.toJSONString(userData));
        msg = JsonUtils.toJSONString(userData);

        System.out.println("------------------------------------------------------------");
        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("name", names);

        System.out.println(JsonUtils.toJSONString(data1));
        msg = JsonUtils.toJSONString(data1);
    }
}