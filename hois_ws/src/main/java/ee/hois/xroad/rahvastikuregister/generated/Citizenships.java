
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Citizenships complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Citizenships"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Citizenship"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="RiigiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "Citizenships", propOrder = {
    "citizenship"
})
public class Citizenships {

    @XmlElement(name = "Citizenship", required = true)
    protected Citizenships.Citizenship citizenship;

    /**
     * Gets the value of the citizenship property.
     * 
     * @return
     *     possible object is
     *     {@link Citizenships.Citizenship }
     *     
     */
    public Citizenships.Citizenship getCitizenship() {
        return citizenship;
    }

    /**
     * Sets the value of the citizenship property.
     * 
     * @param value
     *     allowed object is
     *     {@link Citizenships.Citizenship }
     *     
     */
    public void setCitizenship(Citizenships.Citizenship value) {
        this.citizenship = value;
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
     *         &lt;element name="RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="RiigiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "riigiKood",
        "riigiNimi"
    })
    public static class Citizenship {

        @XmlElement(name = "RiigiKood", required = true)
        protected String riigiKood;
        @XmlElement(name = "RiigiNimi", required = true)
        protected String riigiNimi;

        /**
         * Gets the value of the riigiKood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRiigiKood() {
            return riigiKood;
        }

        /**
         * Sets the value of the riigiKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRiigiKood(String value) {
            this.riigiKood = value;
        }

        /**
         * Gets the value of the riigiNimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRiigiNimi() {
            return riigiNimi;
        }

        /**
         * Sets the value of the riigiNimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRiigiNimi(String value) {
            this.riigiNimi = value;
        }

    }

}
