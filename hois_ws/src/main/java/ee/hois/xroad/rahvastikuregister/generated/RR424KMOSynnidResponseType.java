
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR424KMOSynnidResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR424KMOSynnidResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Dokumendid" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Dokument" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Laps"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
 *                                       &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                       &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Elukoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Ema" type="{http://rr.x-road.eu/producer}ParentShort" minOccurs="0"/&gt;
 *                             &lt;element name="Isa" type="{http://rr.x-road.eu/producer}ParentShort" minOccurs="0"/&gt;
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
@XmlType(name = "RR424KMOSynnidResponseType", propOrder = {
    "dokumendid"
})
public class RR424KMOSynnidResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Dokumendid")
    protected RR424KMOSynnidResponseType.Dokumendid dokumendid;

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RR424KMOSynnidResponseType.Dokumendid }
     *     
     */
    public RR424KMOSynnidResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR424KMOSynnidResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR424KMOSynnidResponseType.Dokumendid value) {
        this.dokumendid = value;
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
     *         &lt;element name="Dokument" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Laps"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
     *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Elukoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Ema" type="{http://rr.x-road.eu/producer}ParentShort" minOccurs="0"/&gt;
     *                   &lt;element name="Isa" type="{http://rr.x-road.eu/producer}ParentShort" minOccurs="0"/&gt;
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
    public static class Dokumendid {

        @XmlElement(name = "Dokument")
        protected List<RR424KMOSynnidResponseType.Dokumendid.Dokument> dokument;

        /**
         * Gets the value of the dokument property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dokument property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDokument().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR424KMOSynnidResponseType.Dokumendid.Dokument }
         * 
         * 
         */
        public List<RR424KMOSynnidResponseType.Dokumendid.Dokument> getDokument() {
            if (dokument == null) {
                dokument = new ArrayList<RR424KMOSynnidResponseType.Dokumendid.Dokument>();
            }
            return this.dokument;
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
         *         &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Laps"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
         *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Elukoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Ema" type="{http://rr.x-road.eu/producer}ParentShort" minOccurs="0"/&gt;
         *         &lt;element name="Isa" type="{http://rr.x-road.eu/producer}ParentShort" minOccurs="0"/&gt;
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
            "nimetus",
            "laps",
            "ema",
            "isa"
        })
        public static class Dokument {

            @XmlElement(name = "Kood", required = true)
            protected String kood;
            @XmlElement(name = "Nimetus", required = true)
            protected String nimetus;
            @XmlElement(name = "Laps", required = true)
            protected RR424KMOSynnidResponseType.Dokumendid.Dokument.Laps laps;
            @XmlElement(name = "Ema")
            protected ParentShort ema;
            @XmlElement(name = "Isa")
            protected ParentShort isa;

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
             * Gets the value of the nimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNimetus() {
                return nimetus;
            }

            /**
             * Sets the value of the nimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNimetus(String value) {
                this.nimetus = value;
            }

            /**
             * Gets the value of the laps property.
             * 
             * @return
             *     possible object is
             *     {@link RR424KMOSynnidResponseType.Dokumendid.Dokument.Laps }
             *     
             */
            public RR424KMOSynnidResponseType.Dokumendid.Dokument.Laps getLaps() {
                return laps;
            }

            /**
             * Sets the value of the laps property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR424KMOSynnidResponseType.Dokumendid.Dokument.Laps }
             *     
             */
            public void setLaps(RR424KMOSynnidResponseType.Dokumendid.Dokument.Laps value) {
                this.laps = value;
            }

            /**
             * Gets the value of the ema property.
             * 
             * @return
             *     possible object is
             *     {@link ParentShort }
             *     
             */
            public ParentShort getEma() {
                return ema;
            }

            /**
             * Sets the value of the ema property.
             * 
             * @param value
             *     allowed object is
             *     {@link ParentShort }
             *     
             */
            public void setEma(ParentShort value) {
                this.ema = value;
            }

            /**
             * Gets the value of the isa property.
             * 
             * @return
             *     possible object is
             *     {@link ParentShort }
             *     
             */
            public ParentShort getIsa() {
                return isa;
            }

            /**
             * Sets the value of the isa property.
             * 
             * @param value
             *     allowed object is
             *     {@link ParentShort }
             *     
             */
            public void setIsa(ParentShort value) {
                this.isa = value;
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
             *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
             *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Elukoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "synniaeg",
                "perenimi",
                "eesnimi",
                "kodakondsus",
                "synnikoht",
                "elukoht"
            })
            public static class Laps {

                @XmlElement(name = "Isikukood")
                protected String isikukood;
                @XmlElement(name = "Synniaeg")
                protected String synniaeg;
                @XmlElement(name = "Perenimi", required = true)
                protected String perenimi;
                @XmlElement(name = "Eesnimi", required = true)
                protected String eesnimi;
                @XmlElement(name = "Kodakondsus")
                protected String kodakondsus;
                @XmlElement(name = "Synnikoht")
                protected String synnikoht;
                @XmlElement(name = "Elukoht")
                protected String elukoht;

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
                 * Gets the value of the synniaeg property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSynniaeg() {
                    return synniaeg;
                }

                /**
                 * Sets the value of the synniaeg property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSynniaeg(String value) {
                    this.synniaeg = value;
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
                 * Gets the value of the synnikoht property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSynnikoht() {
                    return synnikoht;
                }

                /**
                 * Sets the value of the synnikoht property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSynnikoht(String value) {
                    this.synnikoht = value;
                }

                /**
                 * Gets the value of the elukoht property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getElukoht() {
                    return elukoht;
                }

                /**
                 * Sets the value of the elukoht property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setElukoht(String value) {
                    this.elukoht = value;
                }

            }

        }

    }

}
