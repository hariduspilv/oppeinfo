package ee.hitsa.ois.xml.timetable.asc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AscDaysIntegerXmlAdapter extends XmlAdapter<String, List<Integer>> {

    @Override
    public List<Integer> unmarshal(String v) throws Exception {
        List<Integer> days = new ArrayList<>();

        for (final String s : v.split(",")) {
            final String trimmed = s.trim();

            if (trimmed.length() > 0) {
                days.add(convertValue(trimmed));
            }
        }
        return days;
    }

    @Override
    public String marshal(List<Integer> v) throws Exception {
        // Not used
        return null;
    }
    
    private static Integer convertValue(String value) {
        return Integer.valueOf(findBitPosition((Integer.parseInt(new StringBuilder(value).reverse().toString(), 2))));
    }

    private static boolean isPowerOfTwo(int n) {
        return n > 0 && ((n & (n - 1)) == 0);
    }
    
    private static int findBitPosition(int n) {
        if (!isPowerOfTwo(n)) {
            return -1;
        }
        
        int i = 1, pos =1;
        
        while ((i & n) == 0) {
            i = i << 1;
            pos++;
        }
        
        return pos;
    }
}
