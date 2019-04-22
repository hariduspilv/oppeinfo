
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Isikud complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Isikud"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isik" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "Isikud", propOrder = {
    "isik"
})
public class Isikud {

    @XmlElement(name = "Isik", required = true)
    protected List<Isikud.Isik> isik;

    /**
     * Gets the value of the isik property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the isik property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIsik().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Isikud.Isik }
     * 
     * 
     */
    public List<Isikud.Isik> getIsik() {
        if (isik == null) {
            isik = new ArrayList<Isikud.Isik>();
        }
        return this.isik;
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
     *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "isikukood",
        "eesnimi",
        "perenimi"
    })
    public static class Isik {

        @XmlElement(name = "Isikukood", required = true)
        protected String isikukood;
        @XmlElement(name = "Eesnimi", required = true)
        protected String eesnimi;
        @XmlElement(name = "Perenimi", required = true)
        protected String perenimi;

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

    }

}
