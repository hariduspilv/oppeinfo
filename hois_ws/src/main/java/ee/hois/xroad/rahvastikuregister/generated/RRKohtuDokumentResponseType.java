
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKohtuDokumentResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKohtuDokumentResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RRKohtuDokument" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Vastuse_Tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Vead" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Viga" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="VeaKood" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *                             &lt;element name="VeaTekst" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
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
@XmlType(name = "RRKohtuDokumentResponseType", propOrder = {
    "rrKohtuDokument",
    "vastuseTekst",
    "vead"
})
public class RRKohtuDokumentResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "RRKohtuDokument", required = true)
    protected String rrKohtuDokument;
    @XmlElement(name = "Vastuse_Tekst", required = true)
    protected String vastuseTekst;
    @XmlElement(name = "Vead")
    protected RRKohtuDokumentResponseType.Vead vead;

    /**
     * Gets the value of the rrKohtuDokument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRRKohtuDokument() {
        return rrKohtuDokument;
    }

    /**
     * Sets the value of the rrKohtuDokument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRRKohtuDokument(String value) {
        this.rrKohtuDokument = value;
    }

    /**
     * Gets the value of the vastuseTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVastuseTekst() {
        return vastuseTekst;
    }

    /**
     * Sets the value of the vastuseTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVastuseTekst(String value) {
        this.vastuseTekst = value;
    }

    /**
     * Gets the value of the vead property.
     * 
     * @return
     *     possible object is
     *     {@link RRKohtuDokumentResponseType.Vead }
     *     
     */
    public RRKohtuDokumentResponseType.Vead getVead() {
        return vead;
    }

    /**
     * Sets the value of the vead property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRKohtuDokumentResponseType.Vead }
     *     
     */
    public void setVead(RRKohtuDokumentResponseType.Vead value) {
        this.vead = value;
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
     *         &lt;element name="Viga" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="VeaKood" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *                   &lt;element name="VeaTekst" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
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
        "viga"
    })
    public static class Vead {

        @XmlElement(name = "Viga", required = true)
        protected List<RRKohtuDokumentResponseType.Vead.Viga> viga;

        /**
         * Gets the value of the viga property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the viga property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getViga().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RRKohtuDokumentResponseType.Vead.Viga }
         * 
         * 
         */
        public List<RRKohtuDokumentResponseType.Vead.Viga> getViga() {
            if (viga == null) {
                viga = new ArrayList<RRKohtuDokumentResponseType.Vead.Viga>();
            }
            return this.viga;
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
            "veaTekst"
        })
        public static class Viga {

            @XmlElement(name = "VeaKood", required = true)
            protected Object veaKood;
            @XmlElement(name = "VeaTekst", required = true)
            protected Object veaTekst;

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

        }

    }

}
