
package ee.hois.xroad.kutseregister.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, LocalDate>
{


    public LocalDate unmarshal(String value) {
        return (ee.hois.soap.SoapUtil.parseDate(value));
    }

    public String marshal(LocalDate value) {
        return (ee.hois.soap.SoapUtil.printDate(value));
    }

}
