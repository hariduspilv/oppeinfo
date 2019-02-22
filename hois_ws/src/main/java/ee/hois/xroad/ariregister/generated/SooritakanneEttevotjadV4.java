
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sooritakanne_ettevotjad_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sooritakanne_ettevotjad_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asutamise_number" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="kande_sisu" type="{http://arireg.x-road.eu/producer/}sooritakanne_kandesisu_v4" minOccurs="0"/&gt;
 *         &lt;element name="dokumendid" type="{http://arireg.x-road.eu/producer/}dokumentType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sooritakanne_ettevotjad_v4", propOrder = {
    "registrikood",
    "asutamiseNumber",
    "kandeSisu",
    "dokumendid"
})
public class SooritakanneEttevotjadV4 {

    protected String registrikood;
    @XmlElement(name = "asutamise_number")
    protected Integer asutamiseNumber;
    @XmlElement(name = "kande_sisu")
    protected SooritakanneKandesisuV4 kandeSisu;
    @XmlElement(nillable = true)
    protected List<DokumentTypeV4> dokumendid;

    /**
     * Gets the value of the registrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrikood() {
        return registrikood;
    }

    /**
     * Sets the value of the registrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrikood(String value) {
        this.registrikood = value;
    }

    /**
     * Gets the value of the asutamiseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAsutamiseNumber() {
        return asutamiseNumber;
    }

    /**
     * Sets the value of the asutamiseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAsutamiseNumber(Integer value) {
        this.asutamiseNumber = value;
    }

    /**
     * Gets the value of the kandeSisu property.
     * 
     * @return
     *     possible object is
     *     {@link SooritakanneKandesisuV4 }
     *     
     */
    public SooritakanneKandesisuV4 getKandeSisu() {
        return kandeSisu;
    }

    /**
     * Sets the value of the kandeSisu property.
     * 
     * @param value
     *     allowed object is
     *     {@link SooritakanneKandesisuV4 }
     *     
     */
    public void setKandeSisu(SooritakanneKandesisuV4 value) {
        this.kandeSisu = value;
    }

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
     * {@link DokumentTypeV4 }
     * 
     * 
     */
    public List<DokumentTypeV4> getDokumendid() {
        if (dokumendid == null) {
            dokumendid = new ArrayList<DokumentTypeV4>();
        }
        return this.dokumendid;
    }

}
