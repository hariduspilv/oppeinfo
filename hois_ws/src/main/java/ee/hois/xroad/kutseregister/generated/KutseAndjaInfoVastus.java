
package ee.hois.xroad.kutseregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Kutse andja info
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="kutseandja" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;all&gt;
 *                   &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="tunnistusetyybid"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="tunnistusetyyp" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}tunnistuseTyypType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="lisavali" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                   &lt;element name="voibinfotpeita" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                 &lt;/all&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="kutsestandardid" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="kutsestandard" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}kutsestandardType" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="kutsekomisjonid" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="kutsekomisjon" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}kutsekomisjonType" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="kutseeksamikeeled" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="keel" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="vigadearv" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="vead" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}veadType" minOccurs="0"/&gt;
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
@XmlRootElement(name = "kutseAndjaInfoVastus")
public class KutseAndjaInfoVastus {

    protected KutseAndjaInfoVastus.Kutseandja kutseandja;
    protected KutseAndjaInfoVastus.Kutsestandardid kutsestandardid;
    protected KutseAndjaInfoVastus.Kutsekomisjonid kutsekomisjonid;
    protected KutseAndjaInfoVastus.Kutseeksamikeeled kutseeksamikeeled;
    @XmlElement(required = true)
    protected BigInteger vigadearv;
    protected VeadType vead;

    /**
     * Gets the value of the kutseandja property.
     * 
     * @return
     *     possible object is
     *     {@link KutseAndjaInfoVastus.Kutseandja }
     *     
     */
    public KutseAndjaInfoVastus.Kutseandja getKutseandja() {
        return kutseandja;
    }

    /**
     * Sets the value of the kutseandja property.
     * 
     * @param value
     *     allowed object is
     *     {@link KutseAndjaInfoVastus.Kutseandja }
     *     
     */
    public void setKutseandja(KutseAndjaInfoVastus.Kutseandja value) {
        this.kutseandja = value;
    }

    /**
     * Gets the value of the kutsestandardid property.
     * 
     * @return
     *     possible object is
     *     {@link KutseAndjaInfoVastus.Kutsestandardid }
     *     
     */
    public KutseAndjaInfoVastus.Kutsestandardid getKutsestandardid() {
        return kutsestandardid;
    }

    /**
     * Sets the value of the kutsestandardid property.
     * 
     * @param value
     *     allowed object is
     *     {@link KutseAndjaInfoVastus.Kutsestandardid }
     *     
     */
    public void setKutsestandardid(KutseAndjaInfoVastus.Kutsestandardid value) {
        this.kutsestandardid = value;
    }

    /**
     * Gets the value of the kutsekomisjonid property.
     * 
     * @return
     *     possible object is
     *     {@link KutseAndjaInfoVastus.Kutsekomisjonid }
     *     
     */
    public KutseAndjaInfoVastus.Kutsekomisjonid getKutsekomisjonid() {
        return kutsekomisjonid;
    }

    /**
     * Sets the value of the kutsekomisjonid property.
     * 
     * @param value
     *     allowed object is
     *     {@link KutseAndjaInfoVastus.Kutsekomisjonid }
     *     
     */
    public void setKutsekomisjonid(KutseAndjaInfoVastus.Kutsekomisjonid value) {
        this.kutsekomisjonid = value;
    }

    /**
     * Gets the value of the kutseeksamikeeled property.
     * 
     * @return
     *     possible object is
     *     {@link KutseAndjaInfoVastus.Kutseeksamikeeled }
     *     
     */
    public KutseAndjaInfoVastus.Kutseeksamikeeled getKutseeksamikeeled() {
        return kutseeksamikeeled;
    }

    /**
     * Sets the value of the kutseeksamikeeled property.
     * 
     * @param value
     *     allowed object is
     *     {@link KutseAndjaInfoVastus.Kutseeksamikeeled }
     *     
     */
    public void setKutseeksamikeeled(KutseAndjaInfoVastus.Kutseeksamikeeled value) {
        this.kutseeksamikeeled = value;
    }

    /**
     * Gets the value of the vigadearv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVigadearv() {
        return vigadearv;
    }

    /**
     * Sets the value of the vigadearv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVigadearv(BigInteger value) {
        this.vigadearv = value;
    }

    /**
     * Gets the value of the vead property.
     * 
     * @return
     *     possible object is
     *     {@link VeadType }
     *     
     */
    public VeadType getVead() {
        return vead;
    }

