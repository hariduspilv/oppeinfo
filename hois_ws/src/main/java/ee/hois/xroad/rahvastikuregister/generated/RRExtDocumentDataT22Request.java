
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRExtDocumentDataT22Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRExtDocumentDataT22Request"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Yldosa"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Dokument"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="DokumendiLiik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
 *                             &lt;element name="Seeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="KoostanudAsutus"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
 *                                       &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="AmetnikuIsikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                             &lt;element name="AmetnikuNimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Lisaandmed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *         &lt;element name="Pohiosa"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Osalised"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Osaline" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="OsaliseRoll" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
 *                                       &lt;element name="Isik"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="ValisriigiIkoodid"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                                             &lt;complexType&gt;
 *                                                               &lt;complexContent&gt;
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                                   &lt;sequence&gt;
 *                                                                     &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
 *                                                                     &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                                   &lt;/sequence&gt;
 *                                                                 &lt;/restriction&gt;
 *                                                               &lt;/complexContent&gt;
 *                                                             &lt;/complexType&gt;
 *                                                           &lt;/element&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                                 &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="SynniKP" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                                                 &lt;element name="Sugu" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
 *                                                 &lt;element name="PohiKodakondsus"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                                 &lt;element name="Kodakondsused"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                                             &lt;complexType&gt;
 *                                                               &lt;complexContent&gt;
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                                   &lt;sequence&gt;
 *                                                                     &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
 *                                                                   &lt;/sequence&gt;
 *                                                                 &lt;/restriction&gt;
 *                                                               &lt;/complexContent&gt;
 *                                                             &lt;/complexType&gt;
 *                                                           &lt;/element&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
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
@XmlType(name = "RRExtDocumentDataT22Request", propOrder = {
    "yldosa",
    "pohiosa"
})
public class RRExtDocumentDataT22Request {

    @XmlElement(name = "Yldosa", required = true)
    protected RRExtDocumentDataT22Request.Yldosa yldosa;
    @XmlElement(name = "Pohiosa", required = true)
    protected RRExtDocumentDataT22Request.Pohiosa pohiosa;

    /**
     * Gets the value of the yldosa property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataT22Request.Yldosa }
     *     
     */
    public RRExtDocumentDataT22Request.Yldosa getYldosa() {
        return yldosa;
    }

    /**
     * Sets the value of the yldosa property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataT22Request.Yldosa }
     *     
     */
    public void setYldosa(RRExtDocumentDataT22Request.Yldosa value) {
        this.yldosa = value;
    }

    /**
     * Gets the value of the pohiosa property.
     * 
     * @return
     *     possible object is
     *     {@link RRExtDocumentDataT22Request.Pohiosa }
     *     
     */
    public RRExtDocumentDataT22Request.Pohiosa getPohiosa() {
        return pohiosa;
    }

