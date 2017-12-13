
package ee.hois.xroad.kutseregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for kutsestandardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kutsestandardType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tyybid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="tyyp" maxOccurs="unbounded"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;enumeration value="1"/&gt;
 *                         &lt;enumeration value="2"/&gt;
 *                         &lt;enumeration value="3"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="spetsialiseerumised" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="spetsialiseerumine" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="osakutsed" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="osakutse" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="kompetentsid" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;all&gt;
 *                   &lt;element name="tunnistusetyybid"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="tunnistusetyyp" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}tunnistuseTyypType" maxOccurs="unbounded"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="kompetentsinimekiri"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="kompetents" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/all&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="pohikutse" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kutsestandardType", propOrder = {
    "id",
    "nimetus",
    "tyybid",
    "spetsialiseerumised",
    "osakutsed",
    "kompetentsid",
    "pohikutse"
})
public class KutsestandardType {

    @XmlElement(required = true)
    protected BigInteger id;
    @XmlElement(required = true)
    protected String nimetus;
    @XmlElement(required = true)
    protected KutsestandardType.Tyybid tyybid;
    protected KutsestandardType.Spetsialiseerumised spetsialiseerumised;
    protected KutsestandardType.Osakutsed osakutsed;
    protected List<KutsestandardType.Kompetentsid> kompetentsid;
    protected boolean pohikutse;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
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
     * Gets the value of the tyybid property.
     * 
     * @return
     *     possible object is
     *     {@link KutsestandardType.Tyybid }
     *     
     */
    public KutsestandardType.Tyybid getTyybid() {
        return tyybid;
    }

    /**
     * Sets the value of the tyybid property.
     * 
     * @param value
     *     allowed object is
     *     {@link KutsestandardType.Tyybid }
     *     
     */
    public void setTyybid(KutsestandardType.Tyybid value) {
        this.tyybid = value;
    }

    /**
     * Gets the value of the spetsialiseerumised property.
     * 
     * @return
     *     possible object is
     *     {@link KutsestandardType.Spetsialiseerumised }
     *     
     */
    public KutsestandardType.Spetsialiseerumised getSpetsialiseerumised() {
        return spetsialiseerumised;
    }

    /**
     * Sets the value of the spetsialiseerumised property.
     * 
     * @param value
     *     allowed object is
     *     {@link KutsestandardType.Spetsialiseerumised }
     *     
     */
    public void setSpetsialiseerumised(KutsestandardType.Spetsialiseerumised value) {
        this.spetsialiseerumised = value;
    }

    /**
     * Gets the value of the osakutsed property.
     * 
     * @return
     *     possible object is
     *     {@link KutsestandardType.Osakutsed }
     *     
     */
    public KutsestandardType.Osakutsed getOsakutsed() {
        return osakutsed;
    }

    /**
     * Sets the value of the osakutsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link KutsestandardType.Osakutsed }
     *     
     */
    public void setOsakutsed(KutsestandardType.Osakutsed value) {
        this.osakutsed = value;
    }

    /**
     * Gets the value of the kompetentsid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kompetentsid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKompetentsid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KutsestandardType.Kompetentsid }
     * 
     * 
     */
    public List<KutsestandardType.Kompetentsid> getKompetentsid() {
        if (kompetentsid == null) {
            kompetentsid = new ArrayList<KutsestandardType.Kompetentsid>();
        }
        return this.kompetentsid;
    }

    /**
     * Gets the value of the pohikutse property.
     * 
     */
    public boolean isPohikutse() {
        return pohikutse;
    }

    /**
     * Sets the value of the pohikutse property.
     * 
     */
    public void setPohikutse(boolean value) {
        this.pohikutse = value;
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
     *       &lt;all&gt;
     *         &lt;element name="tunnistusetyybid"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="tunnistusetyyp" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}tunnistuseTyypType" maxOccurs="unbounded"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="kompetentsinimekiri"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="kompetents" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/all&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class Kompetentsid {

        @XmlElement(required = true)
        protected KutsestandardType.Kompetentsid.Tunnistusetyybid tunnistusetyybid;
        @XmlElement(required = true)
        protected KutsestandardType.Kompetentsid.Kompetentsinimekiri kompetentsinimekiri;

