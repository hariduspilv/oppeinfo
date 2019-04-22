
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR71_fail_downloadResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR71_fail_downloadResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ttData"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ttData.id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="ttData.cData" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR71_fail_downloadResponseType", propOrder = {
    "ttData"
})
public class RR71FailDownloadResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected RR71FailDownloadResponseType.TtData ttData;

    /**
     * Gets the value of the ttData property.
     * 
     * @return
     *     possible object is
     *     {@link RR71FailDownloadResponseType.TtData }
     *     
     */
    public RR71FailDownloadResponseType.TtData getTtData() {
        return ttData;
    }

    /**
     * Sets the value of the ttData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR71FailDownloadResponseType.TtData }
     *     
     */
    public void setTtData(RR71FailDownloadResponseType.TtData value) {
        this.ttData = value;
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
     *         &lt;element name="item" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ttData.id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="ttData.cData" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    public static class TtData {

        protected List<RR71FailDownloadResponseType.TtData.Item> item;

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
         * {@link RR71FailDownloadResponseType.TtData.Item }
         * 
         * 
         */
        public List<RR71FailDownloadResponseType.TtData.Item> getItem() {
            if (item == null) {
                item = new ArrayList<RR71FailDownloadResponseType.TtData.Item>();
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
         *         &lt;element name="ttData.id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="ttData.cData" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "ttDataId",
            "ttDataCData"
        })
        public static class Item {

            @XmlElement(name = "ttData.id", required = true)
            protected BigInteger ttDataId;
            @XmlElement(name = "ttData.cData", required = true)
            protected String ttDataCData;

            /**
             * Gets the value of the ttDataId property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getTtDataId() {
                return ttDataId;
            }

            /**
             * Sets the value of the ttDataId property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setTtDataId(BigInteger value) {
                this.ttDataId = value;
            }

            /**
             * Gets the value of the ttDataCData property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtDataCData() {
                return ttDataCData;
            }

            /**
             * Sets the value of the ttDataCData property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtDataCData(String value) {
                this.ttDataCData = value;
            }

        }

    }

}
