
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Isik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Isik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="KuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
@XmlType(name = "Isik", propOrder = {
    "isikukood",
    "eesnimi",
    "perenimi",
    "valisriigiIK"
})
public class Isik {

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "Eesnimi", required = true)
    protected String eesnimi;
    @XmlElement(name = "Perenimi", required = true)
    protected String perenimi;
    @XmlElement(name = "ValisriigiIK")
    protected List<Isik.ValisriigiIK> valisriigiIK;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the perenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimi() {
        return perenimi;
    }

    /**
     * Sets the value of the perenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimi(String value) {
        this.perenimi = value;
    }

    /**
     * Gets the value of the valisriigiIK property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valisriigiIK property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValisriigiIK().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Isik.ValisriigiIK }
     * 
     * 
     */
    public List<Isik.ValisriigiIK> getValisriigiIK() {
        if (valisriigiIK == null) {
            valisriigiIK = new ArrayList<Isik.ValisriigiIK>();
        }
        return this.valisriigiIK;
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
     *         &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="KuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
        "riigiKood",
        "valisriigiIK",
        "alatesKP",
        "kuniKP"
    })
    public static class ValisriigiIK {

        @XmlElement(name = "RiigiKood", required = true)
        protected String riigiKood;
        @XmlElement(name = "ValisriigiIK", required = true)
        protected String valisriigiIK;
        @XmlElement(name = "AlatesKP", required = true)
        protected String alatesKP;
        @XmlElement(name = "KuniKP")
        protected String kuniKP;

        /**
         * Gets the value of the riigiKood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRiigiKood() {
            return riigiKood;
        }

        /**
         * Sets the value of the riigiKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRiigiKood(String value) {
            this.riigiKood = value;
        }

        /**
         * Gets the value of the valisriigiIK property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValisriigiIK() {
            return valisriigiIK;
        }

        /**
         * Sets the value of the valisriigiIK property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValisriigiIK(String value) {
            this.valisriigiIK = value;
        }

        /**
         * Gets the value of the alatesKP property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAlatesKP() {
            return alatesKP;
        }

        /**
         * Sets the value of the alatesKP property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAlatesKP(String value) {
            this.alatesKP = value;
        }

        /**
         * Gets the value of the kuniKP property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKuniKP() {
            return kuniKP;
        }

        /**
         * Sets the value of the kuniKP property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKuniKP(String value) {
            this.kuniKP = value;
        }

    }

}