        /**
         * Gets the value of the tunnistusetyybid property.
         * 
         * @return
         *     possible object is
         *     {@link KutsestandardType.Kompetentsid.Tunnistusetyybid }
         *     
         */
        public KutsestandardType.Kompetentsid.Tunnistusetyybid getTunnistusetyybid() {
            return tunnistusetyybid;
        }

        /**
         * Sets the value of the tunnistusetyybid property.
         * 
         * @param value
         *     allowed object is
         *     {@link KutsestandardType.Kompetentsid.Tunnistusetyybid }
         *     
         */
        public void setTunnistusetyybid(KutsestandardType.Kompetentsid.Tunnistusetyybid value) {
            this.tunnistusetyybid = value;
        }

        /**
         * Gets the value of the kompetentsinimekiri property.
         * 
         * @return
         *     possible object is
         *     {@link KutsestandardType.Kompetentsid.Kompetentsinimekiri }
         *     
         */
        public KutsestandardType.Kompetentsid.Kompetentsinimekiri getKompetentsinimekiri() {
            return kompetentsinimekiri;
        }

        /**
         * Sets the value of the kompetentsinimekiri property.
         * 
         * @param value
         *     allowed object is
         *     {@link KutsestandardType.Kompetentsid.Kompetentsinimekiri }
         *     
         */
        public void setKompetentsinimekiri(KutsestandardType.Kompetentsid.Kompetentsinimekiri value) {
            this.kompetentsinimekiri = value;
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
         *         &lt;element name="kompetents" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
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
            "kompetents"
        })
        public static class Kompetentsinimekiri {

            @XmlElement(required = true)
            protected List<IdNimetusType> kompetents;

            /**
             * Gets the value of the kompetents property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the kompetents property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getKompetents().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link IdNimetusType }
             * 
             * 
             */
            public List<IdNimetusType> getKompetents() {
                if (kompetents == null) {
                    kompetents = new ArrayList<IdNimetusType>();
                }
                return this.kompetents;
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
         *         &lt;element name="tunnistusetyyp" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}tunnistuseTyypType" maxOccurs="unbounded"/&gt;
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
            "tunnistusetyyp"
        })
        public static class Tunnistusetyybid {

            @XmlElement(required = true)
            protected List<BigInteger> tunnistusetyyp;

            /**
             * Gets the value of the tunnistusetyyp property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the tunnistusetyyp property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTunnistusetyyp().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BigInteger }
             * 
             * 
             */
            public List<BigInteger> getTunnistusetyyp() {
                if (tunnistusetyyp == null) {
                    tunnistusetyyp = new ArrayList<BigInteger>();
                }
                return this.tunnistusetyyp;
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
     *         &lt;element name="osakutse" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "osakutse"
    })
    public static class Osakutsed {

        protected List<IdNimetusType> osakutse;

        /**
         * Gets the value of the osakutse property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the osakutse property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOsakutse().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdNimetusType }
         * 
         * 
         */
        public List<IdNimetusType> getOsakutse() {
            if (osakutse == null) {
                osakutse = new ArrayList<IdNimetusType>();
            }
            return this.osakutse;
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
     *         &lt;element name="spetsialiseerumine" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
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
        "spetsialiseerumine"
    })
    public static class Spetsialiseerumised {

        @XmlElement(required = true)
        protected List<IdNimetusType> spetsialiseerumine;

        /**
         * Gets the value of the spetsialiseerumine property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the spetsialiseerumine property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSpetsialiseerumine().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdNimetusType }
         * 
         * 
         */
        public List<IdNimetusType> getSpetsialiseerumine() {
            if (spetsialiseerumine == null) {
                spetsialiseerumine = new ArrayList<IdNimetusType>();
            }
            return this.spetsialiseerumine;
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
     *         &lt;element name="tyyp" maxOccurs="unbounded"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;enumeration value="1"/&gt;
     *               &lt;enumeration value="2"/&gt;
     *               &lt;enumeration value="3"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
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
        "tyyp"
    })
    public static class Tyybid {

        @XmlElement(required = true)
        protected List<BigInteger> tyyp;

        /**
         * Gets the value of the tyyp property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the tyyp property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTyyp().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BigInteger }
         * 
         * 
         */
        public List<BigInteger> getTyyp() {
            if (tyyp == null) {
                tyyp = new ArrayList<BigInteger>();
            }
            return this.tyyp;
        }

    }

}
