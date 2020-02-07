
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR434ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR434ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikud" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.ValisriigiIsikukoodid" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="VrIK" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="VrIK.VrIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="VrIK.Riik" type="{http://rr.x-road.eu/producer}Riig"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.IsStaatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.IsKirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.PohiKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.Kodakondsused" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.TeovoimeSisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.MuudEesnimed" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="MuuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Isik.MuudPerenimed" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="MuuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Isik.Surmaaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.EestkostjaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.EestkostjaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Isik.EestkostjaIsikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Elukohad"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Elukoht.Riikkood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Elukoht.RiikNim" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Elukoht.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.MaakonnaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.VallaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.KylaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Vaikekohakood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Vaikekohanim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Tanavkood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Tanavnim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Nimikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Niminim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Majanr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Korternr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Alates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                                 &lt;element name="Elukoht.Staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Dokumendid"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Dokument" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Dokument.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Dokument.Riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Dokument.KehtibKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                                 &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
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
@XmlType(name = "RR434ResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud"
})
public class RR434ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood")
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isikud")
    protected RR434ResponseType.Isikud isikud;

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeakood(BigInteger value) {
        this.veakood = value;
    }

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR434ResponseType.Isikud }
     *     
     */
    public RR434ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR434ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR434ResponseType.Isikud value) {
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
     *                   &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.ValisriigiIsikukoodid" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="VrIK" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="VrIK.VrIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="VrIK.Riik" type="{http://rr.x-road.eu/producer}Riig"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.IsStaatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.IsKirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.PohiKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.Kodakondsused" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.TeovoimeSisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.MuudEesnimed" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="MuuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Isik.MuudPerenimed" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="MuuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Isik.Surmaaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.EestkostjaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.EestkostjaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Isik.EestkostjaIsikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Elukohad"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Elukoht.Riikkood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Elukoht.RiikNim" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Elukoht.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.MaakonnaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.VallaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.KylaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Vaikekohakood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Vaikekohanim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Tanavkood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Tanavnim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Nimikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Niminim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Majanr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Korternr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Alates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                                       &lt;element name="Elukoht.Staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Dokumendid"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Dokument" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Dokument.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Dokument.Riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Dokument.KehtibKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                                       &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
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
    @XmlType(name = "", propOrder = {
        "isik"
    })
    public static class Isikud {

        @XmlElement(name = "Isik")
        protected List<RR434ResponseType.Isikud.Isik> isik;

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
         * {@link RR434ResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR434ResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR434ResponseType.Isikud.Isik>();
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
         *         &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isik.ValisriigiIsikukoodid" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="VrIK" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="VrIK.VrIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="VrIK.Riik" type="{http://rr.x-road.eu/producer}Riig"/&gt;
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
         *         &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isik.Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="Isik.IsStaatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isik.IsKirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isik.PohiKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isik.Kodakondsused" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isik.TeovoimeSisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isik.MuudEesnimed" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="MuuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Isik.MuudPerenimed" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="MuuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Isik.Surmaaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="Isik.EestkostjaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isik.EestkostjaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Isik.EestkostjaIsikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Elukohad"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Elukoht.Riikkood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Elukoht.RiikNim" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Elukoht.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.MaakonnaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.VallaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.KylaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Vaikekohakood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Vaikekohanim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Tanavkood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Tanavnim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Nimikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Niminim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Majanr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Korternr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Alates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                             &lt;element name="Elukoht.Staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
         *         &lt;element name="Dokumendid"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Dokument" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Dokument.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Dokument.Riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Dokument.KehtibKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                             &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        @XmlType(name = "", propOrder = {
            "isikIsikukood",
            "isikValisriigiIsikukoodid",
            "isikEesnimi",
            "isikPerenimi",
            "isikSynniaeg",
            "isikIsStaatus",
            "isikIsKirjeStaatus",
            "isikPohiKodakondsus",
            "isikKodakondsused",
            "isikTeovoime",
            "isikTeovoimeSisu",
            "isikMuudEesnimed",
            "isikMuudPerenimed",
            "isikSurmaaeg",
            "isikEestkostjaEesnimi",
            "isikEestkostjaPerenimi",
            "isikEestkostjaIsikukood",
            "elukohad",
            "dokumendid"
        })
        public static class Isik {

            @XmlElement(name = "Isik.Isikukood")
            protected String isikIsikukood;
            @XmlElement(name = "Isik.ValisriigiIsikukoodid")
            protected RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid isikValisriigiIsikukoodid;
            @XmlElement(name = "Isik.Eesnimi")
            protected String isikEesnimi;
            @XmlElement(name = "Isik.Perenimi")
            protected String isikPerenimi;
            @XmlElement(name = "Isik.Synniaeg")
            protected String isikSynniaeg;
            @XmlElement(name = "Isik.IsStaatus")
            protected String isikIsStaatus;
            @XmlElement(name = "Isik.IsKirjeStaatus")
            protected String isikIsKirjeStaatus;
            @XmlElement(name = "Isik.PohiKodakondsus")
            protected String isikPohiKodakondsus;
            @XmlElement(name = "Isik.Kodakondsused")
            protected RR434ResponseType.Isikud.Isik.IsikKodakondsused isikKodakondsused;
            @XmlElement(name = "Isik.Teovoime")
            protected String isikTeovoime;
            @XmlElement(name = "Isik.TeovoimeSisu")
            protected String isikTeovoimeSisu;
            @XmlElement(name = "Isik.MuudEesnimed")
            protected RR434ResponseType.Isikud.Isik.IsikMuudEesnimed isikMuudEesnimed;
            @XmlElement(name = "Isik.MuudPerenimed")
            protected RR434ResponseType.Isikud.Isik.IsikMuudPerenimed isikMuudPerenimed;
            @XmlElement(name = "Isik.Surmaaeg")
            protected String isikSurmaaeg;
            @XmlElement(name = "Isik.EestkostjaEesnimi")
            protected String isikEestkostjaEesnimi;
            @XmlElement(name = "Isik.EestkostjaPerenimi")
            protected String isikEestkostjaPerenimi;
            @XmlElement(name = "Isik.EestkostjaIsikukood")
            protected String isikEestkostjaIsikukood;
            @XmlElement(name = "Elukohad", required = true)
            protected RR434ResponseType.Isikud.Isik.Elukohad elukohad;
            @XmlElement(name = "Dokumendid", required = true)
            protected RR434ResponseType.Isikud.Isik.Dokumendid dokumendid;

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
             * Gets the value of the isikValisriigiIsikukoodid property.
             * 
             * @return
             *     possible object is
             *     {@link RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid }
             *     
             */
            public RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid getIsikValisriigiIsikukoodid() {
                return isikValisriigiIsikukoodid;
            }

            /**
             * Sets the value of the isikValisriigiIsikukoodid property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid }
             *     
             */
            public void setIsikValisriigiIsikukoodid(RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid value) {
                this.isikValisriigiIsikukoodid = value;
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
             * Gets the value of the isikSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSynniaeg() {
                return isikSynniaeg;
            }

            /**
             * Sets the value of the isikSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSynniaeg(String value) {
                this.isikSynniaeg = value;
            }

            /**
             * Gets the value of the isikIsStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsStaatus() {
                return isikIsStaatus;
            }

            /**
             * Sets the value of the isikIsStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsStaatus(String value) {
                this.isikIsStaatus = value;
            }

            /**
             * Gets the value of the isikIsKirjeStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsKirjeStaatus() {
                return isikIsKirjeStaatus;
            }

            /**
             * Sets the value of the isikIsKirjeStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsKirjeStaatus(String value) {
                this.isikIsKirjeStaatus = value;
            }

            /**
             * Gets the value of the isikPohiKodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikPohiKodakondsus() {
                return isikPohiKodakondsus;
            }

            /**
             * Sets the value of the isikPohiKodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikPohiKodakondsus(String value) {
                this.isikPohiKodakondsus = value;
            }

            /**
             * Gets the value of the isikKodakondsused property.
             * 
             * @return
             *     possible object is
             *     {@link RR434ResponseType.Isikud.Isik.IsikKodakondsused }
             *     
             */
            public RR434ResponseType.Isikud.Isik.IsikKodakondsused getIsikKodakondsused() {
                return isikKodakondsused;
            }

            /**
             * Sets the value of the isikKodakondsused property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR434ResponseType.Isikud.Isik.IsikKodakondsused }
             *     
             */
            public void setIsikKodakondsused(RR434ResponseType.Isikud.Isik.IsikKodakondsused value) {
                this.isikKodakondsused = value;
            }

            /**
             * Gets the value of the isikTeovoime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTeovoime() {
                return isikTeovoime;
            }

            /**
             * Sets the value of the isikTeovoime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTeovoime(String value) {
                this.isikTeovoime = value;
            }

            /**
             * Gets the value of the isikTeovoimeSisu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTeovoimeSisu() {
                return isikTeovoimeSisu;
            }

            /**
             * Sets the value of the isikTeovoimeSisu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTeovoimeSisu(String value) {
                this.isikTeovoimeSisu = value;
            }

            /**
             * Gets the value of the isikMuudEesnimed property.
             * 
             * @return
             *     possible object is
             *     {@link RR434ResponseType.Isikud.Isik.IsikMuudEesnimed }
             *     
             */
            public RR434ResponseType.Isikud.Isik.IsikMuudEesnimed getIsikMuudEesnimed() {
                return isikMuudEesnimed;
            }

            /**
             * Sets the value of the isikMuudEesnimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR434ResponseType.Isikud.Isik.IsikMuudEesnimed }
             *     
             */
            public void setIsikMuudEesnimed(RR434ResponseType.Isikud.Isik.IsikMuudEesnimed value) {
                this.isikMuudEesnimed = value;
            }

            /**
             * Gets the value of the isikMuudPerenimed property.
             * 
             * @return
             *     possible object is
             *     {@link RR434ResponseType.Isikud.Isik.IsikMuudPerenimed }
             *     
             */
            public RR434ResponseType.Isikud.Isik.IsikMuudPerenimed getIsikMuudPerenimed() {
                return isikMuudPerenimed;
            }

            /**
             * Sets the value of the isikMuudPerenimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR434ResponseType.Isikud.Isik.IsikMuudPerenimed }
             *     
             */
            public void setIsikMuudPerenimed(RR434ResponseType.Isikud.Isik.IsikMuudPerenimed value) {
                this.isikMuudPerenimed = value;
            }

            /**
             * Gets the value of the isikSurmaaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSurmaaeg() {
                return isikSurmaaeg;
            }

            /**
             * Sets the value of the isikSurmaaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSurmaaeg(String value) {
                this.isikSurmaaeg = value;
            }

            /**
             * Gets the value of the isikEestkostjaEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEestkostjaEesnimi() {
                return isikEestkostjaEesnimi;
            }

            /**
             * Sets the value of the isikEestkostjaEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEestkostjaEesnimi(String value) {
                this.isikEestkostjaEesnimi = value;
            }

            /**
             * Gets the value of the isikEestkostjaPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEestkostjaPerenimi() {
                return isikEestkostjaPerenimi;
            }

            /**
             * Sets the value of the isikEestkostjaPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEestkostjaPerenimi(String value) {
                this.isikEestkostjaPerenimi = value;
            }

            /**
             * Gets the value of the isikEestkostjaIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEestkostjaIsikukood() {
                return isikEestkostjaIsikukood;
            }

            /**
             * Sets the value of the isikEestkostjaIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEestkostjaIsikukood(String value) {
                this.isikEestkostjaIsikukood = value;
            }

            /**
             * Gets the value of the elukohad property.
             * 
             * @return
             *     possible object is
             *     {@link RR434ResponseType.Isikud.Isik.Elukohad }
             *     
             */
            public RR434ResponseType.Isikud.Isik.Elukohad getElukohad() {
                return elukohad;
            }

            /**
             * Sets the value of the elukohad property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR434ResponseType.Isikud.Isik.Elukohad }
             *     
             */
            public void setElukohad(RR434ResponseType.Isikud.Isik.Elukohad value) {
                this.elukohad = value;
            }

            /**
             * Gets the value of the dokumendid property.
             * 
             * @return
             *     possible object is
             *     {@link RR434ResponseType.Isikud.Isik.Dokumendid }
             *     
             */
            public RR434ResponseType.Isikud.Isik.Dokumendid getDokumendid() {
                return dokumendid;
            }

            /**
             * Sets the value of the dokumendid property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR434ResponseType.Isikud.Isik.Dokumendid }
             *     
             */
            public void setDokumendid(RR434ResponseType.Isikud.Isik.Dokumendid value) {
                this.dokumendid = value;
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
             *         &lt;element name="Dokument" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Dokument.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Dokument.Riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Dokument.KehtibKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *                   &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "dokument"
            })
            public static class Dokumendid {

                @XmlElement(name = "Dokument")
                protected List<RR434ResponseType.Isikud.Isik.Dokumendid.Dokument> dokument;

                /**
                 * Gets the value of the dokument property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the dokument property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDokument().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RR434ResponseType.Isikud.Isik.Dokumendid.Dokument }
                 * 
                 * 
                 */
                public List<RR434ResponseType.Isikud.Isik.Dokumendid.Dokument> getDokument() {
                    if (dokument == null) {
                        dokument = new ArrayList<RR434ResponseType.Isikud.Isik.Dokumendid.Dokument>();
                    }
                    return this.dokument;
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
                 *         &lt;element name="Dokument.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Dokument.Riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Dokument.KehtibKuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
                 *         &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "dokumentLiik",
                    "dokumentRiik",
                    "dokumentNumber",
                    "dokumentKehtibKuni",
                    "dokumentStaatus"
                })
                public static class Dokument {

                    @XmlElement(name = "Dokument.Liik", required = true)
                    protected String dokumentLiik;
                    @XmlElement(name = "Dokument.Riik")
                    protected String dokumentRiik;
                    @XmlElement(name = "Dokument.Number")
                    protected String dokumentNumber;
                    @XmlElement(name = "Dokument.KehtibKuni")
                    protected String dokumentKehtibKuni;
                    @XmlElement(name = "Dokument.Staatus", required = true)
                    protected String dokumentStaatus;

                    /**
                     * Gets the value of the dokumentLiik property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDokumentLiik() {
                        return dokumentLiik;
                    }

                    /**
                     * Sets the value of the dokumentLiik property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDokumentLiik(String value) {
                        this.dokumentLiik = value;
                    }

                    /**
                     * Gets the value of the dokumentRiik property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDokumentRiik() {
                        return dokumentRiik;
                    }

                    /**
                     * Sets the value of the dokumentRiik property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDokumentRiik(String value) {
                        this.dokumentRiik = value;
                    }

                    /**
                     * Gets the value of the dokumentNumber property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDokumentNumber() {
                        return dokumentNumber;
                    }

                    /**
                     * Sets the value of the dokumentNumber property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDokumentNumber(String value) {
                        this.dokumentNumber = value;
                    }

                    /**
                     * Gets the value of the dokumentKehtibKuni property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDokumentKehtibKuni() {
                        return dokumentKehtibKuni;
                    }

                    /**
                     * Sets the value of the dokumentKehtibKuni property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDokumentKehtibKuni(String value) {
                        this.dokumentKehtibKuni = value;
                    }

                    /**
                     * Gets the value of the dokumentStaatus property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDokumentStaatus() {
                        return dokumentStaatus;
                    }

                    /**
                     * Sets the value of the dokumentStaatus property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDokumentStaatus(String value) {
                        this.dokumentStaatus = value;
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
             *         &lt;element name="Elukoht" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Elukoht.Riikkood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Elukoht.RiikNim" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Elukoht.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.MaakonnaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.VallaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.KylaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Vaikekohakood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Vaikekohanim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Tanavkood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Tanavnim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Nimikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Niminim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Majanr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Korternr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Alates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *                   &lt;element name="Elukoht.Staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "elukoht"
            })
            public static class Elukohad {

                @XmlElement(name = "Elukoht")
                protected List<RR434ResponseType.Isikud.Isik.Elukohad.Elukoht> elukoht;

                /**
                 * Gets the value of the elukoht property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the elukoht property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getElukoht().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RR434ResponseType.Isikud.Isik.Elukohad.Elukoht }
                 * 
                 * 
                 */
                public List<RR434ResponseType.Isikud.Isik.Elukohad.Elukoht> getElukoht() {
                    if (elukoht == null) {
                        elukoht = new ArrayList<RR434ResponseType.Isikud.Isik.Elukohad.Elukoht>();
                    }
                    return this.elukoht;
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
                 *         &lt;element name="Elukoht.Riikkood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Elukoht.RiikNim" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Elukoht.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.MaakonnaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.VallaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.KylaNim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Vaikekohakood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Vaikekohanim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Tanavkood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Tanavnim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Nimikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Niminim" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Majanr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Korternr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Alates" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
                 *         &lt;element name="Elukoht.Staatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                    "elukohtRiikkood",
                    "elukohtRiikNim",
                    "elukohtMaakonnaKood",
                    "elukohtMaakonnaNim",
                    "elukohtVallaKood",
                    "elukohtVallaNim",
                    "elukohtKylaKood",
                    "elukohtKylaNim",
                    "elukohtVaikekohakood",
                    "elukohtVaikekohanim",
                    "elukohtTanavkood",
                    "elukohtTanavnim",
                    "elukohtNimikood",
                    "elukohtNiminim",
                    "elukohtMajanr",
                    "elukohtKorternr",
                    "elukohtTekst",
                    "elukohtPostiindeks",
                    "elukohtAdsAdrID",
                    "elukohtAdsOid",
                    "elukohtKoodaadress",
                    "elukohtAlates",
                    "elukohtKuni",
                    "elukohtStaatus"
                })
                public static class Elukoht {

                    @XmlElement(name = "Elukoht.Riikkood", required = true)
                    protected String elukohtRiikkood;
                    @XmlElement(name = "Elukoht.RiikNim", required = true)
                    protected String elukohtRiikNim;
                    @XmlElement(name = "Elukoht.MaakonnaKood")
                    protected String elukohtMaakonnaKood;
                    @XmlElement(name = "Elukoht.MaakonnaNim")
                    protected String elukohtMaakonnaNim;
                    @XmlElement(name = "Elukoht.VallaKood")
                    protected String elukohtVallaKood;
                    @XmlElement(name = "Elukoht.VallaNim")
                    protected String elukohtVallaNim;
                    @XmlElement(name = "Elukoht.KylaKood")
                    protected String elukohtKylaKood;
                    @XmlElement(name = "Elukoht.KylaNim")
                    protected String elukohtKylaNim;
                    @XmlElement(name = "Elukoht.Vaikekohakood")
                    protected String elukohtVaikekohakood;
                    @XmlElement(name = "Elukoht.Vaikekohanim")
                    protected String elukohtVaikekohanim;
                    @XmlElement(name = "Elukoht.Tanavkood")
                    protected String elukohtTanavkood;
                    @XmlElement(name = "Elukoht.Tanavnim")
                    protected String elukohtTanavnim;
                    @XmlElement(name = "Elukoht.Nimikood")
                    protected String elukohtNimikood;
                    @XmlElement(name = "Elukoht.Niminim")
                    protected String elukohtNiminim;
                    @XmlElement(name = "Elukoht.Majanr")
                    protected String elukohtMajanr;
                    @XmlElement(name = "Elukoht.Korternr")
                    protected String elukohtKorternr;
                    @XmlElement(name = "Elukoht.Tekst")
                    protected String elukohtTekst;
                    @XmlElement(name = "Elukoht.Postiindeks")
                    protected String elukohtPostiindeks;
                    @XmlElement(name = "Elukoht.AdsAdrID")
                    protected String elukohtAdsAdrID;
                    @XmlElement(name = "Elukoht.AdsOid")
                    protected String elukohtAdsOid;
                    @XmlElement(name = "Elukoht.Koodaadress")
                    protected String elukohtKoodaadress;
                    @XmlElement(name = "Elukoht.Alates")
                    protected String elukohtAlates;
                    @XmlElement(name = "Elukoht.Kuni")
                    protected String elukohtKuni;
                    @XmlElement(name = "Elukoht.Staatus")
                    protected String elukohtStaatus;

                    /**
                     * Gets the value of the elukohtRiikkood property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtRiikkood() {
                        return elukohtRiikkood;
                    }

                    /**
                     * Sets the value of the elukohtRiikkood property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtRiikkood(String value) {
                        this.elukohtRiikkood = value;
                    }

                    /**
                     * Gets the value of the elukohtRiikNim property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtRiikNim() {
                        return elukohtRiikNim;
                    }

                    /**
                     * Sets the value of the elukohtRiikNim property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtRiikNim(String value) {
                        this.elukohtRiikNim = value;
                    }

                    /**
                     * Gets the value of the elukohtMaakonnaKood property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtMaakonnaKood() {
                        return elukohtMaakonnaKood;
                    }

                    /**
                     * Sets the value of the elukohtMaakonnaKood property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtMaakonnaKood(String value) {
                        this.elukohtMaakonnaKood = value;
                    }

                    /**
                     * Gets the value of the elukohtMaakonnaNim property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtMaakonnaNim() {
                        return elukohtMaakonnaNim;
                    }

                    /**
                     * Sets the value of the elukohtMaakonnaNim property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtMaakonnaNim(String value) {
                        this.elukohtMaakonnaNim = value;
                    }

                    /**
                     * Gets the value of the elukohtVallaKood property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtVallaKood() {
                        return elukohtVallaKood;
                    }

                    /**
                     * Sets the value of the elukohtVallaKood property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtVallaKood(String value) {
                        this.elukohtVallaKood = value;
                    }

                    /**
                     * Gets the value of the elukohtVallaNim property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtVallaNim() {
                        return elukohtVallaNim;
                    }

                    /**
                     * Sets the value of the elukohtVallaNim property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtVallaNim(String value) {
                        this.elukohtVallaNim = value;
                    }

                    /**
                     * Gets the value of the elukohtKylaKood property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtKylaKood() {
                        return elukohtKylaKood;
                    }

                    /**
                     * Sets the value of the elukohtKylaKood property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtKylaKood(String value) {
                        this.elukohtKylaKood = value;
                    }

                    /**
                     * Gets the value of the elukohtKylaNim property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtKylaNim() {
                        return elukohtKylaNim;
                    }

                    /**
                     * Sets the value of the elukohtKylaNim property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtKylaNim(String value) {
                        this.elukohtKylaNim = value;
                    }

                    /**
                     * Gets the value of the elukohtVaikekohakood property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtVaikekohakood() {
                        return elukohtVaikekohakood;
                    }

                    /**
                     * Sets the value of the elukohtVaikekohakood property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtVaikekohakood(String value) {
                        this.elukohtVaikekohakood = value;
                    }

                    /**
                     * Gets the value of the elukohtVaikekohanim property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtVaikekohanim() {
                        return elukohtVaikekohanim;
                    }

                    /**
                     * Sets the value of the elukohtVaikekohanim property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtVaikekohanim(String value) {
                        this.elukohtVaikekohanim = value;
                    }

                    /**
                     * Gets the value of the elukohtTanavkood property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtTanavkood() {
                        return elukohtTanavkood;
                    }

                    /**
                     * Sets the value of the elukohtTanavkood property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtTanavkood(String value) {
                        this.elukohtTanavkood = value;
                    }

                    /**
                     * Gets the value of the elukohtTanavnim property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtTanavnim() {
                        return elukohtTanavnim;
                    }

                    /**
                     * Sets the value of the elukohtTanavnim property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtTanavnim(String value) {
                        this.elukohtTanavnim = value;
                    }

                    /**
                     * Gets the value of the elukohtNimikood property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtNimikood() {
                        return elukohtNimikood;
                    }

                    /**
                     * Sets the value of the elukohtNimikood property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtNimikood(String value) {
                        this.elukohtNimikood = value;
                    }

                    /**
                     * Gets the value of the elukohtNiminim property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtNiminim() {
                        return elukohtNiminim;
                    }

                    /**
                     * Sets the value of the elukohtNiminim property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtNiminim(String value) {
                        this.elukohtNiminim = value;
                    }

                    /**
                     * Gets the value of the elukohtMajanr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtMajanr() {
                        return elukohtMajanr;
                    }

                    /**
                     * Sets the value of the elukohtMajanr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtMajanr(String value) {
                        this.elukohtMajanr = value;
                    }

                    /**
                     * Gets the value of the elukohtKorternr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtKorternr() {
                        return elukohtKorternr;
                    }

                    /**
                     * Sets the value of the elukohtKorternr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtKorternr(String value) {
                        this.elukohtKorternr = value;
                    }

                    /**
                     * Gets the value of the elukohtTekst property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtTekst() {
                        return elukohtTekst;
                    }

                    /**
                     * Sets the value of the elukohtTekst property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtTekst(String value) {
                        this.elukohtTekst = value;
                    }

                    /**
                     * Gets the value of the elukohtPostiindeks property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtPostiindeks() {
                        return elukohtPostiindeks;
                    }

                    /**
                     * Sets the value of the elukohtPostiindeks property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtPostiindeks(String value) {
                        this.elukohtPostiindeks = value;
                    }

                    /**
                     * Gets the value of the elukohtAdsAdrID property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtAdsAdrID() {
                        return elukohtAdsAdrID;
                    }

                    /**
                     * Sets the value of the elukohtAdsAdrID property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtAdsAdrID(String value) {
                        this.elukohtAdsAdrID = value;
                    }

                    /**
                     * Gets the value of the elukohtAdsOid property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtAdsOid() {
                        return elukohtAdsOid;
                    }

                    /**
                     * Sets the value of the elukohtAdsOid property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtAdsOid(String value) {
                        this.elukohtAdsOid = value;
                    }

                    /**
                     * Gets the value of the elukohtKoodaadress property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtKoodaadress() {
                        return elukohtKoodaadress;
                    }

                    /**
                     * Sets the value of the elukohtKoodaadress property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtKoodaadress(String value) {
                        this.elukohtKoodaadress = value;
                    }

                    /**
                     * Gets the value of the elukohtAlates property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtAlates() {
                        return elukohtAlates;
                    }

                    /**
                     * Sets the value of the elukohtAlates property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtAlates(String value) {
                        this.elukohtAlates = value;
                    }

                    /**
                     * Gets the value of the elukohtKuni property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtKuni() {
                        return elukohtKuni;
                    }

                    /**
                     * Sets the value of the elukohtKuni property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtKuni(String value) {
                        this.elukohtKuni = value;
                    }

                    /**
                     * Gets the value of the elukohtStaatus property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getElukohtStaatus() {
                        return elukohtStaatus;
                    }

                    /**
                     * Sets the value of the elukohtStaatus property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setElukohtStaatus(String value) {
                        this.elukohtStaatus = value;
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
             *         &lt;element name="Kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                "kodakondsus"
            })
            public static class IsikKodakondsused {

                @XmlElement(name = "Kodakondsus")
                protected List<String> kodakondsus;

                /**
                 * Gets the value of the kodakondsus property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the kodakondsus property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getKodakondsus().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getKodakondsus() {
                    if (kodakondsus == null) {
                        kodakondsus = new ArrayList<String>();
                    }
                    return this.kodakondsus;
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
             *         &lt;element name="MuuEesnimi" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                "muuEesnimi"
            })
            public static class IsikMuudEesnimed {

                @XmlElement(name = "MuuEesnimi")
                protected List<String> muuEesnimi;

                /**
                 * Gets the value of the muuEesnimi property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the muuEesnimi property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getMuuEesnimi().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getMuuEesnimi() {
                    if (muuEesnimi == null) {
                        muuEesnimi = new ArrayList<String>();
                    }
                    return this.muuEesnimi;
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
             *         &lt;element name="MuuPerenimi" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                "muuPerenimi"
            })
            public static class IsikMuudPerenimed {

                @XmlElement(name = "MuuPerenimi")
                protected List<String> muuPerenimi;

                /**
                 * Gets the value of the muuPerenimi property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the muuPerenimi property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getMuuPerenimi().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getMuuPerenimi() {
                    if (muuPerenimi == null) {
                        muuPerenimi = new ArrayList<String>();
                    }
                    return this.muuPerenimi;
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
             *         &lt;element name="VrIK" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="VrIK.VrIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="VrIK.Riik" type="{http://rr.x-road.eu/producer}Riig"/&gt;
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
                "vrIK"
            })
            public static class IsikValisriigiIsikukoodid {

                @XmlElement(name = "VrIK")
                protected List<RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.VrIK> vrIK;

                /**
                 * Gets the value of the vrIK property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the vrIK property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getVrIK().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.VrIK }
                 * 
                 * 
                 */
                public List<RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.VrIK> getVrIK() {
                    if (vrIK == null) {
                        vrIK = new ArrayList<RR434ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.VrIK>();
                    }
                    return this.vrIK;
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
                 *         &lt;element name="VrIK.VrIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="VrIK.Riik" type="{http://rr.x-road.eu/producer}Riig"/&gt;
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
                    "vrIKVrIK",
                    "vrIKRiik"
                })
                public static class VrIK {

                    @XmlElement(name = "VrIK.VrIK", required = true)
                    protected String vrIKVrIK;
                    @XmlElement(name = "VrIK.Riik", required = true)
                    protected Riig vrIKRiik;

                    /**
                     * Gets the value of the vrIKVrIK property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getVrIKVrIK() {
                        return vrIKVrIK;
                    }

                    /**
                     * Sets the value of the vrIKVrIK property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setVrIKVrIK(String value) {
                        this.vrIKVrIK = value;
                    }

                    /**
                     * Gets the value of the vrIKRiik property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Riig }
                     *     
                     */
                    public Riig getVrIKRiik() {
                        return vrIKRiik;
                    }

                    /**
                     * Sets the value of the vrIKRiik property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Riig }
                     *     
                     */
                    public void setVrIKRiik(Riig value) {
                        this.vrIKRiik = value;
                    }

                }

            }

        }

    }

}
