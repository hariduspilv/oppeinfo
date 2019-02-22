
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiMenetlusinfo_v1_Menetlused complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiMenetlusinfo_v1_Menetlused"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="menetlus_info" type="{http://arireg.x-road.eu/producer/}EVapiMenetlusinfo_v1_Menetlus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="maarused" type="{http://arireg.x-road.eu/producer/}EVapiMenetlusinfo_v1_Maarus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiMenetlusinfo_v1_Menetlused", propOrder = {
    "menetlusInfo",
    "maarused"
})
public class EVapiMenetlusinfoV1Menetlused {

    @XmlElement(name = "menetlus_info")
    protected List<EVapiMenetlusinfoV1Menetlus> menetlusInfo;
    protected List<EVapiMenetlusinfoV1Maarus> maarused;

    /**
     * Gets the value of the menetlusInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menetlusInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenetlusInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EVapiMenetlusinfoV1Menetlus }
     * 
     * 
     */
    public List<EVapiMenetlusinfoV1Menetlus> getMenetlusInfo() {
        if (menetlusInfo == null) {
            menetlusInfo = new ArrayList<EVapiMenetlusinfoV1Menetlus>();
        }
        return this.menetlusInfo;
    }

    /**
     * Gets the value of the maarused property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the maarused property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaarused().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EVapiMenetlusinfoV1Maarus }
     * 
     * 
     */
    public List<EVapiMenetlusinfoV1Maarus> getMaarused() {
        if (maarused == null) {
            maarused = new ArrayList<EVapiMenetlusinfoV1Maarus>();
        }
        return this.maarused;
    }

}
