
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRSurmateatisResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRSurmateatisResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRSurmateatis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SurmakandeNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Viga" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="VeaKood" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *                   &lt;element name="VeaTekst" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *                   &lt;element name="RRSurmateatis" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *                   &lt;element name="SurmakandeNumber" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
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
@XmlType(name = "RRSurmateatisResponseType", propOrder = {
    "rrSurmateatis",
    "surmakandeNumber",
    "viga"
})
public class RRSurmateatisResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRSurmateatis", required = true)
    protected String rrSurmateatis;
    @XmlElement(name = "SurmakandeNumber", required = true)
    protected String surmakandeNumber;
    @XmlElement(name = "Viga")
    protected RRSurmateatisResponseType.Viga viga;

    /**
     * Gets the value of the rrSurmateatis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRRSurmateatis() {
        return rrSurmateatis;
    }

    /**
     * Sets the value of the rrSurmateatis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRRSurmateatis(String value) {
        this.rrSurmateatis = value;
    }

    /**
     * Gets the value of the surmakandeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurmakandeNumber() {
        return surmakandeNumber;
    }

    /**
     * Sets the value of the surmakandeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurmakandeNumber(String value) {
        this.surmakandeNumber = value;
    }

    /**
     * Gets the value of the viga property.
     * 
     * @return
     *     possible object is
     *     {@link RRSurmateatisResponseType.Viga }
     *     
     */
    public RRSurmateatisResponseType.Viga getViga() {
        return viga;
    }

    /**
     * Sets the value of the viga property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRSurmateatisResponseType.Viga }
     *     
     */
    public void setViga(RRSurmateatisResponseType.Viga value) {
        this.viga = value;
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
     *         &lt;element name="VeaKood" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *         &lt;element name="VeaTekst" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *         &lt;element name="RRSurmateatis" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *         &lt;element name="SurmakandeNumber" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
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
        "veaKood",
        "veaTekst",
        "rrSurmateatis",
        "surmakandeNumber"
    })
    public static class Viga {

        @XmlElement(name = "VeaKood", required = true)
        protected Object veaKood;
        @XmlElement(name = "VeaTekst", required = true)
        protected Object veaTekst;
        @XmlElement(name = "RRSurmateatis", required = true)
        protected Object rrSurmateatis;
        @XmlElement(name = "SurmakandeNumber", required = true)
        protected Object surmakandeNumber;

        /**
         * Gets the value of the veaKood property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getVeaKood() {
            return veaKood;
        }

        /**
         * Sets the value of the veaKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setVeaKood(Object value) {
            this.veaKood = value;
        }

        /**
         * Gets the value of the veaTekst property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getVeaTekst() {
            return veaTekst;
        }

        /**
         * Sets the value of the veaTekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setVeaTekst(Object value) {
            this.veaTekst = value;
        }

        /**
         * Gets the value of the rrSurmateatis property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getRRSurmateatis() {
            return rrSurmateatis;
        }

        /**
         * Sets the value of the rrSurmateatis property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setRRSurmateatis(Object value) {
            this.rrSurmateatis = value;
        }

        /**
         * Gets the value of the surmakandeNumber property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getSurmakandeNumber() {
            return surmakandeNumber;
        }

        /**
         * Sets the value of the surmakandeNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setSurmakandeNumber(Object value) {
            this.surmakandeNumber = value;
        }

    }

}
