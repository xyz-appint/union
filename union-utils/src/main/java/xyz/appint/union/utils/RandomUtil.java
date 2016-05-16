package xyz.appint.union.utils;

import java.util.*;

/**
 * Created by Justin on 2014/4/27.
 */
public class RandomUtil {
    private String[] choices;
    private float[] rates;
    private List<Float> list = new ArrayList();

    public RandomUtil(String[] choices, float[] rates) {
        this.choices = choices;
        this.rates = rates;
        init();
        System.out.println(this.list.toString());
    }

    public RandomUtil(Map<String, Float> floatMap) {
        Assert.notNull(floatMap);
        List<Map.Entry> a = new ArrayList<Map.Entry>(floatMap.entrySet());
        Collections.sort(a,
                new Comparator() {
                    public int compare(Object o1, Object o2) {
                        Map.Entry e1 = (Map.Entry) o1;
                        Map.Entry e2 = (Map.Entry) o2;
                        return ((Comparable) e1.getValue()).compareTo(e2.getValue());
                    }
                }
        );
        String[] choices = new String[floatMap.size()];
        float[] rates = new float[floatMap.size()];
        int i = 0;
        for (Map.Entry<String, Float> e : a) {
            choices[i] = e.getKey();
            rates[i] = e.getValue();
            i++;
        }
        this.choices = choices;
        this.rates = rates;
        init();
        System.err.println(list.toString());
    }

    public static void testGetRate() {
        String[] choices = {"A", "B", "C", "D"};
        float[] rates = {20.0F, 20.0F, 20.0F, 20.0F};

        RandomUtil ran = new RandomUtil(choices, rates);
        String tempChoice = "";
        int total = 10000000;
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        for (int i = 0; i < total; i++) {
            tempChoice = ran.getChoice();
            if ("A".equals(tempChoice)) {
                a++;
            } else if ("B".equals(tempChoice)) {
                b++;
            } else if ("C".equals(tempChoice)) {
                c++;
            } else if ("D".equals(tempChoice)) {
                d++;
            }
        }
        System.out.print("Total=" + total + " Rating:A=" + a + " 机率：" + new Double(1.0F * a / total * 100.0F) + "%\tB=" + b + "机率：" + new Double(1.0F * b / total * 100.0F) + "%\tC=" + c + "  机率：" + new Double(1.0F * c / total * 100.0F) + "%\tD=" + d + "  机率：" + new Double(1.0F * d / total * 100.0F) + "%");
    }

    public static void testGetRate1() {
        Map<String, Float> map = new HashMap<String, Float>();
        map.put("A", 0.01f);
        map.put("B", 22f);
        map.put("C", 10f);
        map.put("D", 88.9f);

        System.err.println(map);
        RandomUtil ran = new RandomUtil(map);
        String tempChoice = "";
        int total = 10000000;

        Map<String, Integer> count = new HashMap<String, Integer>();
        for (int i = 0; i < total; i++) {
            tempChoice = ran.getChoice();
            if (count.containsKey(tempChoice)) {
                count.put(tempChoice, count.get(tempChoice) + 1);
            } else {
                count.put(tempChoice, 1);
            }
        }


        Iterator iterator = count.entrySet().iterator();
        String[] choices = new String[count.size()];
        float[] rates = new float[count.size()];
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry) iterator.next();

            //System.err.println(entry.getKey() + " >> " + entry.getValue() + " >> " + new Float(1.0F * entry.getValue() / total * 100.0F) + "%");
        }
    }

    public static void main(String[] args) {
        testGetRate1();
    }

    private float getRandomRate(int j) {
        float rate = 0.0F;
        for (int i = 0; i < j; i++) {
            rate += this.rates[i];
        }
        return rate;
    }

    /**
     * 分配随机区间
     */
    private void init() {
//        float[] arr = new float[rates.length];
//        float[] old = rates.clone();//保存排序前数组
//        for(float str : rates) {
//            System.out.println(">>>>" + str);
//        }
//        for(float str : rates) {
//            arr[i] = old.v
//        }
        List<Float> temp = new ArrayList<Float>();
        float total = 1;
        //this.list.add(Float.valueOf(0.0F));
        for (int i = 0; i < this.choices.length; i++) {
            float f = Float.valueOf(getRandomRate(i + 1));
            total += f;
            temp.add(f);
        }
        this.list.add(Float.valueOf(0.0F));
        for (float f : temp) {
            //list.add(f/total);
            list.add((f / total) * 100);
        }
    }

    /**
     * 得到结果
     *
     * @return
     */
    public String getChoice() {
        String choice = "";
        float random = (float) (100.0D * Math.random());//生成100以内随机数
        //取出在区间范围内的结果
        for (int i = 0; i < this.choices.length; i++) {
            if (((ConvertUtils.toFloat(this.list.get(i)).floatValue()) <= random) && (random < (ConvertUtils.toFloat(this.list.get(i + 1)).floatValue()))) {
                choice = this.choices[i];
                break;
            }
        }
        if ((choice == null) || (choice.equals(""))) {
            choice = getChoice();
        }
        return choice;
    }
}
