
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRExtDocumentDataT22Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRExtDocumentDataT22Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Viga" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *                   &lt;element name="tekst" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
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
@XmlType(name = "RRExtDocumentDataT22Response", propOrder = {
    "viga"
})
public class RRExtDocumentDataT22Response {

    @XmlElement(name = "Viga")
    protected RRExtDocumentDataT22Response.Viga viga;

    /**
     * Gets the value of the viga property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataT22Response.Viga }
     *     
     */
    public RRExtDocumentDataT22Response.Viga getViga() {
        return viga;
    }

    /**
     * Sets the value of the viga property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataT22Response.Viga }
     *     
     */
    public void setViga(RRExtDocumentDataT22Response.Viga value) {
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
     *         &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *         &lt;element name="tekst" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
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
        "kood",
        "tekst"
    })
    public static class Viga {

        @XmlElement(required = true)
        protected Object kood;
        @XmlElement(required = true)
        protected Object tekst;

        /**
         * Gets the value of the kood property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getKood() {
            return kood;
        }

        /**
         * Sets the value of the kood property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setKood(Object value) {
            this.kood = value;
        }

        /**
         * Gets the value of the tekst property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getTekst() {
            return tekst;
        }

        /**
         * Sets the value of the tekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setTekst(Object value) {
            this.tekst = value;
        }

    }

}
