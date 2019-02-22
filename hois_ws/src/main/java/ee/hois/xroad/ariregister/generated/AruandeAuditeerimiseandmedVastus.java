
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for aruande_auditeerimiseandmed_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aruande_auditeerimiseandmed_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirjed" type="{http://arireg.x-road.eu/producer/}aruande_auditeerimiseandmed_kirjed"/&gt;
 *         &lt;element name="leitud_kirjete_arv" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aruande_auditeerimiseandmed_vastus", propOrder = {
    "kirjed",
    "leitudKirjeteArv"
})
public class AruandeAuditeerimiseandmedVastus {

    @XmlElement(required = true)
    protected AruandeAuditeerimiseandmedKirjed kirjed;
    @XmlElement(name = "leitud_kirjete_arv", required = true)
    protected BigInteger leitudKirjeteArv;

    /**
     * Gets the value of the kirjed property.
     * 
     * @return
     *     possible object is
     *     {@link AruandeAuditeerimiseandmedKirjed }
     *     
     */
    public AruandeAuditeerimiseandmedKirjed getKirjed() {
        return kirjed;
    }

    /**
     * Sets the value of the kirjed property.
     * 
     * @param value
     *     allowed object is
     *     {@link AruandeAuditeerimiseandmedKirjed }
     *     
     */
    public void setKirjed(AruandeAuditeerimiseandmedKirjed value) {
        this.kirjed = value;
    }

    /**
     * Gets the value of the leitudKirjeteArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLeitudKirjeteArv() {
        return leitudKirjeteArv;
    }

    /**
     * Sets the value of the leitudKirjeteArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLeitudKirjeteArv(BigInteger value) {
        this.leitudKirjeteArv = value;
    }

}
