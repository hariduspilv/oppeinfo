
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR417ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR417ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikud" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Elukohaalgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Elukohalopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.IsikuOlekHetkel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.KirjeOlekHetkel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.IsikuOlekSeisuga" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.KirjeOlekSeisuga" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Vallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Kylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Kyla" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Vaikekohtkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Tanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Tanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Nimikd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_ADOB_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR417ResponseType", propOrder = {
    "isikud"
})
public class RR417ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikud")
    protected RR417ResponseType.Isikud isikud;

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR417ResponseType.Isikud }
     *     
     */
    public RR417ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR417ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR417ResponseType.Isikud value) {
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
     *                   &lt;element name="Isik.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Elukohaalgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Elukohalopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.IsikuOlekHetkel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.KirjeOlekHetkel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.IsikuOlekSeisuga" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.KirjeOlekSeisuga" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Vallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Kylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Kyla" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Vaikekohtkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Tanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Tanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Nimikd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_ADOB_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR417ResponseType.Isikud.Isik> isik;

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
         * {@link RR417ResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR417ResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR417ResponseType.Isikud.Isik>();
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
         *         &lt;element name="Isik.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Elukohaalgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Elukohalopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.IsikuOlekHetkel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.KirjeOlekHetkel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.IsikuOlekSeisuga" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.KirjeOlekSeisuga" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Vallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Kylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Kyla" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Vaikekohtkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Tanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Tanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Nimikd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_ADOB_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikCPerenimi",
            "isikCEesnimi",
            "isikCRiikKood",
            "isikCRiik",
            "isikElukohaalgus",
            "isikElukohalopp",
            "isikIsikuOlekHetkel",
            "isikKirjeOlekHetkel",
            "isikIsikuOlekSeisuga",
            "isikKirjeOlekSeisuga",
            "isikRiik",
            "isikMaakondkd",
            "isikMaakond",
            "isikVallakd",
            "isikVald",
            "isikKylakd",
            "isikKyla",
            "isikVaikekohtkd",
            "isikVaikekoht",
            "isikTanavakd",
            "isikTanavanm",
            "isikNimikd",
            "isikNimi",
            "isikMajanr",
            "isikKorternr",
            "isikADSADRID",
            "isikADSOID",
            "isikADSKoodaadress",
            "isikADSADOBOID"
        })
        public static class Isik {

            @XmlElement(name = "Isik.Isikukood", required = true)
            protected String isikIsikukood;
            @XmlElement(name = "Isik.cPerenimi", required = true)
            protected String isikCPerenimi;
            @XmlElement(name = "Isik.cEesnimi", required = true)
            protected String isikCEesnimi;
            @XmlElement(name = "Isik.cRiikKood", required = true)
            protected String isikCRiikKood;
            @XmlElement(name = "Isik.cRiik", required = true)
            protected String isikCRiik;
            @XmlElement(name = "Isik.Elukohaalgus", required = true)
            protected String isikElukohaalgus;
            @XmlElement(name = "Isik.Elukohalopp", required = true)
            protected String isikElukohalopp;
            @XmlElement(name = "Isik.IsikuOlekHetkel", required = true)
            protected String isikIsikuOlekHetkel;
            @XmlElement(name = "Isik.KirjeOlekHetkel", required = true)
            protected String isikKirjeOlekHetkel;
            @XmlElement(name = "Isik.IsikuOlekSeisuga", required = true)
            protected String isikIsikuOlekSeisuga;
            @XmlElement(name = "Isik.KirjeOlekSeisuga", required = true)
            protected String isikKirjeOlekSeisuga;
            @XmlElement(name = "Isik.Riik", required = true)
            protected String isikRiik;
            @XmlElement(name = "Isik.Maakondkd", required = true)
            protected String isikMaakondkd;
            @XmlElement(name = "Isik.Maakond", required = true)
            protected String isikMaakond;
            @XmlElement(name = "Isik.Vallakd", required = true)
            protected String isikVallakd;
            @XmlElement(name = "Isik.Vald", required = true)
            protected String isikVald;
            @XmlElement(name = "Isik.Kylakd", required = true)
            protected String isikKylakd;
            @XmlElement(name = "Isik.Kyla", required = true)
            protected String isikKyla;
            @XmlElement(name = "Isik.Vaikekohtkd", required = true)
            protected String isikVaikekohtkd;
            @XmlElement(name = "Isik.Vaikekoht", required = true)
            protected String isikVaikekoht;
            @XmlElement(name = "Isik.Tanavakd", required = true)
            protected String isikTanavakd;
            @XmlElement(name = "Isik.Tanavanm", required = true)
            protected String isikTanavanm;
            @XmlElement(name = "Isik.Nimikd", required = true)
            protected String isikNimikd;
            @XmlElement(name = "Isik.Nimi", required = true)
            protected String isikNimi;
            @XmlElement(name = "Isik.Majanr", required = true)
            protected String isikMajanr;
            @XmlElement(name = "Isik.Korternr", required = true)
            protected String isikKorternr;
            @XmlElement(name = "Isik.ADS_ADR_ID", required = true)
            protected String isikADSADRID;
            @XmlElement(name = "Isik.ADS_OID", required = true)
            protected String isikADSOID;
            @XmlElement(name = "Isik.ADS_Koodaadress", required = true)
            protected String isikADSKoodaadress;
            @XmlElement(name = "Isik.ADS_ADOB_OID", required = true)
            protected String isikADSADOBOID;

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
             * Gets the value of the isikCPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikCPerenimi() {
                return isikCPerenimi;
            }

            /**
             * Sets the value of the isikCPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikCPerenimi(String value) {
                this.isikCPerenimi = value;
            }

            /**
             * Gets the value of the isikCEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikCEesnimi() {
                return isikCEesnimi;
            }

            /**
             * Sets the value of the isikCEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikCEesnimi(String value) {
                this.isikCEesnimi = value;
            }

            /**
             * Gets the value of the isikCRiikKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikCRiikKood() {
                return isikCRiikKood;
            }

            /**
             * Sets the value of the isikCRiikKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikCRiikKood(String value) {
                this.isikCRiikKood = value;
            }

            /**
             * Gets the value of the isikCRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikCRiik() {
                return isikCRiik;
            }

            /**
             * Sets the value of the isikCRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikCRiik(String value) {
                this.isikCRiik = value;
            }

            /**
             * Gets the value of the isikElukohaalgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukohaalgus() {
                return isikElukohaalgus;
            }

            /**
             * Sets the value of the isikElukohaalgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukohaalgus(String value) {
                this.isikElukohaalgus = value;
            }

            /**
             * Gets the value of the isikElukohalopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukohalopp() {
                return isikElukohalopp;
            }

            /**
             * Sets the value of the isikElukohalopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukohalopp(String value) {
                this.isikElukohalopp = value;
            }

            /**
             * Gets the value of the isikIsikuOlekHetkel property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikuOlekHetkel() {
                return isikIsikuOlekHetkel;
            }

            /**
             * Sets the value of the isikIsikuOlekHetkel property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikuOlekHetkel(String value) {
                this.isikIsikuOlekHetkel = value;
            }

            /**
             * Gets the value of the isikKirjeOlekHetkel property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKirjeOlekHetkel() {
                return isikKirjeOlekHetkel;
            }

            /**
             * Sets the value of the isikKirjeOlekHetkel property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKirjeOlekHetkel(String value) {
                this.isikKirjeOlekHetkel = value;
            }

            /**
             * Gets the value of the isikIsikuOlekSeisuga property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikuOlekSeisuga() {
                return isikIsikuOlekSeisuga;
            }

            /**
             * Sets the value of the isikIsikuOlekSeisuga property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikuOlekSeisuga(String value) {
                this.isikIsikuOlekSeisuga = value;
            }

            /**
             * Gets the value of the isikKirjeOlekSeisuga property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKirjeOlekSeisuga() {
                return isikKirjeOlekSeisuga;
            }

            /**
             * Sets the value of the isikKirjeOlekSeisuga property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKirjeOlekSeisuga(String value) {
                this.isikKirjeOlekSeisuga = value;
            }

            /**
             * Gets the value of the isikRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikRiik() {
                return isikRiik;
            }

            /**
             * Sets the value of the isikRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikRiik(String value) {
                this.isikRiik = value;
            }

            /**
             * Gets the value of the isikMaakondkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMaakondkd() {
                return isikMaakondkd;
            }

            /**
             * Sets the value of the isikMaakondkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMaakondkd(String value) {
                this.isikMaakondkd = value;
            }

            /**
             * Gets the value of the isikMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMaakond() {
                return isikMaakond;
            }

            /**
             * Sets the value of the isikMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMaakond(String value) {
                this.isikMaakond = value;
            }

            /**
             * Gets the value of the isikVallakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVallakd() {
                return isikVallakd;
            }

            /**
             * Sets the value of the isikVallakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVallakd(String value) {
                this.isikVallakd = value;
            }

            /**
             * Gets the value of the isikVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVald() {
                return isikVald;
            }

            /**
             * Sets the value of the isikVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVald(String value) {
                this.isikVald = value;
            }

            /**
             * Gets the value of the isikKylakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKylakd() {
                return isikKylakd;
            }

            /**
             * Sets the value of the isikKylakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKylakd(String value) {
                this.isikKylakd = value;
            }

            /**
             * Gets the value of the isikKyla property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKyla() {
                return isikKyla;
            }

            /**
             * Sets the value of the isikKyla property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKyla(String value) {
                this.isikKyla = value;
            }

            /**
             * Gets the value of the isikVaikekohtkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVaikekohtkd() {
                return isikVaikekohtkd;
            }

            /**
             * Sets the value of the isikVaikekohtkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVaikekohtkd(String value) {
                this.isikVaikekohtkd = value;
            }

            /**
             * Gets the value of the isikVaikekoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVaikekoht() {
                return isikVaikekoht;
            }

            /**
             * Sets the value of the isikVaikekoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVaikekoht(String value) {
                this.isikVaikekoht = value;
            }

            /**
             * Gets the value of the isikTanavakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTanavakd() {
                return isikTanavakd;
            }

            /**
             * Sets the value of the isikTanavakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTanavakd(String value) {
                this.isikTanavakd = value;
            }

            /**
             * Gets the value of the isikTanavanm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTanavanm() {
                return isikTanavanm;
            }

            /**
             * Sets the value of the isikTanavanm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTanavanm(String value) {
                this.isikTanavanm = value;
            }

            /**
             * Gets the value of the isikNimikd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikNimikd() {
                return isikNimikd;
            }

            /**
             * Sets the value of the isikNimikd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikNimikd(String value) {
                this.isikNimikd = value;
            }

            /**
             * Gets the value of the isikNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikNimi() {
                return isikNimi;
            }

            /**
             * Sets the value of the isikNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikNimi(String value) {
                this.isikNimi = value;
            }

            /**
             * Gets the value of the isikMajanr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMajanr() {
                return isikMajanr;
            }

            /**
             * Sets the value of the isikMajanr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMajanr(String value) {
                this.isikMajanr = value;
            }

            /**
             * Gets the value of the isikKorternr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKorternr() {
                return isikKorternr;
            }

            /**
             * Sets the value of the isikKorternr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKorternr(String value) {
                this.isikKorternr = value;
            }

            /**
             * Gets the value of the isikADSADRID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikADSADRID() {
                return isikADSADRID;
            }

            /**
             * Sets the value of the isikADSADRID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikADSADRID(String value) {
                this.isikADSADRID = value;
            }

            /**
             * Gets the value of the isikADSOID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikADSOID() {
                return isikADSOID;
            }

            /**
             * Sets the value of the isikADSOID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikADSOID(String value) {
                this.isikADSOID = value;
            }

            /**
             * Gets the value of the isikADSKoodaadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikADSKoodaadress() {
                return isikADSKoodaadress;
            }

            /**
             * Sets the value of the isikADSKoodaadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikADSKoodaadress(String value) {
                this.isikADSKoodaadress = value;
            }

            /**
             * Gets the value of the isikADSADOBOID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikADSADOBOID() {
                return isikADSADOBOID;
            }

            /**
             * Sets the value of the isikADSADOBOID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikADSADOBOID(String value) {
                this.isikADSADOBOID = value;
            }

        }

    }

}
