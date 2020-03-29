package ee.hitsa.ois.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CommaSeparatedListXmlAdapter extends XmlAdapter<String, List<String>> {

    @Override
    public List<String> unmarshal(String v) throws Exception {
        final List<String> strings = new ArrayList<>();

        for (final String s : v.split(",")) {
            final String trimmed = s.trim();

            if (trimmed.length() > 0) {
                strings.add(trimmed);
            }
        }

        return strings;
    }

    @Override
    public String marshal(List<String> v) throws Exception {
        final StringBuilder sb = new StringBuilder();

        for (final String string : v) {
            if (sb.length() > 0) {
                sb.append(",");
            }

            sb.append(string);
        }

        return sb.toString();
    }

}
