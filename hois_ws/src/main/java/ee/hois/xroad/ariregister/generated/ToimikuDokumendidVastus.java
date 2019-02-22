
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for toimiku_dokumendidVastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="toimiku_dokumendidVastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="dokumendid" type="{http://arireg.x-road.eu/producer/}toimiku_dokumendid_dokumendid"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "toimiku_dokumendidVastus", propOrder = {

})
public class ToimikuDokumendidVastus {

    @XmlElement(required = true)
    protected ToimikuDokumendidDokumendid dokumendid;

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link ToimikuDokumendidDokumendid }
     *     
     */
    public ToimikuDokumendidDokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToimikuDokumendidDokumendid }
     *     
     */
    public void setDokumendid(ToimikuDokumendidDokumendid value) {
        this.dokumendid = value;
    }

}
