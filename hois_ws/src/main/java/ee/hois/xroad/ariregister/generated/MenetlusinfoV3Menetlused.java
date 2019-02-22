
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for menetlusinfo_v3_menetlused complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="menetlusinfo_v3_menetlused"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dokumendid" type="{http://arireg.x-road.eu/producer/}menetlusinfo_v3_dokument" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="hoiatused" type="{http://arireg.x-road.eu/producer/}menetlusinfo_v3_hoiatus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="maarused" type="{http://arireg.x-road.eu/producer/}menetlusinfo_v3_maarus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menetlusinfo_v3_menetlused", propOrder = {
    "dokumendid",
    "hoiatused",
    "maarused"
})
public class MenetlusinfoV3Menetlused {

    @XmlElement(nillable = true)
    protected List<MenetlusinfoV3Dokument> dokumendid;
    @XmlElement(nillable = true)
    protected List<MenetlusinfoV3Hoiatus> hoiatused;
    @XmlElement(nillable = true)
    protected List<MenetlusinfoV3Maarus> maarused;

    /**
     * Gets the value of the dokumendid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dokumendid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDokumendid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MenetlusinfoV3Dokument }
     * 
     * 
     */
    public List<MenetlusinfoV3Dokument> getDokumendid() {
        if (dokumendid == null) {
            dokumendid = new ArrayList<MenetlusinfoV3Dokument>();
        }
        return this.dokumendid;
    }

    /**
     * Gets the value of the hoiatused property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hoiatused property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHoiatused().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MenetlusinfoV3Hoiatus }
     * 
     * 
     */
    public List<MenetlusinfoV3Hoiatus> getHoiatused() {
        if (hoiatused == null) {
            hoiatused = new ArrayList<MenetlusinfoV3Hoiatus>();
        }
        return this.hoiatused;
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
     * {@link MenetlusinfoV3Maarus }
     * 
     * 
     */
    public List<MenetlusinfoV3Maarus> getMaarused() {
        if (maarused == null) {
            maarused = new ArrayList<MenetlusinfoV3Maarus>();
        }
        return this.maarused;
    }

}
