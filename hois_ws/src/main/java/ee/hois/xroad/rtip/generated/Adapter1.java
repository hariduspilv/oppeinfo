
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, LocalDate>
{


    public LocalDate unmarshal(String value) {
        return (ee.hois.soap.SoapUtil.rtipParseDate(value));
    }

    public String marshal(LocalDate value) {
        return (ee.hois.soap.SoapUtil.rtipPrintDate(value));
    }

}
