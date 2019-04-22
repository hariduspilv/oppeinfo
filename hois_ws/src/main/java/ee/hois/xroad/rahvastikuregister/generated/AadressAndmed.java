
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AadressAndmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AadressAndmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Aadress"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodif1"/&gt;
 *                   &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="KuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                   &lt;element name="AadressEST" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="AadressNoEST" type="{http://rr.x-road.eu/producer}Kodif3" minOccurs="0"/&gt;
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
@XmlType(name = "AadressAndmed", propOrder = {
    "aadress"
})
public class AadressAndmed {

    @XmlElement(name = "Aadress", required = true)
    protected AadressAndmed.Aadress aadress;

    /**
     * Gets the value of the aadress property.
     * 
     * @return
     *     possible object is
     *     {@link AadressAndmed.Aadress }
     *     
     */
    public AadressAndmed.Aadress getAadress() {
        return aadress;
    }

    /**
     * Sets the value of the aadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AadressAndmed.Aadress }
     *     
     */
    public void setAadress(AadressAndmed.Aadress value) {
        this.aadress = value;
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
     *         &lt;element name="Liik" type="{http://rr.x-road.eu/producer}Kodif1"/&gt;
     *         &lt;element name="AlatesKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="KuniKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *         &lt;element name="AadressEST" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="AadressNoEST" type="{http://rr.x-road.eu/producer}Kodif3" minOccurs="0"/&gt;
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
        "liik",
        "alatesKP",
        "kuniKP",
        "aadressEST",
        "aadressNoEST"
    })
    public static class Aadress {

        @XmlElement(name = "Liik", required = true)
        protected Kodif1 liik;
        @XmlElement(name = "AlatesKP", required = true)
        protected String alatesKP;
        @XmlElement(name = "KuniKP")
        protected String kuniKP;
        @XmlElement(name = "AadressEST")
        protected AadressAndmed.Aadress.AadressEST aadressEST;
        @XmlElement(name = "AadressNoEST")
        protected Kodif3 aadressNoEST;

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

        /**
         * Gets the value of the aadressEST property.
         * 
         * @return
         *     possible object is
         *     {@link AadressAndmed.Aadress.AadressEST }
         *     
         */
        public AadressAndmed.Aadress.AadressEST getAadressEST() {
            return aadressEST;
        }

        /**
         * Sets the value of the aadressEST property.
         * 
         * @param value
         *     allowed object is
         *     {@link AadressAndmed.Aadress.AadressEST }
         *     
         */
        public void setAadressEST(AadressAndmed.Aadress.AadressEST value) {
            this.aadressEST = value;
        }

        /**
         * Gets the value of the aadressNoEST property.
         * 
         * @return
         *     possible object is
         *     {@link Kodif3 }
         *     
         */
        public Kodif3 getAadressNoEST() {
            return aadressNoEST;
        }

        /**
         * Sets the value of the aadressNoEST property.
         * 
         * @param value
         *     allowed object is
         *     {@link Kodif3 }
         *     
         */
        public void setAadressNoEST(Kodif3 value) {
            this.aadressNoEST = value;
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
         *         &lt;element name="ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "adsoid"
        })
        public static class AadressEST {

            @XmlElement(name = "ADS_OID", required = true)
            protected String adsoid;

            /**
             * Gets the value of the adsoid property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getADSOID() {
                return adsoid;
            }

            /**
             * Sets the value of the adsoid property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setADSOID(String value) {
                this.adsoid = value;
            }

        }

    }

}
