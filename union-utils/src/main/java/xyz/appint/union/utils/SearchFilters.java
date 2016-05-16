package xyz.appint.union.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;


public class SearchFilters {
    private static final Map<String, String> ops = new HashMap<String, String>();
    private String groupOp;
    private JsonNode rules;
    private String siteId;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public SearchFilters() {
        ops.put("eq", " = ");
        ops.put("ne", " <> ");
        ops.put("lt", " < ");
        ops.put("le", " <= ");
        ops.put("gt", " > ");
        ops.put("ge", " >= ");
        ops.put("bw", " LIKE ");
        ops.put("bn", " NOT LIKE ");
        ops.put("in", " IN ");
        ops.put("ni", " NOT IN ");
        ops.put("ew", " LIKE ");
        ops.put("en", " NOT LIKE ");
        ops.put("cn", " LIKE ");
        ops.put("nc", " NOT LIKE ");
        ops.put("nu", " IS NULL ");
        ops.put("nn", " IS NOT NULL ");
    }

    public void parser(String filters) {
        if (filters != null && filters.equals("") == false) {
            JsonNode node = JsonUtils.parse(filters, JsonNode.class);
            if (node != null && node.size() > 0) {
                if (node.has("groupOp")) {
                    this.setGroupOp(node.get("groupOp").asText());
                }
                rules = node.get("rules");
            }
        }
    }

    public String genData(String op, String d) {
        if (op.equals("bw") || op.equals("bn")) {
            return "'" + d + "%'";
        } else if (op.equals("ew") || op.equals("en")) {
            return "'%" + d + "'";
        } else if (op.equals("cn") || op.equals("nc")) {
            return "'%" + d + "%'";
        } else {
            return "'" + d + "'";
        }
    }

    public String getWhere(String filters, String prefix) {
        parser(filters);
        StringBuffer str = new StringBuffer();
        if (rules != null && rules.size() > 0) {
            String op = "";
            String data = "";
            String field = "";
            for (int i = 0; i < rules.size(); i++) {
                JsonNode j = rules.get(i);
                if (j.has("op") == false) {
                    op = ops.get("eq");
                } else {
                    op = ops.get(j.get("op").asText());
                }
                if (!j.has("data")) {
                    continue;
                }
                data = genData(j.get("op").asText(), j.get("data").asText());
                if (j.get("data").asText() == null || j.get("data").asText().equals("")) {
                    continue;
                }
                if (prefix != null && prefix.equals("") == false) {
                    field = prefix + "." + j.get("field").asText();
                } else {
                    field = j.get("field").asText();
                }
                if (op.equals("in") || op.equals("ni")) {
                    str.append(field + op + " (" + data + ")");
                } else {
                    str.append(field + op + data);
                }
                if (i < rules.size() - 1) {
                    str.append(" " + groupOp + " ");
                }
            }
        }
        return str.toString();
    }

    public String getWhere(String searchField, String searchString, String searchOper, String prefix) {
        if (searchField == null || searchOper == null || searchField.equals("") || searchOper.equals("")) {
            return "";
        }
        StringBuffer str = new StringBuffer();
        String op = "";
        String data = "";
        String field = "";
        op = ops.get(searchOper);
        data = genData(searchOper, searchString);
        if (prefix != null && prefix.equals("") == false) {
            field = prefix + "." + searchField;
        } else {
            field = searchField;
        }
        if (op.equals("in") || op.equals("ni")) {
            str.append(field + op + " (" + data + ")");
        } else {
            str.append(field + op + data);
        }

        return str.toString();
    }

    public String getGroupOp() {
        return groupOp;
    }

    public void setGroupOp(String groupOp) {
        this.groupOp = groupOp;
    }

    public JsonNode getRules() {
        return rules;
    }

    public void setRules(JsonNode rules) {
        this.rules = rules;
    }

    public static void main(String args[]) {
        String f = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"userName\",\"op\":\"bw\",\"data\":\"123\"},{\"field\":\"password\",\"op\":\"nc\",\"data\":\"123\"}]}";
        SearchFilters sf = new SearchFilters();
        System.out.println(sf.getWhere(f, "a"));
    }

}
