
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ky_registriosamuut_v1_Vastusrida complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ky_registriosamuut_v1_Vastusrida"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisatud_registriosad" type="{http://arireg.x-road.eu/producer/}ky_registriosamuut_v1_Regosa" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="eemaldatud_registriosad" type="{http://arireg.x-road.eu/producer/}ky_registriosamuut_v1_Regosa" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ky_registriosamuut_v1_Vastusrida", propOrder = {
    "ariregistriKood",
    "staatus",
    "lisatudRegistriosad",
    "eemaldatudRegistriosad"
})
public class KyRegistriosamuutV1Vastusrida {

    @XmlElement(name = "ariregistri_kood", required = true)
    protected BigInteger ariregistriKood;
    protected String staatus;
    @XmlElement(name = "lisatud_registriosad")
    protected List<KyRegistriosamuutV1Regosa> lisatudRegistriosad;
    @XmlElement(name = "eemaldatud_registriosad")
    protected List<KyRegistriosamuutV1Regosa> eemaldatudRegistriosad;

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAriregistriKood(BigInteger value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatus(String value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the lisatudRegistriosad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lisatudRegistriosad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLisatudRegistriosad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KyRegistriosamuutV1Regosa }
     * 
     * 
     */
    public List<KyRegistriosamuutV1Regosa> getLisatudRegistriosad() {
        if (lisatudRegistriosad == null) {
            lisatudRegistriosad = new ArrayList<KyRegistriosamuutV1Regosa>();
        }
        return this.lisatudRegistriosad;
    }

    /**
     * Gets the value of the eemaldatudRegistriosad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eemaldatudRegistriosad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEemaldatudRegistriosad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KyRegistriosamuutV1Regosa }
     * 
     * 
     */
    public List<KyRegistriosamuutV1Regosa> getEemaldatudRegistriosad() {
        if (eemaldatudRegistriosad == null) {
            eemaldatudRegistriosad = new ArrayList<KyRegistriosamuutV1Regosa>();
        }
        return this.eemaldatudRegistriosad;
    }

}
