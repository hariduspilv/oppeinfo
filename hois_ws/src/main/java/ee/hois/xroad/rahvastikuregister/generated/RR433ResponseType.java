
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR433ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR433ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Viga" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikud"&gt;
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
 *                             &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.OnEestiKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ElamisloaLiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ElamisloaLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ElamislubaKehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.OnSurnud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR433ResponseType", propOrder = {
    "viga",
    "isikud"
})
public class RR433ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Viga")
    protected String viga;
    @XmlElement(name = "Isikud", required = true)
    protected RR433ResponseType.Isikud isikud;

    /**
     * Gets the value of the viga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViga() {
        return viga;
    }

    /**
     * Sets the value of the viga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViga(String value) {
        this.viga = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR433ResponseType.Isikud }
     *     
     */
    public RR433ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR433ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR433ResponseType.Isikud value) {
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
     *                   &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.OnEestiKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ElamisloaLiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ElamisloaLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ElamislubaKehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.OnSurnud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR433ResponseType.Isikud.Isik> isik;

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
         * {@link RR433ResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR433ResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR433ResponseType.Isikud.Isik>();
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
         *         &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.OnEestiKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ElamisloaLiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ElamisloaLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ElamislubaKehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.OnSurnud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikEesnimi",
            "isikPerenimi",
            "isikOnEestiKodakondsus",
            "isikElamisloaLiikKood",
            "isikElamisloaLiik",
            "isikElamislubaKehtivKuni",
            "isikOnSurnud"
        })
        public static class Isik {

            @XmlElement(name = "Isik.Isikukood", required = true)
            protected String isikIsikukood;
            @XmlElement(name = "Isik.Eesnimi", required = true)
            protected String isikEesnimi;
            @XmlElement(name = "Isik.Perenimi", required = true)
            protected String isikPerenimi;
            @XmlElement(name = "Isik.OnEestiKodakondsus", required = true)
            protected String isikOnEestiKodakondsus;
            @XmlElement(name = "Isik.ElamisloaLiikKood", required = true)
            protected String isikElamisloaLiikKood;
            @XmlElement(name = "Isik.ElamisloaLiik", required = true)
            protected String isikElamisloaLiik;
            @XmlElement(name = "Isik.ElamislubaKehtivKuni", required = true)
            protected String isikElamislubaKehtivKuni;
            @XmlElement(name = "Isik.OnSurnud", required = true)
            protected String isikOnSurnud;

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
             * Gets the value of the isikEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEesnimi() {
                return isikEesnimi;
            }

            /**
             * Sets the value of the isikEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEesnimi(String value) {
                this.isikEesnimi = value;
            }

            /**
             * Gets the value of the isikPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikPerenimi() {
                return isikPerenimi;
            }

            /**
             * Sets the value of the isikPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikPerenimi(String value) {
                this.isikPerenimi = value;
            }

            /**
             * Gets the value of the isikOnEestiKodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikOnEestiKodakondsus() {
                return isikOnEestiKodakondsus;
            }

            /**
             * Sets the value of the isikOnEestiKodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikOnEestiKodakondsus(String value) {
                this.isikOnEestiKodakondsus = value;
            }

            /**
             * Gets the value of the isikElamisloaLiikKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElamisloaLiikKood() {
                return isikElamisloaLiikKood;
            }

            /**
             * Sets the value of the isikElamisloaLiikKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElamisloaLiikKood(String value) {
                this.isikElamisloaLiikKood = value;
            }

            /**
             * Gets the value of the isikElamisloaLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElamisloaLiik() {
                return isikElamisloaLiik;
            }

            /**
             * Sets the value of the isikElamisloaLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElamisloaLiik(String value) {
                this.isikElamisloaLiik = value;
            }

            /**
             * Gets the value of the isikElamislubaKehtivKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElamislubaKehtivKuni() {
                return isikElamislubaKehtivKuni;
            }

            /**
             * Sets the value of the isikElamislubaKehtivKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElamislubaKehtivKuni(String value) {
                this.isikElamislubaKehtivKuni = value;
            }

            /**
             * Gets the value of the isikOnSurnud property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikOnSurnud() {
                return isikOnSurnud;
            }

            /**
             * Sets the value of the isikOnSurnud property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikOnSurnud(String value) {
                this.isikOnSurnud = value;
            }

        }

    }

}
