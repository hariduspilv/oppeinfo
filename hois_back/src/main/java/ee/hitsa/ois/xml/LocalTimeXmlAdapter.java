package ee.hitsa.ois.xml;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalTimeXmlAdapter extends XmlAdapter<String, LocalTime> {

    @Override
    public LocalTime unmarshal(String v) throws Exception {
        return LocalTime.parse(v, DateTimeFormatter.ofPattern("H:mm"));
    }

    @Override
    public String marshal(LocalTime v) throws Exception {
        return v.toString();
    }

}
