
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR418ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR418ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Seosed" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Seos" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="SeoseLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="SeoseIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="SeoseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="SeoseAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="SeoseKehtivus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="SeoseStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ParitudIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR418ResponseType", propOrder = {
    "seosed"
})
public class RR418ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Seosed")
    protected RR418ResponseType.Seosed seosed;

    /**
     * Gets the value of the seosed property.
     * 
     * @return
     *     possible object is
     *     {@link RR418ResponseType.Seosed }
     *     
     */
    public RR418ResponseType.Seosed getSeosed() {
        return seosed;
    }

    /**
     * Sets the value of the seosed property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR418ResponseType.Seosed }
     *     
     */
    public void setSeosed(RR418ResponseType.Seosed value) {
        this.seosed = value;
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
     *         &lt;element name="Seos" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="SeoseLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="SeoseIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="SeoseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="SeoseAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="SeoseKehtivus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="SeoseStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ParitudIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "seos"
    })
    public static class Seosed {

        @XmlElement(name = "Seos")
        protected List<RR418ResponseType.Seosed.Seos> seos;

        /**
         * Gets the value of the seos property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the seos property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSeos().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR418ResponseType.Seosed.Seos }
         * 
         * 
         */
        public List<RR418ResponseType.Seosed.Seos> getSeos() {
            if (seos == null) {
                seos = new ArrayList<RR418ResponseType.Seosed.Seos>();
            }
            return this.seos;
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
         *         &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="SeoseLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="SeoseIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="SeoseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="SeoseAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="SeoseKehtivus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="SeoseStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ParitudIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "nimi",
            "kodakondsus",
            "aadress",
            "staatus",
            "seoseLiik",
            "seoseIsikukood",
            "seoseNimi",
            "seoseAadress",
            "seoseKehtivus",
            "seoseStaatus",
            "paritudIK"
        })
        public static class Seos {

            @XmlElement(name = "Isikukood", required = true)
            protected String isikukood;
            @XmlElement(name = "Nimi", required = true)
            protected String nimi;
            @XmlElement(name = "Kodakondsus", required = true)
            protected String kodakondsus;
            @XmlElement(name = "Aadress", required = true)
            protected String aadress;
            @XmlElement(name = "Staatus", required = true)
            protected String staatus;
            @XmlElement(name = "SeoseLiik", required = true)
            protected String seoseLiik;
            @XmlElement(name = "SeoseIsikukood", required = true)
            protected String seoseIsikukood;
            @XmlElement(name = "SeoseNimi", required = true)
            protected String seoseNimi;
            @XmlElement(name = "SeoseAadress", required = true)
            protected String seoseAadress;
            @XmlElement(name = "SeoseKehtivus", required = true)
            protected String seoseKehtivus;
            @XmlElement(name = "SeoseStaatus", required = true)
            protected String seoseStaatus;
            @XmlElement(name = "ParitudIK", required = true)
            protected String paritudIK;

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
             * Gets the value of the nimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNimi() {
                return nimi;
            }

            /**
             * Sets the value of the nimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNimi(String value) {
                this.nimi = value;
            }

            /**
             * Gets the value of the kodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKodakondsus() {
                return kodakondsus;
            }

            /**
             * Sets the value of the kodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKodakondsus(String value) {
                this.kodakondsus = value;
            }

            /**
             * Gets the value of the aadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadress() {
                return aadress;
            }

            /**
             * Sets the value of the aadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadress(String value) {
                this.aadress = value;
            }

            /**
             * Gets the value of the staatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStaatus() {
                return staatus;
            }

            /**
             * Sets the value of the staatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStaatus(String value) {
                this.staatus = value;
            }

            /**
             * Gets the value of the seoseLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeoseLiik() {
                return seoseLiik;
            }

            /**
             * Sets the value of the seoseLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeoseLiik(String value) {
                this.seoseLiik = value;
            }

            /**
             * Gets the value of the seoseIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeoseIsikukood() {
                return seoseIsikukood;
            }

            /**
             * Sets the value of the seoseIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeoseIsikukood(String value) {
                this.seoseIsikukood = value;
            }

            /**
             * Gets the value of the seoseNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeoseNimi() {
                return seoseNimi;
            }

            /**
             * Sets the value of the seoseNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeoseNimi(String value) {
                this.seoseNimi = value;
            }

            /**
             * Gets the value of the seoseAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeoseAadress() {
                return seoseAadress;
            }

            /**
             * Sets the value of the seoseAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeoseAadress(String value) {
                this.seoseAadress = value;
            }

            /**
             * Gets the value of the seoseKehtivus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeoseKehtivus() {
                return seoseKehtivus;
            }

            /**
             * Sets the value of the seoseKehtivus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeoseKehtivus(String value) {
                this.seoseKehtivus = value;
            }

            /**
             * Gets the value of the seoseStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeoseStaatus() {
                return seoseStaatus;
            }

            /**
             * Sets the value of the seoseStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeoseStaatus(String value) {
                this.seoseStaatus = value;
            }

            /**
             * Gets the value of the paritudIK property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParitudIK() {
                return paritudIK;
            }

            /**
             * Sets the value of the paritudIK property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParitudIK(String value) {
                this.paritudIK = value;
            }

        }

    }

}
