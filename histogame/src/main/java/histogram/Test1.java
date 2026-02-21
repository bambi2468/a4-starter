package histogram;

import java.util.HashMap;
import java.util.Map;

public class Test1 {
    public static void main(String[] args) {
        Map<String,Integer> m1 = new HashMap<>();
        m1.put("cat", 2);
        m1.put("dog", 5);
        Map<String,Integer> m2 = new HashMap<>();
        m2.put("dog", 5);
        m2.put("cat", 2);
        Map<String,Integer> m3 = new HashMap<>();
        m3.put("dog", 5);
        m3.put("cat", 2);
        m3.put("jelly", 6);
        System.out.println(m1.equals(m2));
        System.out.println(m1.equals(m3));

        SimpleHistogram<String> sh = new SimpleHistogram<>();
        sh.setCount("cat",3);
        sh.setCount("dog",5);
        System.out.println(sh);
    }
}
