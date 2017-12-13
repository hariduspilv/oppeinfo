
package ee.hois.xroad.kutseregister.generated;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter2
    extends XmlAdapter<String, String>
{


    public String unmarshal(String value) {
        return (ee.hois.soap.SoapUtil.parseTaseType(value));
    }

    public String marshal(String value) {
        return (ee.hois.soap.SoapUtil.printTaseType(value));
    }

}
