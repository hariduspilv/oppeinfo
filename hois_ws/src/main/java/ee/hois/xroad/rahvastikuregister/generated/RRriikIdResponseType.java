
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRriikIdResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRriikIdResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Riik" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="AkpId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="AkpKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="AkpPikkNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRriikIdResponseType", propOrder = {
    "riik"
})
public class RRriikIdResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Riik", required = true)
    protected List<RRriikIdResponseType.Riik> riik;

    /**
     * Gets the value of the riik property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the riik property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRiik().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RRriikIdResponseType.Riik }
     * 
     * 
     */
    public List<RRriikIdResponseType.Riik> getRiik() {
        if (riik == null) {
            riik = new ArrayList<RRriikIdResponseType.Riik>();
        }
        return this.riik;
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
     *         &lt;element name="AkpId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="AkpKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="AkpPikkNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "akpId",
        "akpKood",
        "akpPikkNimetus"
    })
    public static class Riik {

        @XmlElement(name = "AkpId", required = true)
        protected String akpId;
        @XmlElement(name = "AkpKood", required = true)
        protected String akpKood;
        @XmlElement(name = "AkpPikkNimetus", required = true)
        protected String akpPikkNimetus;

        /**
         * Gets the value of the akpId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAkpId() {
            return akpId;
        }

        /**
         * Sets the value of the akpId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAkpId(String value) {
            this.akpId = value;
        }

        /**
         * Gets the value of the akpKood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAkpKood() {
            return akpKood;
        }

        /**
         * Sets the value of the akpKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAkpKood(String value) {
            this.akpKood = value;
        }

        /**
         * Gets the value of the akpPikkNimetus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAkpPikkNimetus() {
            return akpPikkNimetus;
        }

        /**
         * Sets the value of the akpPikkNimetus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAkpPikkNimetus(String value) {
            this.akpPikkNimetus = value;
        }

    }

}
