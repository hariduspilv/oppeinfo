
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRAddressResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRAddressResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PersonStatus"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Person" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="PersonalCode" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                             &lt;element name="exitStatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="exitStatusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="exitStatusText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "RRAddressResponseType", propOrder = {
    "personStatus"
})
public class RRAddressResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "PersonStatus", required = true)
    protected RRAddressResponseType.PersonStatus personStatus;

    /**
     * Gets the value of the personStatus property.
     * 
     * @return
     *     possible object is
     *     {@link RRAddressResponseType.PersonStatus }
     *     
     */
    public RRAddressResponseType.PersonStatus getPersonStatus() {
        return personStatus;
    }

    /**
     * Sets the value of the personStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRAddressResponseType.PersonStatus }
     *     
     */
    public void setPersonStatus(RRAddressResponseType.PersonStatus value) {
        this.personStatus = value;
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
     *         &lt;element name="Person" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="PersonalCode" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *                   &lt;element name="exitStatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="exitStatusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="exitStatusText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "person"
    })
    public static class PersonStatus {

        @XmlElement(name = "Person")
        protected List<RRAddressResponseType.PersonStatus.Person> person;

        /**
         * Gets the value of the person property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the person property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPerson().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RRAddressResponseType.PersonStatus.Person }
         * 
         * 
         */
        public List<RRAddressResponseType.PersonStatus.Person> getPerson() {
            if (person == null) {
                person = new ArrayList<RRAddressResponseType.PersonStatus.Person>();
            }
            return this.person;
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
         *         &lt;element name="PersonalCode" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
         *         &lt;element name="exitStatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="exitStatusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="exitStatusText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "personalCode",
            "exitStatus",
            "exitStatusCode",
            "exitStatusText"
        })
        public static class Person {

            @XmlElement(name = "PersonalCode", required = true)
            protected String personalCode;
            @XmlElement(required = true)
            protected String exitStatus;
            protected String exitStatusCode;
            protected String exitStatusText;

            /**
             * Gets the value of the personalCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPersonalCode() {
                return personalCode;
            }

            /**
             * Sets the value of the personalCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPersonalCode(String value) {
                this.personalCode = value;
            }

            /**
             * Gets the value of the exitStatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExitStatus() {
                return exitStatus;
            }

            /**
             * Sets the value of the exitStatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExitStatus(String value) {
                this.exitStatus = value;
            }

            /**
             * Gets the value of the exitStatusCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExitStatusCode() {
                return exitStatusCode;
            }

            /**
             * Sets the value of the exitStatusCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExitStatusCode(String value) {
                this.exitStatusCode = value;
            }

            /**
             * Gets the value of the exitStatusText property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExitStatusText() {
                return exitStatusText;
            }

            /**
             * Sets the value of the exitStatusText property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExitStatusText(String value) {
                this.exitStatusText = value;
            }

        }

    }

}
