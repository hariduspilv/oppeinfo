
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RRAddressRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRAddressRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Activity" type="{http://rr.x-road.eu/producer}AddressActivityType"/&gt;
 *         &lt;element name="AddressType" type="{http://rr.x-road.eu/producer}PersonAddressType"/&gt;
 *         &lt;element name="AddressStartDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="AddressEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="AddressEST" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ADS_OID" type="{http://rr.x-road.eu/producer}AdsOidCode"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AddressNoEST" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CountryCode" type="{http://rr.x-road.eu/producer}CountryCode"/&gt;
 *                   &lt;element name="ComponentLevel1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ComponentLevel2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ComponentLevel3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ComponentLevel4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ComponentLevel5" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ComponentLevel6" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ComponentLevel7" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ComponentLevel8" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="PostCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Persons"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Person" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="PersonalCode" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                             &lt;element name="GivenName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="FamilyName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RRAddressRequestType", propOrder = {
    "activity",
    "addressType",
    "addressStartDate",
    "addressEndDate",
    "addressEST",
    "addressNoEST",
    "persons"
})
public class RRAddressRequestType {

    @XmlElement(name = "Activity", required = true)
    @XmlSchemaType(name = "string")
    protected AddressActivityType activity;
    @XmlElement(name = "AddressType", required = true)
    @XmlSchemaType(name = "string")
    protected PersonAddressType addressType;
    @XmlElement(name = "AddressStartDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar addressStartDate;
    @XmlElement(name = "AddressEndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar addressEndDate;
    @XmlElement(name = "AddressEST")
    protected RRAddressRequestType.AddressEST addressEST;
    @XmlElement(name = "AddressNoEST")
    protected RRAddressRequestType.AddressNoEST addressNoEST;
    @XmlElement(name = "Persons", required = true)
    protected RRAddressRequestType.Persons persons;

    /**
     * Gets the value of the activity property.
     * 
     * @return
     *     possible object is
     *     {@link AddressActivityType }
     *     
     */
    public AddressActivityType getActivity() {
        return activity;
    }

    /**
     * Sets the value of the activity property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressActivityType }
     *     
     */
    public void setActivity(AddressActivityType value) {
        this.activity = value;
    }

    /**
     * Gets the value of the addressType property.
     * 
     * @return
     *     possible object is
     *     {@link PersonAddressType }
     *     
     */
    public PersonAddressType getAddressType() {
        return addressType;
    }

    /**
     * Sets the value of the addressType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonAddressType }
     *     
     */
    public void setAddressType(PersonAddressType value) {
        this.addressType = value;
    }

    /**
     * Gets the value of the addressStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAddressStartDate() {
        return addressStartDate;
    }

    /**
     * Sets the value of the addressStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAddressStartDate(XMLGregorianCalendar value) {
        this.addressStartDate = value;
    }

    /**
     * Gets the value of the addressEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAddressEndDate() {
        return addressEndDate;
    }

    /**
     * Sets the value of the addressEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAddressEndDate(XMLGregorianCalendar value) {
        this.addressEndDate = value;
    }

    /**
     * Gets the value of the addressEST property.
     * 
     * @return
     *     possible object is
     *     {@link RRAddressRequestType.AddressEST }
     *     
     */
    public RRAddressRequestType.AddressEST getAddressEST() {
        return addressEST;
    }

    /**
     * Sets the value of the addressEST property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRAddressRequestType.AddressEST }
     *     
     */
    public void setAddressEST(RRAddressRequestType.AddressEST value) {
        this.addressEST = value;
    }

    /**
     * Gets the value of the addressNoEST property.
     * 
     * @return
     *     possible object is
     *     {@link RRAddressRequestType.AddressNoEST }
     *     
     */
    public RRAddressRequestType.AddressNoEST getAddressNoEST() {
        return addressNoEST;
    }

    /**
     * Sets the value of the addressNoEST property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRAddressRequestType.AddressNoEST }
     *     
     */
    public void setAddressNoEST(RRAddressRequestType.AddressNoEST value) {
        this.addressNoEST = value;
    }

    /**
     * Gets the value of the persons property.
     * 
     * @return
     *     possible object is
     *     {@link RRAddressRequestType.Persons }
     *     
     */
    public RRAddressRequestType.Persons getPersons() {
        return persons;
    }

    /**
     * Sets the value of the persons property.
     * 
     * @param value
     *     allowed object is
     *     {@link RRAddressRequestType.Persons }
     *     
     */
    public void setPersons(RRAddressRequestType.Persons value) {
        this.persons = value;
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
     *         &lt;element name="ADS_OID" type="{http://rr.x-road.eu/producer}AdsOidCode"/&gt;
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
        "adsoid"
    })
    public static class AddressEST {

        @XmlElement(name = "ADS_OID", required = true)
        protected String adsoid;

        /**
         * Gets the value of the adsoid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getADSOID() {
            return adsoid;
        }

        /**
         * Sets the value of the adsoid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setADSOID(String value) {
            this.adsoid = value;
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
     *         &lt;element name="CountryCode" type="{http://rr.x-road.eu/producer}CountryCode"/&gt;
     *         &lt;element name="ComponentLevel1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ComponentLevel2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ComponentLevel3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ComponentLevel4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ComponentLevel5" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ComponentLevel6" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ComponentLevel7" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ComponentLevel8" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="PostCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "countryCode",
        "componentLevel1",
        "componentLevel2",
        "componentLevel3",
        "componentLevel4",
        "componentLevel5",
        "componentLevel6",
        "componentLevel7",
        "componentLevel8",
        "postCode"
    })
    public static class AddressNoEST {

        @XmlElement(name = "CountryCode", required = true)
        protected String countryCode;
        @XmlElement(name = "ComponentLevel1", required = true)
        protected String componentLevel1;
        @XmlElement(name = "ComponentLevel2", required = true)
        protected String componentLevel2;
        @XmlElement(name = "ComponentLevel3", required = true)
        protected String componentLevel3;
        @XmlElement(name = "ComponentLevel4", required = true)
        protected String componentLevel4;
        @XmlElement(name = "ComponentLevel5", required = true)
        protected String componentLevel5;
        @XmlElement(name = "ComponentLevel6", required = true)
        protected String componentLevel6;
        @XmlElement(name = "ComponentLevel7", required = true)
        protected String componentLevel7;
        @XmlElement(name = "ComponentLevel8", required = true)
        protected String componentLevel8;
        @XmlElement(name = "PostCode", required = true)
        protected String postCode;

        /**
         * Gets the value of the countryCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCountryCode() {
            return countryCode;
        }

        /**
         * Sets the value of the countryCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCountryCode(String value) {
            this.countryCode = value;
        }

        /**
         * Gets the value of the componentLevel1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComponentLevel1() {
            return componentLevel1;
        }

        /**
         * Sets the value of the componentLevel1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComponentLevel1(String value) {
            this.componentLevel1 = value;
        }

        /**
         * Gets the value of the componentLevel2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComponentLevel2() {
            return componentLevel2;
        }

        /**
         * Sets the value of the componentLevel2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComponentLevel2(String value) {
            this.componentLevel2 = value;
        }

        /**
         * Gets the value of the componentLevel3 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComponentLevel3() {
            return componentLevel3;
        }

        /**
         * Sets the value of the componentLevel3 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComponentLevel3(String value) {
            this.componentLevel3 = value;
        }

        /**
         * Gets the value of the componentLevel4 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComponentLevel4() {
            return componentLevel4;
        }

        /**
         * Sets the value of the componentLevel4 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComponentLevel4(String value) {
            this.componentLevel4 = value;
        }

        /**
         * Gets the value of the componentLevel5 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComponentLevel5() {
            return componentLevel5;
        }

        /**
         * Sets the value of the componentLevel5 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComponentLevel5(String value) {
            this.componentLevel5 = value;
        }

        /**
         * Gets the value of the componentLevel6 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComponentLevel6() {
            return componentLevel6;
        }

        /**
         * Sets the value of the componentLevel6 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComponentLevel6(String value) {
            this.componentLevel6 = value;
        }

        /**
         * Gets the value of the componentLevel7 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComponentLevel7() {
            return componentLevel7;
        }

        /**
         * Sets the value of the componentLevel7 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComponentLevel7(String value) {
            this.componentLevel7 = value;
        }

        /**
         * Gets the value of the componentLevel8 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComponentLevel8() {
            return componentLevel8;
        }

        /**
         * Sets the value of the componentLevel8 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComponentLevel8(String value) {
            this.componentLevel8 = value;
        }

        /**
         * Gets the value of the postCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPostCode() {
            return postCode;
        }

        /**
         * Sets the value of the postCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPostCode(String value) {
            this.postCode = value;
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
     *         &lt;element name="Person" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="PersonalCode" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *                   &lt;element name="GivenName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="FamilyName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    public static class Persons {

        @XmlElement(name = "Person", required = true)
        protected List<RRAddressRequestType.Persons.Person> person;

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
         * {@link RRAddressRequestType.Persons.Person }
         * 
         * 
         */
        public List<RRAddressRequestType.Persons.Person> getPerson() {
            if (person == null) {
                person = new ArrayList<RRAddressRequestType.Persons.Person>();
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
         *         &lt;element name="GivenName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="FamilyName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "givenName",
            "familyName"
        })
        public static class Person {

            @XmlElement(name = "PersonalCode", required = true)
            protected String personalCode;
            @XmlElement(name = "GivenName", required = true)
            protected String givenName;
            @XmlElement(name = "FamilyName", required = true)
            protected String familyName;

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
             * Gets the value of the givenName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getGivenName() {
                return givenName;
            }

            /**
             * Sets the value of the givenName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setGivenName(String value) {
                this.givenName = value;
            }

            /**
             * Gets the value of the familyName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFamilyName() {
                return familyName;
            }

            /**
             * Sets the value of the familyName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFamilyName(String value) {
                this.familyName = value;
            }

        }

    }

}
