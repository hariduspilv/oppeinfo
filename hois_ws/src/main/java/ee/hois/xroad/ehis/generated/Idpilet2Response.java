
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="idpilet2Jada" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}idpilet2ItemsList"/&gt;
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
    "idpilet2Jada"
})
@XmlRootElement(name = "idpilet2Response")
public class Idpilet2Response {

    @XmlElement(required = true, nillable = true)
    protected Idpilet2ItemsList idpilet2Jada;

    /**
     * Gets the value of the idpilet2Jada property.
     * 
     * @return
     *     possible object is
     *     {@link Idpilet2ItemsList }
     *     
     */
    public Idpilet2ItemsList getIdpilet2Jada() {
        return idpilet2Jada;
    }

    /**
     * Sets the value of the idpilet2Jada property.
     * 
     * @param value
     *     allowed object is
     *     {@link Idpilet2ItemsList }
     *     
     */
    public void setIdpilet2Jada(Idpilet2ItemsList value) {
        this.idpilet2Jada = value;
    }

}
