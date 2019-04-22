
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR67_muutusResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR67_muutusResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ttKood"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ttKoodid" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ttKoodid.cIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttKoodid.cUusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR67_muutusResponseType", propOrder = {
    "ttKood"
})
public class RR67MuutusResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected RR67MuutusResponseType.TtKood ttKood;

    /**
     * Gets the value of the ttKood property.
     * 
     * @return
     *     possible object is
     *     {@link RR67MuutusResponseType.TtKood }
     *     
     */
    public RR67MuutusResponseType.TtKood getTtKood() {
        return ttKood;
    }

    /**
     * Sets the value of the ttKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR67MuutusResponseType.TtKood }
     *     
     */
    public void setTtKood(RR67MuutusResponseType.TtKood value) {
        this.ttKood = value;
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
     *         &lt;element name="ttKoodid" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ttKoodid.cIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttKoodid.cUusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "ttKoodid"
    })
    public static class TtKood {

        protected List<RR67MuutusResponseType.TtKood.TtKoodid> ttKoodid;

        /**
         * Gets the value of the ttKoodid property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ttKoodid property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTtKoodid().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR67MuutusResponseType.TtKood.TtKoodid }
         * 
         * 
         */
        public List<RR67MuutusResponseType.TtKood.TtKoodid> getTtKoodid() {
            if (ttKoodid == null) {
                ttKoodid = new ArrayList<RR67MuutusResponseType.TtKood.TtKoodid>();
            }
            return this.ttKoodid;
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
         *         &lt;element name="ttKoodid.cIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttKoodid.cUusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "ttKoodidCIsikukood",
            "ttKoodidCUusKood"
        })
        public static class TtKoodid {

            @XmlElement(name = "ttKoodid.cIsikukood", required = true)
            protected String ttKoodidCIsikukood;
            @XmlElement(name = "ttKoodid.cUusKood", required = true)
            protected String ttKoodidCUusKood;

            /**
             * Gets the value of the ttKoodidCIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtKoodidCIsikukood() {
                return ttKoodidCIsikukood;
            }

            /**
             * Sets the value of the ttKoodidCIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtKoodidCIsikukood(String value) {
                this.ttKoodidCIsikukood = value;
            }

            /**
             * Gets the value of the ttKoodidCUusKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtKoodidCUusKood() {
                return ttKoodidCUusKood;
            }

            /**
             * Sets the value of the ttKoodidCUusKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtKoodidCUusKood(String value) {
                this.ttKoodidCUusKood = value;
            }

        }

    }

}