    /**
     * Sets the value of the pohiosa property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRExtDocumentDataT22Request.Pohiosa }
     *     
     */
    public void setPohiosa(RRExtDocumentDataT22Request.Pohiosa value) {
        this.pohiosa = value;
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
     *         &lt;element name="Osalised"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Osaline" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="OsaliseRoll" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
     *                             &lt;element name="Isik"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="ValisriigiIkoodid"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
     *                                                   &lt;complexType&gt;
     *                                                     &lt;complexContent&gt;
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                                         &lt;sequence&gt;
     *                                                           &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
     *                                                           &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                         &lt;/sequence&gt;
     *                                                       &lt;/restriction&gt;
     *                                                     &lt;/complexContent&gt;
     *                                                   &lt;/complexType&gt;
     *                                                 &lt;/element&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                       &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="SynniKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                                       &lt;element name="Sugu" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
     *                                       &lt;element name="PohiKodakondsus"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                       &lt;element name="Kodakondsused"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
     *                                                   &lt;complexType&gt;
     *                                                     &lt;complexContent&gt;
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                                         &lt;sequence&gt;
     *                                                           &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
     *                                                         &lt;/sequence&gt;
     *                                                       &lt;/restriction&gt;
     *                                                     &lt;/complexContent&gt;
     *                                                   &lt;/complexType&gt;
     *                                                 &lt;/element&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
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
        "osalised"
    })
    public static class Pohiosa {

        @XmlElement(name = "Osalised", required = true)
        protected RRExtDocumentDataT22Request.Pohiosa.Osalised osalised;

        /**
         * Gets the value of the osalised property.
         * 
         * @return
         *     possible object is
         *     {@link RRExtDocumentDataT22Request.Pohiosa.Osalised }
         *     
         */
        public RRExtDocumentDataT22Request.Pohiosa.Osalised getOsalised() {
            return osalised;
        }

        /**
         * Sets the value of the osalised property.
         * 
         * @param value
         *     allowed object is
         *     {@link RRExtDocumentDataT22Request.Pohiosa.Osalised }
         *     
         */
        public void setOsalised(RRExtDocumentDataT22Request.Pohiosa.Osalised value) {
            this.osalised = value;
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
         *         &lt;element name="Osaline" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="OsaliseRoll" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
         *                   &lt;element name="Isik"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="ValisriigiIkoodid"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
         *                                         &lt;complexType&gt;
         *                                           &lt;complexContent&gt;
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                               &lt;sequence&gt;
         *                                                 &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
         *                                                 &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                               &lt;/sequence&gt;
         *                                             &lt;/restriction&gt;
         *                                           &lt;/complexContent&gt;
         *                                         &lt;/complexType&gt;
         *                                       &lt;/element&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="SynniKP" type="{http://rr.x-road.eu/producer}date"/&gt;
         *                             &lt;element name="Sugu" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
         *                             &lt;element name="PohiKodakondsus"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                             &lt;element name="Kodakondsused"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
         *                                         &lt;complexType&gt;
         *                                           &lt;complexContent&gt;
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                               &lt;sequence&gt;
         *                                                 &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
         *                                               &lt;/sequence&gt;
         *                                             &lt;/restriction&gt;
         *                                           &lt;/complexContent&gt;
         *                                         &lt;/complexType&gt;
         *                                       &lt;/element&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
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
            "osaline"
        })
        public static class Osalised {

            @XmlElement(name = "Osaline", required = true)
            protected List<RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline> osaline;

            /**
             * Gets the value of the osaline property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the osaline property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getOsaline().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline }
             * 
             * 
             */
            public List<RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline> getOsaline() {
                if (osaline == null) {
                    osaline = new ArrayList<RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline>();
                }
                return this.osaline;
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
             *         &lt;element name="OsaliseRoll" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
             *         &lt;element name="Isik"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="ValisriigiIkoodid"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
             *                               &lt;complexType&gt;
             *                                 &lt;complexContent&gt;
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                                     &lt;sequence&gt;
             *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
             *                                       &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                                     &lt;/sequence&gt;
             *                                   &lt;/restriction&gt;
             *                                 &lt;/complexContent&gt;
             *                               &lt;/complexType&gt;
             *                             &lt;/element&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="SynniKP" type="{http://rr.x-road.eu/producer}date"/&gt;
             *                   &lt;element name="Sugu" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
             *                   &lt;element name="PohiKodakondsus"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                   &lt;element name="Kodakondsused"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
             *                               &lt;complexType&gt;
             *                                 &lt;complexContent&gt;
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                                     &lt;sequence&gt;
             *                                       &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
             *                                     &lt;/sequence&gt;
             *                                   &lt;/restriction&gt;
             *                                 &lt;/complexContent&gt;
             *                               &lt;/complexType&gt;
             *                             &lt;/element&gt;
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
                "osaliseRoll",
                "isik"
            })
            public static class Osaline {

                @XmlElement(name = "OsaliseRoll", required = true)
                protected Kodife osaliseRoll;
                @XmlElement(name = "Isik", required = true)
                protected RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik isik;

                /**
                 * Gets the value of the osaliseRoll property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Kodife }
                 *     
                 */
                public Kodife getOsaliseRoll() {
                    return osaliseRoll;
                }

                /**
                 * Sets the value of the osaliseRoll property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Kodife }
                 *     
                 */
                public void setOsaliseRoll(Kodife value) {
                    this.osaliseRoll = value;
                }

                /**
                 * Gets the value of the isik property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik }
                 *     
                 */
                public RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik getIsik() {
                    return isik;
                }

                /**
                 * Sets the value of the isik property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik }
                 *     
                 */
                public void setIsik(RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik value) {
                    this.isik = value;
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
                 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="ValisriigiIkoodid"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
                 *                     &lt;complexType&gt;
                 *                       &lt;complexContent&gt;
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                           &lt;sequence&gt;
                 *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
                 *                             &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="SynniKP" type="{http://rr.x-road.eu/producer}date"/&gt;
                 *         &lt;element name="Sugu" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
                 *         &lt;element name="PohiKodakondsus"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
                 *         &lt;element name="Kodakondsused"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
                 *                     &lt;complexType&gt;
                 *                       &lt;complexContent&gt;
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                           &lt;sequence&gt;
                 *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
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
                    "isikukood",
                    "valisriigiIkoodid",
                    "eesnimi",
                    "perenimi",
                    "synniKP",
                    "sugu",
                    "pohiKodakondsus",
                    "kodakondsused"
                })
                public static class Isik {

                    @XmlElement(name = "Isikukood")
                    protected String isikukood;
                    @XmlElement(name = "ValisriigiIkoodid", required = true)
                    protected RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.ValisriigiIkoodid valisriigiIkoodid;
                    @XmlElement(name = "Eesnimi", required = true)
                    protected String eesnimi;
                    @XmlElement(name = "Perenimi", required = true)
                    protected String perenimi;
                    @XmlElement(name = "SynniKP", required = true)
                    protected String synniKP;
                    @XmlElement(name = "Sugu", required = true)
                    protected Kodife sugu;
                    @XmlElement(name = "PohiKodakondsus", required = true)
                    protected RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.PohiKodakondsus pohiKodakondsus;
                    @XmlElement(name = "Kodakondsused", required = true)
                    protected RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.Kodakondsused kodakondsused;

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
                     * Gets the value of the valisriigiIkoodid property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.ValisriigiIkoodid }
                     *     
                     */
                    public RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.ValisriigiIkoodid getValisriigiIkoodid() {
                        return valisriigiIkoodid;
                    }

                    /**
                     * Sets the value of the valisriigiIkoodid property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.ValisriigiIkoodid }
                     *     
                     */
                    public void setValisriigiIkoodid(RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.ValisriigiIkoodid value) {
                        this.valisriigiIkoodid = value;
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
                     * Gets the value of the synniKP property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSynniKP() {
                        return synniKP;
                    }

                    /**
                     * Sets the value of the synniKP property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSynniKP(String value) {
                        this.synniKP = value;
                    }

                    /**
                     * Gets the value of the sugu property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Kodife }
                     *     
                     */
                    public Kodife getSugu() {
                        return sugu;
                    }

                    /**
                     * Sets the value of the sugu property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Kodife }
                     *     
                     */
                    public void setSugu(Kodife value) {
                        this.sugu = value;
                    }

                    /**
                     * Gets the value of the pohiKodakondsus property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.PohiKodakondsus }
                     *     
                     */
                    public RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.PohiKodakondsus getPohiKodakondsus() {
                        return pohiKodakondsus;
                    }

                    /**
                     * Sets the value of the pohiKodakondsus property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.PohiKodakondsus }
                     *     
                     */
                    public void setPohiKodakondsus(RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.PohiKodakondsus value) {
                        this.pohiKodakondsus = value;
                    }

                    /**
                     * Gets the value of the kodakondsused property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.Kodakondsused }
                     *     
                     */
                    public RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.Kodakondsused getKodakondsused() {
                        return kodakondsused;
                    }

                    /**
                     * Sets the value of the kodakondsused property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.Kodakondsused }
                     *     
                     */
                    public void setKodakondsused(RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.Kodakondsused value) {
                        this.kodakondsused = value;
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
                     *         &lt;element name="Kodakondsus" maxOccurs="unbounded" minOccurs="0"&gt;
                     *           &lt;complexType&gt;
                     *             &lt;complexContent&gt;
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *                 &lt;sequence&gt;
                     *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
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
                        "kodakondsus"
                    })
                    public static class Kodakondsused {

                        @XmlElement(name = "Kodakondsus")
                        protected List<RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.Kodakondsused.Kodakondsus> kodakondsus;

                        /**
                         * Gets the value of the kodakondsus property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the kodakondsus property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getKodakondsus().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.Kodakondsused.Kodakondsus }
                         * 
                         * 
                         */
                        public List<RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.Kodakondsused.Kodakondsus> getKodakondsus() {
                            if (kodakondsus == null) {
                                kodakondsus = new ArrayList<RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.Kodakondsused.Kodakondsus>();
                            }
                            return this.kodakondsus;
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
                         *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
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
                            "riik"
                        })
                        public static class Kodakondsus {

                            @XmlElement(name = "Riik", required = true)
                            protected Riigii riik;

                            /**
                             * Gets the value of the riik property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link Riigii }
                             *     
                             */
                            public Riigii getRiik() {
                                return riik;
                            }

                            /**
                             * Sets the value of the riik property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link Riigii }
                             *     
                             */
                            public void setRiik(Riigii value) {
                                this.riik = value;
                            }

                        }

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
                     *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Riigii"/&gt;
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
                        "riik"
                    })
                    public static class PohiKodakondsus {

                        @XmlElement(name = "Riik", required = true)
                        protected Riigii riik;

                        /**
                         * Gets the value of the riik property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Riigii }
                         *     
                         */
                        public Riigii getRiik() {
                            return riik;
                        }

                        /**
                         * Sets the value of the riik property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Riigii }
                         *     
                         */
                        public void setRiik(Riigii value) {
                            this.riik = value;
                        }

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
                     *         &lt;element name="ValisriigiIK" maxOccurs="unbounded" minOccurs="0"&gt;
                     *           &lt;complexType&gt;
                     *             &lt;complexContent&gt;
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *                 &lt;sequence&gt;
                     *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
                     *                   &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                        "valisriigiIK"
                    })
                    public static class ValisriigiIkoodid {

                        @XmlElement(name = "ValisriigiIK")
                        protected List<RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.ValisriigiIkoodid.ValisriigiIK> valisriigiIK;

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
                         * {@link RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.ValisriigiIkoodid.ValisriigiIK }
                         * 
                         * 
                         */
                        public List<RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.ValisriigiIkoodid.ValisriigiIK> getValisriigiIK() {
                            if (valisriigiIK == null) {
                                valisriigiIK = new ArrayList<RRExtDocumentDataT22Request.Pohiosa.Osalised.Osaline.Isik.ValisriigiIkoodid.ValisriigiIK>();
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
                         *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
                         *         &lt;element name="ValisriigiIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                            "riik",
                            "valisriigiIK"
                        })
                        public static class ValisriigiIK {

                            @XmlElement(name = "Riik", required = true)
                            protected Kodife riik;
                            @XmlElement(name = "ValisriigiIK", required = true)
                            protected String valisriigiIK;

                            /**
                             * Gets the value of the riik property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link Kodife }
                             *     
                             */
                            public Kodife getRiik() {
                                return riik;
                            }

                            /**
                             * Sets the value of the riik property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link Kodife }
                             *     
                             */
                            public void setRiik(Kodife value) {
                                this.riik = value;
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

                        }

                    }

                }

            }

        }

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
     *         &lt;element name="Dokument"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="DokumendiLiik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
     *                   &lt;element name="Seeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="KoostanudAsutus"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
     *                             &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="AmetnikuIsikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *                   &lt;element name="AmetnikuNimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Lisaandmed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "dokument"
    })
    public static class Yldosa {

        @XmlElement(name = "Dokument", required = true)
        protected RRExtDocumentDataT22Request.Yldosa.Dokument dokument;

        /**
         * Gets the value of the dokument property.
         * 
         * @return
         *     possible object is
         *     {@link RRExtDocumentDataT22Request.Yldosa.Dokument }
         *     
         */
        public RRExtDocumentDataT22Request.Yldosa.Dokument getDokument() {
            return dokument;
        }

        /**
         * Sets the value of the dokument property.
         * 
         * @param value
         *     allowed object is
         *     {@link RRExtDocumentDataT22Request.Yldosa.Dokument }
         *     
         */
        public void setDokument(RRExtDocumentDataT22Request.Yldosa.Dokument value) {
            this.dokument = value;
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
         *         &lt;element name="DokumendiLiik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
         *         &lt;element name="Seeria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="JoustumiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="KoostanudAsutus"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
         *                   &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="AmetnikuIsikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
         *         &lt;element name="AmetnikuNimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Lisaandmed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "dokumendiLiik",
            "seeria",
            "number",
            "valjaandmiseKP",
            "joustumiseKP",
            "koostanudAsutus",
            "ametnikuIsikukood",
            "ametnikuNimed",
            "lisaandmed"
        })
        public static class Dokument {

            @XmlElement(name = "DokumendiLiik", required = true)
            protected Kodife dokumendiLiik;
            @XmlElement(name = "Seeria")
            protected String seeria;
            @XmlElement(name = "Number", required = true)
            protected String number;
            @XmlElement(name = "ValjaandmiseKP", required = true)
            protected String valjaandmiseKP;
            @XmlElement(name = "JoustumiseKP")
            protected String joustumiseKP;
            @XmlElement(name = "KoostanudAsutus", required = true)
            protected RRExtDocumentDataT22Request.Yldosa.Dokument.KoostanudAsutus koostanudAsutus;
            @XmlElement(name = "AmetnikuIsikukood", required = true)
            protected String ametnikuIsikukood;
            @XmlElement(name = "AmetnikuNimed", required = true)
            protected String ametnikuNimed;
            @XmlElement(name = "Lisaandmed")
            protected String lisaandmed;

            /**
             * Gets the value of the dokumendiLiik property.
             * 
             * @return
             *     possible object is
             *     {@link Kodife }
             *     
             */
            public Kodife getDokumendiLiik() {
                return dokumendiLiik;
            }

            /**
             * Sets the value of the dokumendiLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link Kodife }
             *     
             */
            public void setDokumendiLiik(Kodife value) {
                this.dokumendiLiik = value;
            }

            /**
             * Gets the value of the seeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeeria() {
                return seeria;
            }

            /**
             * Sets the value of the seeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeeria(String value) {
                this.seeria = value;
            }

            /**
             * Gets the value of the number property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumber() {
                return number;
            }

            /**
             * Sets the value of the number property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumber(String value) {
                this.number = value;
            }

            /**
             * Gets the value of the valjaandmiseKP property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValjaandmiseKP() {
                return valjaandmiseKP;
            }

            /**
             * Sets the value of the valjaandmiseKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValjaandmiseKP(String value) {
                this.valjaandmiseKP = value;
            }

            /**
             * Gets the value of the joustumiseKP property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getJoustumiseKP() {
                return joustumiseKP;
            }

            /**
             * Sets the value of the joustumiseKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setJoustumiseKP(String value) {
                this.joustumiseKP = value;
            }

            /**
             * Gets the value of the koostanudAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link RRExtDocumentDataT22Request.Yldosa.Dokument.KoostanudAsutus }
             *     
             */
            public RRExtDocumentDataT22Request.Yldosa.Dokument.KoostanudAsutus getKoostanudAsutus() {
                return koostanudAsutus;
            }

            /**
             * Sets the value of the koostanudAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRExtDocumentDataT22Request.Yldosa.Dokument.KoostanudAsutus }
             *     
             */
            public void setKoostanudAsutus(RRExtDocumentDataT22Request.Yldosa.Dokument.KoostanudAsutus value) {
                this.koostanudAsutus = value;
            }

            /**
             * Gets the value of the ametnikuIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAmetnikuIsikukood() {
                return ametnikuIsikukood;
            }

            /**
             * Sets the value of the ametnikuIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAmetnikuIsikukood(String value) {
                this.ametnikuIsikukood = value;
            }

            /**
             * Gets the value of the ametnikuNimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAmetnikuNimed() {
                return ametnikuNimed;
            }

            /**
             * Sets the value of the ametnikuNimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAmetnikuNimed(String value) {
                this.ametnikuNimed = value;
            }

            /**
             * Gets the value of the lisaandmed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLisaandmed() {
                return lisaandmed;
            }

            /**
             * Sets the value of the lisaandmed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLisaandmed(String value) {
                this.lisaandmed = value;
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
             *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}Kodife"/&gt;
             *         &lt;element name="AsutuseRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="AsutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="AsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "riik",
                "asutuseRegNumber",
                "asutuseKood",
                "asutuseNimi"
            })
            public static class KoostanudAsutus {

                @XmlElement(name = "Riik", required = true)
                protected Kodife riik;
                @XmlElement(name = "AsutuseRegNumber", required = true)
                protected String asutuseRegNumber;
                @XmlElement(name = "AsutuseKood", required = true)
                protected String asutuseKood;
                @XmlElement(name = "AsutuseNimi")
                protected String asutuseNimi;

                /**
                 * Gets the value of the riik property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Kodife }
                 *     
                 */
                public Kodife getRiik() {
                    return riik;
                }

                /**
                 * Sets the value of the riik property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Kodife }
                 *     
                 */
                public void setRiik(Kodife value) {
                    this.riik = value;
                }

                /**
                 * Gets the value of the asutuseRegNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAsutuseRegNumber() {
                    return asutuseRegNumber;
                }

                /**
                 * Sets the value of the asutuseRegNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAsutuseRegNumber(String value) {
                    this.asutuseRegNumber = value;
                }

                /**
                 * Gets the value of the asutuseKood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAsutuseKood() {
                    return asutuseKood;
                }

                /**
                 * Sets the value of the asutuseKood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAsutuseKood(String value) {
                    this.asutuseKood = value;
                }

                /**
                 * Gets the value of the asutuseNimi property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAsutuseNimi() {
                    return asutuseNimi;
                }

                /**
                 * Sets the value of the asutuseNimi property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAsutuseNimi(String value) {
                    this.asutuseNimi = value;
                }

            }

        }

    }

}
