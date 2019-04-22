
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTELUKOHTResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTELUKOHTResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsikuAadressid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="IsikuAadress" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AadressTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AadressOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Periood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
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
@XmlType(name = "RRPORTELUKOHTResponseType", propOrder = {
    "veatekst",
    "isikuAadressid"
})
public class RRPORTELUKOHTResponseType
    extends XRoadResponseBaseType
{

    protected String veatekst;
    @XmlElement(name = "IsikuAadressid", required = true)
    protected RRPORTELUKOHTResponseType.IsikuAadressid isikuAadressid;

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

    /**
     * Gets the value of the isikuAadressid property.
     * 
     * @return
     *     possible object is
     *     {@link RRPORTELUKOHTResponseType.IsikuAadressid }
     *     
     */
    public RRPORTELUKOHTResponseType.IsikuAadressid getIsikuAadressid() {
        return isikuAadressid;
    }

    /**
     * Sets the value of the isikuAadressid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRPORTELUKOHTResponseType.IsikuAadressid }
     *     
     */
    public void setIsikuAadressid(RRPORTELUKOHTResponseType.IsikuAadressid value) {
        this.isikuAadressid = value;
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
     *         &lt;element name="IsikuAadress" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AadressTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AadressOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Periood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "isikuAadress"
    })
    public static class IsikuAadressid {

        @XmlElement(name = "IsikuAadress")
        protected List<RRPORTELUKOHTResponseType.IsikuAadressid.IsikuAadress> isikuAadress;

        /**
         * Gets the value of the isikuAadress property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the isikuAadress property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIsikuAadress().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RRPORTELUKOHTResponseType.IsikuAadressid.IsikuAadress }
         * 
         * 
         */
        public List<RRPORTELUKOHTResponseType.IsikuAadressid.IsikuAadress> getIsikuAadress() {
            if (isikuAadress == null) {
                isikuAadress = new ArrayList<RRPORTELUKOHTResponseType.IsikuAadressid.IsikuAadress>();
            }
            return this.isikuAadress;
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
         *         &lt;element name="AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AadressTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AadressOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Periood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "aadressiLiik",
            "aadressTekst",
            "aadressOlek",
            "periood"
        })
        public static class IsikuAadress {

            @XmlElement(name = "AadressiLiik", required = true)
            protected String aadressiLiik;
            @XmlElement(name = "AadressTekst", required = true)
            protected String aadressTekst;
            @XmlElement(name = "AadressOlek", required = true)
            protected String aadressOlek;
            @XmlElement(name = "Periood", required = true)
            protected String periood;

            /**
             * Gets the value of the aadressiLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressiLiik() {
                return aadressiLiik;
            }

            /**
             * Sets the value of the aadressiLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressiLiik(String value) {
                this.aadressiLiik = value;
            }

            /**
             * Gets the value of the aadressTekst property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressTekst() {
                return aadressTekst;
            }

            /**
             * Sets the value of the aadressTekst property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressTekst(String value) {
                this.aadressTekst = value;
            }

            /**
             * Gets the value of the aadressOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressOlek() {
                return aadressOlek;
            }

            /**
             * Sets the value of the aadressOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressOlek(String value) {
                this.aadressOlek = value;
            }

            /**
             * Gets the value of the periood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPeriood() {
                return periood;
            }

            /**
             * Sets the value of the periood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPeriood(String value) {
                this.periood = value;
            }

        }

    }

}
