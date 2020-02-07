
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RRisikuViibimiskohtResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRisikuViibimiskohtResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsikuAadressid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="IsikuAadress" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AadressiStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Riik"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Maakond"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Omavalitsus"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="AsulaLinnaosa"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Liikluspind" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Maauksus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AadressiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="HooneosaNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="PostiIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AdsOid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AdsKoodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AdsAdobId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="AlgusKP" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                             &lt;element name="LopuKP" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                             &lt;element name="DokAsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRisikuViibimiskohtResponseType", propOrder = {
    "isikuAadressid"
})
public class RRisikuViibimiskohtResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "IsikuAadressid", required = true)
    protected RRisikuViibimiskohtResponseType.IsikuAadressid isikuAadressid;

    /**
     * Gets the value of the isikuAadressid property.
     * 
     * @return
     *     possible object is
     *     {@link RRisikuViibimiskohtResponseType.IsikuAadressid }
     *     
     */
    public RRisikuViibimiskohtResponseType.IsikuAadressid getIsikuAadressid() {
        return isikuAadressid;
    }

    /**
     * Sets the value of the isikuAadressid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRisikuViibimiskohtResponseType.IsikuAadressid }
     *     
     */
    public void setIsikuAadressid(RRisikuViibimiskohtResponseType.IsikuAadressid value) {
        this.isikuAadressid = value;
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
     *         &lt;element name="IsikuAadress" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AadressiStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Riik"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Maakond"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Omavalitsus"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="AsulaLinnaosa"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Liikluspind" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Maauksus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AadressiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="HooneosaNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="PostiIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AdsOid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AdsKoodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AdsAdobId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="AlgusKP" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                   &lt;element name="LopuKP" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                   &lt;element name="DokAsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "isikuAadress"
    })
    public static class IsikuAadressid {

        @XmlElement(name = "IsikuAadress")
        protected List<RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress> isikuAadress;

        /**
         * Gets the value of the isikuAadress property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the isikuAadress property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIsikuAadress().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress }
         * 
         * 
         */
        public List<RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress> getIsikuAadress() {
            if (isikuAadress == null) {
                isikuAadress = new ArrayList<RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress>();
            }
            return this.isikuAadress;
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
         *         &lt;element name="AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AadressiStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Riik"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Maakond"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Omavalitsus"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="AsulaLinnaosa"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Liikluspind" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Maauksus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AadressiNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="HooneosaNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="PostiIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AdsOid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AdsKoodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AdsAdobId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="AlgusKP" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *         &lt;element name="LopuKP" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *         &lt;element name="DokAsutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "aadressiLiik",
            "aadressiStaatus",
            "riik",
            "maakond",
            "omavalitsus",
            "asulaLinnaosa",
            "vaikekoht",
            "liikluspind",
            "maauksus",
            "aadressiNumber",
            "hooneosaNumber",
            "postiIndeks",
            "adsAdrID",
            "adsOid",
            "adsKoodaadress",
            "adsAdobId",
            "aadressTekstina",
            "algusKP",
            "lopuKP",
            "dokAsutuseNimi"
        })
        public static class IsikuAadress {

            @XmlElement(name = "AadressiLiik", required = true)
            protected String aadressiLiik;
            @XmlElement(name = "AadressiStaatus", required = true)
            protected String aadressiStaatus;
            @XmlElement(name = "Riik", required = true)
            protected RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Riik riik;
            @XmlElement(name = "Maakond", required = true)
            protected RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Maakond maakond;
            @XmlElement(name = "Omavalitsus", required = true)
            protected RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Omavalitsus omavalitsus;
            @XmlElement(name = "AsulaLinnaosa", required = true)
            protected RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.AsulaLinnaosa asulaLinnaosa;
            @XmlElement(name = "Vaikekoht", required = true)
            protected String vaikekoht;
            @XmlElement(name = "Liikluspind", required = true)
            protected String liikluspind;
            @XmlElement(name = "Maauksus", required = true)
            protected String maauksus;
            @XmlElement(name = "AadressiNumber", required = true)
            protected String aadressiNumber;
            @XmlElement(name = "HooneosaNumber", required = true)
            protected String hooneosaNumber;
            @XmlElement(name = "PostiIndeks", required = true)
            protected String postiIndeks;
            @XmlElement(name = "AdsAdrID", required = true)
            protected String adsAdrID;
            @XmlElement(name = "AdsOid", required = true)
            protected String adsOid;
            @XmlElement(name = "AdsKoodaadress", required = true)
            protected String adsKoodaadress;
            @XmlElement(name = "AdsAdobId", required = true)
            protected String adsAdobId;
            @XmlElement(name = "AadressTekstina", required = true)
            protected String aadressTekstina;
            @XmlElement(name = "AlgusKP", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar algusKP;
            @XmlElement(name = "LopuKP", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar lopuKP;
            @XmlElement(name = "DokAsutuseNimi", required = true)
            protected String dokAsutuseNimi;

            /**
             * Gets the value of the aadressiLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressiLiik() {
                return aadressiLiik;
            }

            /**
             * Sets the value of the aadressiLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressiLiik(String value) {
                this.aadressiLiik = value;
            }

            /**
             * Gets the value of the aadressiStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressiStaatus() {
                return aadressiStaatus;
            }

            /**
             * Sets the value of the aadressiStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressiStaatus(String value) {
                this.aadressiStaatus = value;
            }

            /**
             * Gets the value of the riik property.
             * 
             * @return
             *     possible object is
             *     {@link RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Riik }
             *     
             */
            public RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Riik getRiik() {
                return riik;
            }

            /**
             * Sets the value of the riik property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Riik }
             *     
             */
            public void setRiik(RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Riik value) {
                this.riik = value;
            }

            /**
             * Gets the value of the maakond property.
             * 
             * @return
             *     possible object is
             *     {@link RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Maakond }
             *     
             */
            public RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Maakond getMaakond() {
                return maakond;
            }

            /**
             * Sets the value of the maakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Maakond }
             *     
             */
            public void setMaakond(RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Maakond value) {
                this.maakond = value;
            }

            /**
             * Gets the value of the omavalitsus property.
             * 
             * @return
             *     possible object is
             *     {@link RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Omavalitsus }
             *     
             */
            public RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Omavalitsus getOmavalitsus() {
                return omavalitsus;
            }

            /**
             * Sets the value of the omavalitsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Omavalitsus }
             *     
             */
            public void setOmavalitsus(RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.Omavalitsus value) {
                this.omavalitsus = value;
            }

            /**
             * Gets the value of the asulaLinnaosa property.
             * 
             * @return
             *     possible object is
             *     {@link RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.AsulaLinnaosa }
             *     
             */
            public RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.AsulaLinnaosa getAsulaLinnaosa() {
                return asulaLinnaosa;
            }

            /**
             * Sets the value of the asulaLinnaosa property.
             * 
             * @param value
             *     allowed object is
             *     {@link RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.AsulaLinnaosa }
             *     
             */
            public void setAsulaLinnaosa(RRisikuViibimiskohtResponseType.IsikuAadressid.IsikuAadress.AsulaLinnaosa value) {
                this.asulaLinnaosa = value;
            }

            /**
             * Gets the value of the vaikekoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVaikekoht() {
                return vaikekoht;
            }

            /**
             * Sets the value of the vaikekoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVaikekoht(String value) {
                this.vaikekoht = value;
            }

            /**
             * Gets the value of the liikluspind property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLiikluspind() {
                return liikluspind;
            }

            /**
             * Sets the value of the liikluspind property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLiikluspind(String value) {
                this.liikluspind = value;
            }

            /**
             * Gets the value of the maauksus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMaauksus() {
                return maauksus;
            }

            /**
             * Sets the value of the maauksus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMaauksus(String value) {
                this.maauksus = value;
            }

            /**
             * Gets the value of the aadressiNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressiNumber() {
                return aadressiNumber;
            }

            /**
             * Sets the value of the aadressiNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressiNumber(String value) {
                this.aadressiNumber = value;
            }

            /**
             * Gets the value of the hooneosaNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooneosaNumber() {
                return hooneosaNumber;
            }

            /**
             * Sets the value of the hooneosaNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooneosaNumber(String value) {
                this.hooneosaNumber = value;
            }

            /**
             * Gets the value of the postiIndeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostiIndeks() {
                return postiIndeks;
            }

            /**
             * Sets the value of the postiIndeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostiIndeks(String value) {
                this.postiIndeks = value;
            }

            /**
             * Gets the value of the adsAdrID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAdsAdrID() {
                return adsAdrID;
            }

            /**
             * Sets the value of the adsAdrID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAdsAdrID(String value) {
                this.adsAdrID = value;
            }

            /**
             * Gets the value of the adsOid property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAdsOid() {
                return adsOid;
            }

            /**
             * Sets the value of the adsOid property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAdsOid(String value) {
                this.adsOid = value;
            }

            /**
             * Gets the value of the adsKoodaadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAdsKoodaadress() {
                return adsKoodaadress;
            }

            /**
             * Sets the value of the adsKoodaadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAdsKoodaadress(String value) {
                this.adsKoodaadress = value;
            }

            /**
             * Gets the value of the adsAdobId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAdsAdobId() {
                return adsAdobId;
            }

            /**
             * Sets the value of the adsAdobId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAdsAdobId(String value) {
                this.adsAdobId = value;
            }

            /**
             * Gets the value of the aadressTekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAadressTekstina() {
                return aadressTekstina;
            }

            /**
             * Sets the value of the aadressTekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAadressTekstina(String value) {
                this.aadressTekstina = value;
            }

            /**
             * Gets the value of the algusKP property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getAlgusKP() {
                return algusKP;
            }

            /**
             * Sets the value of the algusKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setAlgusKP(XMLGregorianCalendar value) {
                this.algusKP = value;
            }

            /**
             * Gets the value of the lopuKP property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getLopuKP() {
                return lopuKP;
            }

            /**
             * Sets the value of the lopuKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setLopuKP(XMLGregorianCalendar value) {
                this.lopuKP = value;
            }

            /**
             * Gets the value of the dokAsutuseNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokAsutuseNimi() {
                return dokAsutuseNimi;
            }

            /**
             * Sets the value of the dokAsutuseNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokAsutuseNimi(String value) {
                this.dokAsutuseNimi = value;
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
             *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "nimi"
            })
            public static class AsulaLinnaosa {

                @XmlElement(name = "Kood", required = true)
                protected String kood;
                @XmlElement(name = "Nimi", required = true)
                protected String nimi;

                /**
                 * Gets the value of the kood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKood() {
                    return kood;
                }

                /**
                 * Sets the value of the kood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKood(String value) {
                    this.kood = value;
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
             *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "nimi"
            })
            public static class Maakond {

                @XmlElement(name = "Kood", required = true)
                protected String kood;
                @XmlElement(name = "Nimi", required = true)
                protected String nimi;

                /**
                 * Gets the value of the kood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKood() {
                    return kood;
                }

                /**
                 * Sets the value of the kood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKood(String value) {
                    this.kood = value;
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
             *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "nimi"
            })
            public static class Omavalitsus {

                @XmlElement(name = "Kood", required = true)
                protected String kood;
                @XmlElement(name = "Nimi", required = true)
                protected String nimi;

                /**
                 * Gets the value of the kood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKood() {
                    return kood;
                }

                /**
                 * Sets the value of the kood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKood(String value) {
                    this.kood = value;
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
             *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "nimi"
            })
            public static class Riik {

                @XmlElement(name = "Kood", required = true)
                protected String kood;
                @XmlElement(name = "Nimi", required = true)
                protected String nimi;

                /**
                 * Gets the value of the kood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKood() {
                    return kood;
                }

                /**
                 * Sets the value of the kood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKood(String value) {
                    this.kood = value;
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

            }

        }

    }

}
