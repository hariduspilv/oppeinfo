
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR423KMOSurmadResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR423KMOSurmadResponseType"&gt;
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
 *                             &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
 *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="EndisedPerenimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="EndisedEesnimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="SurmaKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="SurmaRegistreerimiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="Dokumendid" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Dokument" maxOccurs="unbounded"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR423KMOSurmadResponseType", propOrder = {
    "isikud"
})
public class RR423KMOSurmadResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikud")
    protected RR423KMOSurmadResponseType.Isikud isikud;

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR423KMOSurmadResponseType.Isikud }
     *     
     */
    public RR423KMOSurmadResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR423KMOSurmadResponseType.Isikud }
     *     
     */
    public void setIsikud(RR423KMOSurmadResponseType.Isikud value) {
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
     *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
     *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="EndisedPerenimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="EndisedEesnimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="SurmaKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="SurmaRegistreerimiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="Dokumendid" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Dokument" maxOccurs="unbounded"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
        "isik"
    })
    public static class Isikud {

        @XmlElement(name = "Isik")
        protected List<RR423KMOSurmadResponseType.Isikud.Isik> isik;

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
         * {@link RR423KMOSurmadResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR423KMOSurmadResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR423KMOSurmadResponseType.Isikud.Isik>();
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
         *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
         *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="EndisedPerenimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="EndisedEesnimed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="SurmaKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="SurmaRegistreerimiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="Dokumendid" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Dokument" maxOccurs="unbounded"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
            "synniaeg",
            "perenimi",
            "endisedPerenimed",
            "eesnimi",
            "endisedEesnimed",
            "surmaKP",
            "surmaRegistreerimiseKP",
            "dokumendid"
        })
        public static class Isik {

            @XmlElement(name = "Isikukood")
            protected String isikukood;
            @XmlElement(name = "Synniaeg")
            protected String synniaeg;
            @XmlElement(name = "Perenimi", required = true)
            protected String perenimi;
            @XmlElement(name = "EndisedPerenimed")
            protected String endisedPerenimed;
            @XmlElement(name = "Eesnimi", required = true)
            protected String eesnimi;
            @XmlElement(name = "EndisedEesnimed")
            protected String endisedEesnimed;
            @XmlElement(name = "SurmaKP")
            protected String surmaKP;
            @XmlElement(name = "SurmaRegistreerimiseKP")
            protected String surmaRegistreerimiseKP;
            @XmlElement(name = "Dokumendid")
            protected RR423KMOSurmadResponseType.Isikud.Isik.Dokumendid dokumendid;

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
             * Gets the value of the endisedPerenimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEndisedPerenimed() {
                return endisedPerenimed;
            }

            /**
             * Sets the value of the endisedPerenimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEndisedPerenimed(String value) {
                this.endisedPerenimed = value;
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
             * Gets the value of the endisedEesnimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEndisedEesnimed() {
                return endisedEesnimed;
            }

            /**
             * Sets the value of the endisedEesnimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEndisedEesnimed(String value) {
                this.endisedEesnimed = value;
            }

            /**
             * Gets the value of the surmaKP property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurmaKP() {
                return surmaKP;
            }

            /**
             * Sets the value of the surmaKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurmaKP(String value) {
                this.surmaKP = value;
            }

            /**
             * Gets the value of the surmaRegistreerimiseKP property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurmaRegistreerimiseKP() {
                return surmaRegistreerimiseKP;
            }

            /**
             * Sets the value of the surmaRegistreerimiseKP property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurmaRegistreerimiseKP(String value) {
                this.surmaRegistreerimiseKP = value;
            }

            /**
             * Gets the value of the dokumendid property.
             * 
             * @return
             *     possible object is
             *     {@link RR423KMOSurmadResponseType.Isikud.Isik.Dokumendid }
             *     
             */
            public RR423KMOSurmadResponseType.Isikud.Isik.Dokumendid getDokumendid() {
                return dokumendid;
            }

            /**
             * Sets the value of the dokumendid property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR423KMOSurmadResponseType.Isikud.Isik.Dokumendid }
             *     
             */
            public void setDokumendid(RR423KMOSurmadResponseType.Isikud.Isik.Dokumendid value) {
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
             *         &lt;element name="Dokument" maxOccurs="unbounded"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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

                @XmlElement(name = "Dokument", required = true)
                protected List<RR423KMOSurmadResponseType.Isikud.Isik.Dokumendid.Dokument> dokument;

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
                 * {@link RR423KMOSurmadResponseType.Isikud.Isik.Dokumendid.Dokument }
                 * 
                 * 
                 */
                public List<RR423KMOSurmadResponseType.Isikud.Isik.Dokumendid.Dokument> getDokument() {
                    if (dokument == null) {
                        dokument = new ArrayList<RR423KMOSurmadResponseType.Isikud.Isik.Dokumendid.Dokument>();
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
                 *         &lt;element name="Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="ValjaandmiseKP" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
                    "nimetus",
                    "number",
                    "valjaandmiseKP"
                })
                public static class Dokument {

                    @XmlElement(name = "Liik", required = true)
                    protected String liik;
                    @XmlElement(name = "Nimetus")
                    protected String nimetus;
                    @XmlElement(name = "Number", required = true)
                    protected String number;
                    @XmlElement(name = "ValjaandmiseKP")
                    protected String valjaandmiseKP;

                    /**
                     * Gets the value of the liik property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getLiik() {
                        return liik;
                    }

                    /**
                     * Sets the value of the liik property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setLiik(String value) {
                        this.liik = value;
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

                }

            }

        }

    }

}
