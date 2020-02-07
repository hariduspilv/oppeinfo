
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR410KeskkonnateabeResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR410KeskkonnateabeResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VastuseKPV" type="{http://rr.x-road.eu/producer}date"/&gt;
 *         &lt;element name="SeisugaKPV" type="{http://rr.x-road.eu/producer}date"/&gt;
 *         &lt;element name="Andmed"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="EHAK" type="{http://rr.x-road.eu/producer}ctEHAK" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR410KeskkonnateabeResponseType", propOrder = {
    "vastuseKPV",
    "seisugaKPV",
    "andmed"
})
public class RR410KeskkonnateabeResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "VastuseKPV", required = true)
    protected String vastuseKPV;
    @XmlElement(name = "SeisugaKPV", required = true)
    protected String seisugaKPV;
    @XmlElement(name = "Andmed", required = true)
    protected RR410KeskkonnateabeResponseType.Andmed andmed;

    /**
     * Gets the value of the vastuseKPV property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVastuseKPV() {
        return vastuseKPV;
    }

    /**
     * Sets the value of the vastuseKPV property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVastuseKPV(String value) {
        this.vastuseKPV = value;
    }

    /**
     * Gets the value of the seisugaKPV property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeisugaKPV() {
        return seisugaKPV;
    }

    /**
     * Sets the value of the seisugaKPV property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeisugaKPV(String value) {
        this.seisugaKPV = value;
    }

    /**
     * Gets the value of the andmed property.
     * 
     * @return
     *     possible object is
     *     {@link RR410KeskkonnateabeResponseType.Andmed }
     *     
     */
    public RR410KeskkonnateabeResponseType.Andmed getAndmed() {
        return andmed;
    }

    /**
     * Sets the value of the andmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR410KeskkonnateabeResponseType.Andmed }
     *     
     */
    public void setAndmed(RR410KeskkonnateabeResponseType.Andmed value) {
        this.andmed = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="EHAK" type="{http://rr.x-road.eu/producer}ctEHAK" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ehak"
    })
    public static class Andmed {

        @XmlElement(name = "EHAK")
        protected List<CtEHAK> ehak;

        /**
         * Gets the value of the ehak property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ehak property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEHAK().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CtEHAK }
         * 
         * 
         */
        public List<CtEHAK> getEHAK() {
            if (ehak == null) {
                ehak = new ArrayList<CtEHAK>();
            }
            return this.ehak;
        }

    }

}
