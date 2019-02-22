
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiMenetlusinfoVastus_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiMenetlusinfoVastus_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="menetlus_konteiner" type="{http://arireg.x-road.eu/producer/}EVapiMenetlusinfo_v1_Menetlused" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiMenetlusinfoVastus_v1", propOrder = {
    "menetlusKonteiner"
})
public class EVapiMenetlusinfoVastusV1 {

    @XmlElement(name = "menetlus_konteiner")
    protected List<EVapiMenetlusinfoV1Menetlused> menetlusKonteiner;

    /**
     * Gets the value of the menetlusKonteiner property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menetlusKonteiner property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenetlusKonteiner().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EVapiMenetlusinfoV1Menetlused }
     * 
     * 
     */
    public List<EVapiMenetlusinfoV1Menetlused> getMenetlusKonteiner() {
        if (menetlusKonteiner == null) {
            menetlusKonteiner = new ArrayList<EVapiMenetlusinfoV1Menetlused>();
        }
        return this.menetlusKonteiner;
    }

}