    /**
     * Sets the value of the vead property.
     * 
     * @param value
     *     allowed object is
     *     {@link VeadType }
     *     
     */
    public void setVead(VeadType value) {
        this.vead = value;
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
     *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="tunnistusetyybid"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="tunnistusetyyp" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}tunnistuseTyypType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="lisavali" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *         &lt;element name="voibinfotpeita" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
    public static class Kutseandja {

        @XmlElement(required = true)
        protected String nimetus;
        @XmlElement(required = true)
        protected KutseAndjaInfoVastus.Kutseandja.Tunnistusetyybid tunnistusetyybid;
        protected boolean lisavali;
        protected boolean voibinfotpeita;

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
         * Gets the value of the tunnistusetyybid property.
         * 
         * @return
         *     possible object is
         *     {@link KutseAndjaInfoVastus.Kutseandja.Tunnistusetyybid }
         *     
         */
        public KutseAndjaInfoVastus.Kutseandja.Tunnistusetyybid getTunnistusetyybid() {
            return tunnistusetyybid;
        }

        /**
         * Sets the value of the tunnistusetyybid property.
         * 
         * @param value
         *     allowed object is
         *     {@link KutseAndjaInfoVastus.Kutseandja.Tunnistusetyybid }
         *     
         */
        public void setTunnistusetyybid(KutseAndjaInfoVastus.Kutseandja.Tunnistusetyybid value) {
            this.tunnistusetyybid = value;
        }

        /**
         * Gets the value of the lisavali property.
         * 
         */
        public boolean isLisavali() {
            return lisavali;
        }

        /**
         * Sets the value of the lisavali property.
         * 
         */
        public void setLisavali(boolean value) {
            this.lisavali = value;
        }

        /**
         * Gets the value of the voibinfotpeita property.
         * 
         */
        public boolean isVoibinfotpeita() {
            return voibinfotpeita;
        }

        /**
         * Sets the value of the voibinfotpeita property.
         * 
         */
        public void setVoibinfotpeita(boolean value) {
            this.voibinfotpeita = value;
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
         *         &lt;element name="tunnistusetyyp" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}tunnistuseTyypType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
     *         &lt;element name="keel" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}idNimetusType" maxOccurs="unbounded"/&gt;
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
        "keel"
    })
    public static class Kutseeksamikeeled {

        @XmlElement(required = true)
        protected List<IdNimetusType> keel;

        /**
         * Gets the value of the keel property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the keel property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKeel().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdNimetusType }
         * 
         * 
         */
        public List<IdNimetusType> getKeel() {
            if (keel == null) {
                keel = new ArrayList<IdNimetusType>();
            }
            return this.keel;
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
     *         &lt;element name="kutsekomisjon" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}kutsekomisjonType" maxOccurs="unbounded"/&gt;
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
        "kutsekomisjon"
    })
    public static class Kutsekomisjonid {

        @XmlElement(required = true)
        protected List<KutsekomisjonType> kutsekomisjon;

        /**
         * Gets the value of the kutsekomisjon property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kutsekomisjon property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKutsekomisjon().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link KutsekomisjonType }
         * 
         * 
         */
        public List<KutsekomisjonType> getKutsekomisjon() {
            if (kutsekomisjon == null) {
                kutsekomisjon = new ArrayList<KutsekomisjonType>();
            }
            return this.kutsekomisjon;
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
     *         &lt;element name="kutsestandard" type="{http://producers.kutseregister.xtee.riik.ee/producer/kutseregister}kutsestandardType" maxOccurs="unbounded"/&gt;
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
        "kutsestandard"
    })
    public static class Kutsestandardid {

        @XmlElement(required = true)
        protected List<KutsestandardType> kutsestandard;

        /**
         * Gets the value of the kutsestandard property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kutsestandard property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKutsestandard().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link KutsestandardType }
         * 
         * 
         */
        public List<KutsestandardType> getKutsestandard() {
            if (kutsestandard == null) {
                kutsestandard = new ArrayList<KutsestandardType>();
            }
            return this.kutsestandard;
        }

    }

}
