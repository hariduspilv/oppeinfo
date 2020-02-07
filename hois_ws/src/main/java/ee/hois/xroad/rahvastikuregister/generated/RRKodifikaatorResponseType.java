
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKodifikaatorResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKodifikaatorResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ttKodifikaator"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="cKodifikaatoriKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cLisaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cLyhikeNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cPikkNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRKodifikaatorResponseType", propOrder = {
    "ttKodifikaator"
})
public class RRKodifikaatorResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected RRKodifikaatorResponseType.TtKodifikaator ttKodifikaator;

    /**
     * Gets the value of the ttKodifikaator property.
     * 
     * @return
     *     possible object is
     *     {@link RRKodifikaatorResponseType.TtKodifikaator }
     *     
     */
    public RRKodifikaatorResponseType.TtKodifikaator getTtKodifikaator() {
        return ttKodifikaator;
    }

    /**
     * Sets the value of the ttKodifikaator property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRKodifikaatorResponseType.TtKodifikaator }
     *     
     */
    public void setTtKodifikaator(RRKodifikaatorResponseType.TtKodifikaator value) {
        this.ttKodifikaator = value;
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
     *         &lt;element name="item" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="cKodifikaatoriKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cLisaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cLyhikeNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cPikkNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "item"
    })
    public static class TtKodifikaator {

        @XmlElement(required = true)
        protected List<RRKodifikaatorResponseType.TtKodifikaator.Item> item;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RRKodifikaatorResponseType.TtKodifikaator.Item }
         * 
         * 
         */
        public List<RRKodifikaatorResponseType.TtKodifikaator.Item> getItem() {
            if (item == null) {
                item = new ArrayList<RRKodifikaatorResponseType.TtKodifikaator.Item>();
            }
            return this.item;
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
         *         &lt;element name="cKodifikaatoriKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cElemendiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cLisaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cLyhikeNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cPikkNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "cKodifikaatoriKood",
            "cElemendiKood",
            "cLisaKood",
            "cLyhikeNimetus",
            "cPikkNimetus"
        })
        public static class Item {

            @XmlElement(required = true)
            protected String cKodifikaatoriKood;
            @XmlElement(required = true)
            protected String cElemendiKood;
            @XmlElement(required = true)
            protected String cLisaKood;
            @XmlElement(required = true)
            protected String cLyhikeNimetus;
            @XmlElement(required = true)
            protected String cPikkNimetus;

            /**
             * Gets the value of the cKodifikaatoriKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCKodifikaatoriKood() {
                return cKodifikaatoriKood;
            }

            /**
             * Sets the value of the cKodifikaatoriKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCKodifikaatoriKood(String value) {
                this.cKodifikaatoriKood = value;
            }

            /**
             * Gets the value of the cElemendiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCElemendiKood() {
                return cElemendiKood;
            }

            /**
             * Sets the value of the cElemendiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCElemendiKood(String value) {
                this.cElemendiKood = value;
            }

            /**
             * Gets the value of the cLisaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCLisaKood() {
                return cLisaKood;
            }

            /**
             * Sets the value of the cLisaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCLisaKood(String value) {
                this.cLisaKood = value;
            }

            /**
             * Gets the value of the cLyhikeNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCLyhikeNimetus() {
                return cLyhikeNimetus;
            }

            /**
             * Sets the value of the cLyhikeNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCLyhikeNimetus(String value) {
                this.cLyhikeNimetus = value;
            }

            /**
             * Gets the value of the cPikkNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCPikkNimetus() {
                return cPikkNimetus;
            }

            /**
             * Sets the value of the cPikkNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCPikkNimetus(String value) {
                this.cPikkNimetus = value;
            }

        }

    }

}
