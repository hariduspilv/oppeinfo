
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for earveEemaldaKlient_v1Response_Kliendid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="earveEemaldaKlient_v1Response_Kliendid"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klient" type="{http://arireg.x-road.eu/producer/}earveEemaldaKlient_v1Response_Klient" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "earveEemaldaKlient_v1Response_Kliendid", propOrder = {
    "klient"
})
public class EarveEemaldaKlientV1ResponseKliendid {

    protected List<EarveEemaldaKlientV1ResponseKlient> klient;

    /**
     * Gets the value of the klient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the klient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKlient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EarveEemaldaKlientV1ResponseKlient }
     * 
     * 
     */
    public List<EarveEemaldaKlientV1ResponseKlient> getKlient() {
        if (klient == null) {
            klient = new ArrayList<EarveEemaldaKlientV1ResponseKlient>();
        }
        return this.klient;
    }

}
