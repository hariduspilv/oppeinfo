
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVK_kanded_v1_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVK_kanded_v1_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kanded" type="{http://arireg.x-road.eu/producer/}evk_seotud_kandedArray"/&gt;
 *         &lt;element name="leitud_kannete_arv" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVK_kanded_v1_vastus", propOrder = {
    "kanded",
    "leitudKanneteArv"
})
public class EVKKandedV1Vastus {

    @XmlElement(required = true)
    protected EvkSeotudKandedArray kanded;
    @XmlElement(name = "leitud_kannete_arv")
    protected int leitudKanneteArv;

    /**
     * Gets the value of the kanded property.
     * 
     * @return
     *     possible object is
     *     {@link EvkSeotudKandedArray }
     *     
     */
    public EvkSeotudKandedArray getKanded() {
        return kanded;
    }

    /**
     * Sets the value of the kanded property.
     * 
     * @param value
     *     allowed object is
     *     {@link EvkSeotudKandedArray }
     *     
     */
    public void setKanded(EvkSeotudKandedArray value) {
        this.kanded = value;
    }

    /**
     * Gets the value of the leitudKanneteArv property.
     * 
     */
    public int getLeitudKanneteArv() {
        return leitudKanneteArv;
    }

    /**
     * Sets the value of the leitudKanneteArv property.
     * 
     */
    public void setLeitudKanneteArv(int value) {
        this.leitudKanneteArv = value;
    }

}
