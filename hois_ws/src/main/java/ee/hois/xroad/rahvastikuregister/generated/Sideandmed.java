
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Sideandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Sideandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Kontakt" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodif1"/&gt;
 *                   &lt;element name="KontaktiTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "Sideandmed", propOrder = {
    "kontakt"
})
public class Sideandmed {

    @XmlElement(name = "Kontakt", required = true)
    protected List<Sideandmed.Kontakt> kontakt;

    /**
     * Gets the value of the kontakt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kontakt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKontakt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sideandmed.Kontakt }
     * 
     * 
     */
    public List<Sideandmed.Kontakt> getKontakt() {
        if (kontakt == null) {
            kontakt = new ArrayList<Sideandmed.Kontakt>();
        }
        return this.kontakt;
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
     *         &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodif1"/&gt;
     *         &lt;element name="KontaktiTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "liik",
        "kontaktiTekst",
        "alatesKP",
        "kuniKP"
    })
    public static class Kontakt {

        @XmlElement(name = "RiigiKood")
        protected String riigiKood;
        @XmlElement(name = "Liik", required = true)
        protected Kodif1 liik;
        @XmlElement(name = "KontaktiTekst", required = true)
        protected String kontaktiTekst;
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
         * Gets the value of the liik property.
         * 
         * @return
         *     possible object is
         *     {@link Kodif1 }
         *     
         */
        public Kodif1 getLiik() {
            return liik;
        }

        /**
         * Sets the value of the liik property.
         * 
         * @param value
         *     allowed object is
         *     {@link Kodif1 }
         *     
         */
        public void setLiik(Kodif1 value) {
            this.liik = value;
        }

        /**
         * Gets the value of the kontaktiTekst property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKontaktiTekst() {
            return kontaktiTekst;
        }

        /**
         * Sets the value of the kontaktiTekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKontaktiTekst(String value) {
            this.kontaktiTekst = value;
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
