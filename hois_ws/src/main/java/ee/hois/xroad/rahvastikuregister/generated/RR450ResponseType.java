
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR450ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR450ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veateade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="Isikud" minOccurs="0"&gt;
 *             &lt;complexType&gt;
 *               &lt;complexContent&gt;
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                   &lt;sequence&gt;
 *                     &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;complexContent&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                             &lt;sequence&gt;
 *                               &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                               &lt;element name="Isik.Isikupnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                               &lt;element name="Isik.Isikuenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                               &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                 &lt;complexType&gt;
 *                                   &lt;complexContent&gt;
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                       &lt;sequence&gt;
 *                                         &lt;element name="Elukoht.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.ElukohaAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.AadressTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.ADSAadresstekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                         &lt;element name="Elukoht.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;/sequence&gt;
 *                                     &lt;/restriction&gt;
 *                                   &lt;/complexContent&gt;
 *                                 &lt;/complexType&gt;
 *                               &lt;/element&gt;
 *                             &lt;/sequence&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/complexContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                   &lt;/sequence&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/complexContent&gt;
 *             &lt;/complexType&gt;
 *           &lt;/element&gt;
 *         &lt;/sequence&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR450ResponseType", propOrder = {
    "veateade",
    "isikud"
})
public class RR450ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veateade")
    protected String veateade;
    @XmlElement(name = "Isikud")
    protected RR450ResponseType.Isikud isikud;

    /**
     * Gets the value of the veateade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeateade() {
        return veateade;
    }

    /**
     * Sets the value of the veateade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeateade(String value) {
        this.veateade = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR450ResponseType.Isikud }
     *     
     */
    public RR450ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR450ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR450ResponseType.Isikud value) {
        this.isikud = value;
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
     *         &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Isikupnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Isikuenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Elukoht.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.ElukohaAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.AadressTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.ADSAadresstekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Elukoht.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "isik"
    })
    public static class Isikud {

        @XmlElement(name = "Isik")
        protected List<RR450ResponseType.Isikud.Isik> isik;

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
         * {@link RR450ResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR450ResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR450ResponseType.Isikud.Isik>();
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
         *         &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Isikupnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Isikuenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Elukoht.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.ElukohaAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.AadressTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.ADSAadresstekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Elukoht.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikIsikukood",
            "isikIsikupnimi",
            "isikIsikuenimi",
            "elukoht"
        })
        public static class Isik {

            @XmlElement(name = "Isik.Isikukood", required = true)
            protected String isikIsikukood;
            @XmlElement(name = "Isik.Isikupnimi", required = true)
            protected String isikIsikupnimi;
            @XmlElement(name = "Isik.Isikuenimi", required = true)
            protected String isikIsikuenimi;
            @XmlElement(name = "Elukoht")
            protected List<RR450ResponseType.Isikud.Isik.Elukoht> elukoht;

            /**
             * Gets the value of the isikIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikukood() {
                return isikIsikukood;
            }

            /**
             * Sets the value of the isikIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikukood(String value) {
                this.isikIsikukood = value;
            }

            /**
             * Gets the value of the isikIsikupnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikupnimi() {
                return isikIsikupnimi;
            }

            /**
             * Sets the value of the isikIsikupnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikupnimi(String value) {
                this.isikIsikupnimi = value;
            }

            /**
             * Gets the value of the isikIsikuenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikuenimi() {
                return isikIsikuenimi;
            }

            /**
             * Sets the value of the isikIsikuenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikuenimi(String value) {
                this.isikIsikuenimi = value;
            }

            /**
             * Gets the value of the elukoht property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the elukoht property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getElukoht().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RR450ResponseType.Isikud.Isik.Elukoht }
             * 
             * 
             */
            public List<RR450ResponseType.Isikud.Isik.Elukoht> getElukoht() {
                if (elukoht == null) {
                    elukoht = new ArrayList<RR450ResponseType.Isikud.Isik.Elukoht>();
                }
                return this.elukoht;
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
             *         &lt;element name="Elukoht.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.Asula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.Maja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.Korter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.ElukohaAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.AadressTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.ADSAadresstekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Elukoht.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "elukohtRiik",
                "elukohtMaakond",
                "elukohtVald",
                "elukohtAsula",
                "elukohtVaikekoht",
                "elukohtNimi",
                "elukohtTanav",
                "elukohtMaja",
                "elukohtKorter",
                "elukohtPostiindeks",
                "elukohtElukohaAlgus",
                "elukohtAadressTekst",
                "elukohtADSAadresstekst",
                "elukohtADSADRID",
                "elukohtADSOID"
            })
            public static class Elukoht {

                @XmlElement(name = "Elukoht.Riik", required = true)
                protected String elukohtRiik;
                @XmlElement(name = "Elukoht.Maakond", required = true)
                protected String elukohtMaakond;
                @XmlElement(name = "Elukoht.Vald", required = true)
                protected String elukohtVald;
                @XmlElement(name = "Elukoht.Asula", required = true)
                protected String elukohtAsula;
                @XmlElement(name = "Elukoht.Vaikekoht", required = true)
                protected String elukohtVaikekoht;
                @XmlElement(name = "Elukoht.Nimi", required = true)
                protected String elukohtNimi;
                @XmlElement(name = "Elukoht.Tanav", required = true)
                protected String elukohtTanav;
                @XmlElement(name = "Elukoht.Maja", required = true)
                protected String elukohtMaja;
                @XmlElement(name = "Elukoht.Korter", required = true)
                protected String elukohtKorter;
                @XmlElement(name = "Elukoht.Postiindeks", required = true)
                protected String elukohtPostiindeks;
                @XmlElement(name = "Elukoht.ElukohaAlgus", required = true)
                protected String elukohtElukohaAlgus;
                @XmlElement(name = "Elukoht.AadressTekst", required = true)
                protected String elukohtAadressTekst;
                @XmlElement(name = "Elukoht.ADSAadresstekst", required = true)
                protected String elukohtADSAadresstekst;
                @XmlElement(name = "Elukoht.ADS_ADR_ID", required = true)
                protected String elukohtADSADRID;
                @XmlElement(name = "Elukoht.ADS_OID", required = true)
                protected String elukohtADSOID;

                /**
                 * Gets the value of the elukohtRiik property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtRiik() {
                    return elukohtRiik;
                }

                /**
                 * Sets the value of the elukohtRiik property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtRiik(String value) {
                    this.elukohtRiik = value;
                }

                /**
                 * Gets the value of the elukohtMaakond property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtMaakond() {
                    return elukohtMaakond;
                }

                /**
                 * Sets the value of the elukohtMaakond property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtMaakond(String value) {
                    this.elukohtMaakond = value;
                }

                /**
                 * Gets the value of the elukohtVald property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtVald() {
                    return elukohtVald;
                }

                /**
                 * Sets the value of the elukohtVald property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtVald(String value) {
                    this.elukohtVald = value;
                }

                /**
                 * Gets the value of the elukohtAsula property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtAsula() {
                    return elukohtAsula;
                }

                /**
                 * Sets the value of the elukohtAsula property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtAsula(String value) {
                    this.elukohtAsula = value;
                }

                /**
                 * Gets the value of the elukohtVaikekoht property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtVaikekoht() {
                    return elukohtVaikekoht;
                }

                /**
                 * Sets the value of the elukohtVaikekoht property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtVaikekoht(String value) {
                    this.elukohtVaikekoht = value;
                }

                /**
                 * Gets the value of the elukohtNimi property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtNimi() {
                    return elukohtNimi;
                }

                /**
                 * Sets the value of the elukohtNimi property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtNimi(String value) {
                    this.elukohtNimi = value;
                }

                /**
                 * Gets the value of the elukohtTanav property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtTanav() {
                    return elukohtTanav;
                }

                /**
                 * Sets the value of the elukohtTanav property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtTanav(String value) {
                    this.elukohtTanav = value;
                }

                /**
                 * Gets the value of the elukohtMaja property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtMaja() {
                    return elukohtMaja;
                }

                /**
                 * Sets the value of the elukohtMaja property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtMaja(String value) {
                    this.elukohtMaja = value;
                }

                /**
                 * Gets the value of the elukohtKorter property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtKorter() {
                    return elukohtKorter;
                }

                /**
                 * Sets the value of the elukohtKorter property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtKorter(String value) {
                    this.elukohtKorter = value;
                }

                /**
                 * Gets the value of the elukohtPostiindeks property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtPostiindeks() {
                    return elukohtPostiindeks;
                }

                /**
                 * Sets the value of the elukohtPostiindeks property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtPostiindeks(String value) {
                    this.elukohtPostiindeks = value;
                }

                /**
                 * Gets the value of the elukohtElukohaAlgus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtElukohaAlgus() {
                    return elukohtElukohaAlgus;
                }

                /**
                 * Sets the value of the elukohtElukohaAlgus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtElukohaAlgus(String value) {
                    this.elukohtElukohaAlgus = value;
                }

                /**
                 * Gets the value of the elukohtAadressTekst property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtAadressTekst() {
                    return elukohtAadressTekst;
                }

                /**
                 * Sets the value of the elukohtAadressTekst property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtAadressTekst(String value) {
                    this.elukohtAadressTekst = value;
                }

                /**
                 * Gets the value of the elukohtADSAadresstekst property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtADSAadresstekst() {
                    return elukohtADSAadresstekst;
                }

                /**
                 * Sets the value of the elukohtADSAadresstekst property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtADSAadresstekst(String value) {
                    this.elukohtADSAadresstekst = value;
                }

                /**
                 * Gets the value of the elukohtADSADRID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtADSADRID() {
                    return elukohtADSADRID;
                }

                /**
                 * Sets the value of the elukohtADSADRID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtADSADRID(String value) {
                    this.elukohtADSADRID = value;
                }

                /**
                 * Gets the value of the elukohtADSOID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukohtADSOID() {
                    return elukohtADSOID;
                }

                /**
                 * Sets the value of the elukohtADSOID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukohtADSOID(String value) {
                    this.elukohtADSOID = value;
                }

            }

        }

    }

}
