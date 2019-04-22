
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR438isikAadrDokEresidResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR438isikAadrDokEresidResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isik" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="Isik.SynniKoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Surmaaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="Isik.PohikodakondsusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.PohikodakondsusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.KodakondsusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.KodakondsusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Isik.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ElamisDokumendiTunnus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ElamisDokument" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ElamisDokumentKehtivKuni" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                   &lt;element name="Elukoht" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Elukoht.RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.RiigiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.MaakonnaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.VallaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.AsulaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.AsulaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.VaikekohaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.TanavaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.NimiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukoht.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="Elukoht.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Sideaadress" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Sideaadress.RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.RiigiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.MaakonnaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.VallaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.AsulaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.AsulaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.VaikekohaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.TanavaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.NimiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Sideaadress.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="Sideaadress.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Telefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *                                       &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Dokument.ValjaAntud" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                                       &lt;element name="Dokument.KehtibKuni" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                                       &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Dokument.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR438isikAadrDokEresidResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isik"
})
public class RR438IsikAadrDokEresidResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood")
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isik")
    protected RR438IsikAadrDokEresidResponseType.Isik isik;

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
     * Gets the value of the isik property.
     * 
     * @return
     *     possible object is
     *     {@link RR438IsikAadrDokEresidResponseType.Isik }
     *     
     */
    public RR438IsikAadrDokEresidResponseType.Isik getIsik() {
        return isik;
    }

    /**
     * Sets the value of the isik property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR438IsikAadrDokEresidResponseType.Isik }
     *     
     */
    public void setIsik(RR438IsikAadrDokEresidResponseType.Isik value) {
        this.isik = value;
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
     *         &lt;element name="Isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="Isik.SynniKoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.Surmaaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="Isik.PohikodakondsusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.PohikodakondsusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.KodakondsusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.KodakondsusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Isik.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ElamisDokumendiTunnus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ElamisDokument" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ElamisDokumentKehtivKuni" type="{http://rr.x-road.eu/producer}date"/&gt;
     *         &lt;element name="Elukoht" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Elukoht.RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.RiigiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.MaakonnaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.VallaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.AsulaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.AsulaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.VaikekohaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.TanavaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.NimiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukoht.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="Elukoht.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Sideaadress" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Sideaadress.RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.RiigiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.MaakonnaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.VallaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.AsulaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.AsulaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.VaikekohaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.TanavaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.NimiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Sideaadress.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="Sideaadress.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Telefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
     *                             &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Dokument.ValjaAntud" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                             &lt;element name="Dokument.KehtibKuni" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                             &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Dokument.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "isikEesnimi",
        "isikPerenimi",
        "isikSugu",
        "isikSynniaeg",
        "isikSynniKoht",
        "isikSurmaaeg",
        "isikPohikodakondsusKood",
        "isikPohikodakondsusNimi",
        "isikKodakondsusKood",
        "isikKodakondsusNimi",
        "isikTeovoime",
        "isikTegevusala",
        "isikIsikuStaatus",
        "isikKirjeStaatus",
        "elamisDokumendiTunnus",
        "elamisDokument",
        "elamisDokumentKehtivKuni",
        "elukoht",
        "sideaadress",
        "telefon",
        "dokumendid"
    })
    public static class Isik {

        @XmlElement(name = "Isik.Isikukood", required = true)
        protected String isikIsikukood;
        @XmlElement(name = "Isik.Eesnimi", required = true)
        protected String isikEesnimi;
        @XmlElement(name = "Isik.Perenimi", required = true)
        protected String isikPerenimi;
        @XmlElement(name = "Isik.Sugu", required = true)
        protected String isikSugu;
        @XmlElement(name = "Isik.Synniaeg", required = true)
        protected String isikSynniaeg;
        @XmlElement(name = "Isik.SynniKoht", required = true)
        protected String isikSynniKoht;
        @XmlElement(name = "Isik.Surmaaeg", required = true)
        protected String isikSurmaaeg;
        @XmlElement(name = "Isik.PohikodakondsusKood", required = true)
        protected String isikPohikodakondsusKood;
        @XmlElement(name = "Isik.PohikodakondsusNimi", required = true)
        protected String isikPohikodakondsusNimi;
        @XmlElement(name = "Isik.KodakondsusKood", required = true)
        protected String isikKodakondsusKood;
        @XmlElement(name = "Isik.KodakondsusNimi", required = true)
        protected String isikKodakondsusNimi;
        @XmlElement(name = "Isik.Teovoime", required = true)
        protected String isikTeovoime;
        @XmlElement(name = "Isik.Tegevusala", required = true)
        protected String isikTegevusala;
        @XmlElement(name = "Isik.IsikuStaatus", required = true)
        protected String isikIsikuStaatus;
        @XmlElement(name = "Isik.KirjeStaatus", required = true)
        protected String isikKirjeStaatus;
        @XmlElement(name = "ElamisDokumendiTunnus", required = true)
        protected String elamisDokumendiTunnus;
        @XmlElement(name = "ElamisDokument", required = true)
        protected String elamisDokument;
        @XmlElement(name = "ElamisDokumentKehtivKuni", required = true)
        protected String elamisDokumentKehtivKuni;
        @XmlElement(name = "Elukoht")
        protected RR438IsikAadrDokEresidResponseType.Isik.Elukoht elukoht;
        @XmlElement(name = "Sideaadress")
        protected RR438IsikAadrDokEresidResponseType.Isik.Sideaadress sideaadress;
        @XmlElement(name = "Telefon")
        protected String telefon;
        @XmlElement(name = "Dokumendid", required = true)
        protected RR438IsikAadrDokEresidResponseType.Isik.Dokumendid dokumendid;

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
         * Gets the value of the isikSugu property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikSugu() {
            return isikSugu;
        }

        /**
         * Sets the value of the isikSugu property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikSugu(String value) {
            this.isikSugu = value;
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
         * Gets the value of the isikSynniKoht property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikSynniKoht() {
            return isikSynniKoht;
        }

        /**
         * Sets the value of the isikSynniKoht property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikSynniKoht(String value) {
            this.isikSynniKoht = value;
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
         * Gets the value of the isikPohikodakondsusKood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikPohikodakondsusKood() {
            return isikPohikodakondsusKood;
        }

        /**
         * Sets the value of the isikPohikodakondsusKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikPohikodakondsusKood(String value) {
            this.isikPohikodakondsusKood = value;
        }

        /**
         * Gets the value of the isikPohikodakondsusNimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikPohikodakondsusNimi() {
            return isikPohikodakondsusNimi;
        }

        /**
         * Sets the value of the isikPohikodakondsusNimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikPohikodakondsusNimi(String value) {
            this.isikPohikodakondsusNimi = value;
        }

        /**
         * Gets the value of the isikKodakondsusKood property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikKodakondsusKood() {
            return isikKodakondsusKood;
        }

        /**
         * Sets the value of the isikKodakondsusKood property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikKodakondsusKood(String value) {
            this.isikKodakondsusKood = value;
        }

        /**
         * Gets the value of the isikKodakondsusNimi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikKodakondsusNimi() {
            return isikKodakondsusNimi;
        }

        /**
         * Sets the value of the isikKodakondsusNimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikKodakondsusNimi(String value) {
            this.isikKodakondsusNimi = value;
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
         * Gets the value of the isikTegevusala property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikTegevusala() {
            return isikTegevusala;
        }

        /**
         * Sets the value of the isikTegevusala property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikTegevusala(String value) {
            this.isikTegevusala = value;
        }

        /**
         * Gets the value of the isikIsikuStaatus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikIsikuStaatus() {
            return isikIsikuStaatus;
        }

        /**
         * Sets the value of the isikIsikuStaatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikIsikuStaatus(String value) {
            this.isikIsikuStaatus = value;
        }

        /**
         * Gets the value of the isikKirjeStaatus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsikKirjeStaatus() {
            return isikKirjeStaatus;
        }

        /**
         * Sets the value of the isikKirjeStaatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsikKirjeStaatus(String value) {
            this.isikKirjeStaatus = value;
        }

        /**
         * Gets the value of the elamisDokumendiTunnus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getElamisDokumendiTunnus() {
            return elamisDokumendiTunnus;
        }

        /**
         * Sets the value of the elamisDokumendiTunnus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setElamisDokumendiTunnus(String value) {
            this.elamisDokumendiTunnus = value;
        }

        /**
         * Gets the value of the elamisDokument property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getElamisDokument() {
            return elamisDokument;
        }

        /**
         * Sets the value of the elamisDokument property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setElamisDokument(String value) {
            this.elamisDokument = value;
        }

        /**
         * Gets the value of the elamisDokumentKehtivKuni property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getElamisDokumentKehtivKuni() {
            return elamisDokumentKehtivKuni;
        }

        /**
         * Sets the value of the elamisDokumentKehtivKuni property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setElamisDokumentKehtivKuni(String value) {
            this.elamisDokumentKehtivKuni = value;
        }

        /**
         * Gets the value of the elukoht property.
         * 
         * @return
         *     possible object is
         *     {@link RR438IsikAadrDokEresidResponseType.Isik.Elukoht }
         *     
         */
        public RR438IsikAadrDokEresidResponseType.Isik.Elukoht getElukoht() {
            return elukoht;
        }

        /**
         * Sets the value of the elukoht property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR438IsikAadrDokEresidResponseType.Isik.Elukoht }
         *     
         */
        public void setElukoht(RR438IsikAadrDokEresidResponseType.Isik.Elukoht value) {
            this.elukoht = value;
        }

        /**
         * Gets the value of the sideaadress property.
         * 
         * @return
         *     possible object is
         *     {@link RR438IsikAadrDokEresidResponseType.Isik.Sideaadress }
         *     
         */
        public RR438IsikAadrDokEresidResponseType.Isik.Sideaadress getSideaadress() {
            return sideaadress;
        }

        /**
         * Sets the value of the sideaadress property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR438IsikAadrDokEresidResponseType.Isik.Sideaadress }
         *     
         */
        public void setSideaadress(RR438IsikAadrDokEresidResponseType.Isik.Sideaadress value) {
            this.sideaadress = value;
        }

        /**
         * Gets the value of the telefon property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTelefon() {
            return telefon;
        }

        /**
         * Sets the value of the telefon property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTelefon(String value) {
            this.telefon = value;
        }

        /**
         * Gets the value of the dokumendid property.
         * 
         * @return
         *     possible object is
         *     {@link RR438IsikAadrDokEresidResponseType.Isik.Dokumendid }
         *     
         */
        public RR438IsikAadrDokEresidResponseType.Isik.Dokumendid getDokumendid() {
            return dokumendid;
        }

        /**
         * Sets the value of the dokumendid property.
         * 
         * @param value
         *     allowed object is
         *     {@link RR438IsikAadrDokEresidResponseType.Isik.Dokumendid }
         *     
         */
        public void setDokumendid(RR438IsikAadrDokEresidResponseType.Isik.Dokumendid value) {
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
         *                   &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Dokument.ValjaAntud" type="{http://rr.x-road.eu/producer}date"/&gt;
         *                   &lt;element name="Dokument.KehtibKuni" type="{http://rr.x-road.eu/producer}date"/&gt;
         *                   &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Dokument.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            protected List<RR438IsikAadrDokEresidResponseType.Isik.Dokumendid.Dokument> dokument;

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
             * {@link RR438IsikAadrDokEresidResponseType.Isik.Dokumendid.Dokument }
             * 
             * 
             */
            public List<RR438IsikAadrDokEresidResponseType.Isik.Dokumendid.Dokument> getDokument() {
                if (dokument == null) {
                    dokument = new ArrayList<RR438IsikAadrDokEresidResponseType.Isik.Dokumendid.Dokument>();
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
             *         &lt;element name="Dokument.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Dokument.ValjaAntud" type="{http://rr.x-road.eu/producer}date"/&gt;
             *         &lt;element name="Dokument.KehtibKuni" type="{http://rr.x-road.eu/producer}date"/&gt;
             *         &lt;element name="Dokument.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Dokument.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "dokumentNumber",
                "dokumentValjaAntud",
                "dokumentKehtibKuni",
                "dokumentStaatus",
                "dokumentAsutus"
            })
            public static class Dokument {

                @XmlElement(name = "Dokument.Liik", required = true)
                protected String dokumentLiik;
                @XmlElement(name = "Dokument.Number", required = true)
                protected String dokumentNumber;
                @XmlElement(name = "Dokument.ValjaAntud", required = true)
                protected String dokumentValjaAntud;
                @XmlElement(name = "Dokument.KehtibKuni", required = true)
                protected String dokumentKehtibKuni;
                @XmlElement(name = "Dokument.Staatus", required = true)
                protected String dokumentStaatus;
                @XmlElement(name = "Dokument.Asutus", required = true)
                protected String dokumentAsutus;

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
                 * Gets the value of the dokumentValjaAntud property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDokumentValjaAntud() {
                    return dokumentValjaAntud;
                }

                /**
                 * Sets the value of the dokumentValjaAntud property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDokumentValjaAntud(String value) {
                    this.dokumentValjaAntud = value;
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

                /**
                 * Gets the value of the dokumentAsutus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDokumentAsutus() {
                    return dokumentAsutus;
                }

                /**
                 * Sets the value of the dokumentAsutus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDokumentAsutus(String value) {
                    this.dokumentAsutus = value;
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
         *         &lt;element name="Elukoht.RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.RiigiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.MaakonnaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.VallaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.AsulaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.AsulaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.VaikekohaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.TanavaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.NimiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukoht.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="Elukoht.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "elukohtRiigiKood",
            "elukohtRiigiNimi",
            "elukohtMaakonnaKood",
            "elukohtMaakonnaNimi",
            "elukohtVallaKood",
            "elukohtVallaNimi",
            "elukohtAsulaKood",
            "elukohtAsulaNimi",
            "elukohtVaikekohaNimi",
            "elukohtTanavaNimi",
            "elukohtNimiNimi",
            "elukohtMajanr",
            "elukohtKorternr",
            "elukohtPostiindeks",
            "elukohtAlates",
            "elukohtAadressTekstina",
            "elukohtAdsAdrID",
            "elukohtAdsOid"
        })
        public static class Elukoht {

            @XmlElement(name = "Elukoht.RiigiKood", required = true)
            protected String elukohtRiigiKood;
            @XmlElement(name = "Elukoht.RiigiNimi", required = true)
            protected String elukohtRiigiNimi;
            @XmlElement(name = "Elukoht.MaakonnaKood", required = true)
            protected String elukohtMaakonnaKood;
            @XmlElement(name = "Elukoht.MaakonnaNimi", required = true)
            protected String elukohtMaakonnaNimi;
            @XmlElement(name = "Elukoht.VallaKood", required = true)
            protected String elukohtVallaKood;
            @XmlElement(name = "Elukoht.VallaNimi", required = true)
            protected String elukohtVallaNimi;
            @XmlElement(name = "Elukoht.AsulaKood", required = true)
            protected String elukohtAsulaKood;
            @XmlElement(name = "Elukoht.AsulaNimi", required = true)
            protected String elukohtAsulaNimi;
            @XmlElement(name = "Elukoht.VaikekohaNimi", required = true)
            protected String elukohtVaikekohaNimi;
            @XmlElement(name = "Elukoht.TanavaNimi", required = true)
            protected String elukohtTanavaNimi;
            @XmlElement(name = "Elukoht.NimiNimi", required = true)
            protected String elukohtNimiNimi;
            @XmlElement(name = "Elukoht.Majanr", required = true)
            protected String elukohtMajanr;
            @XmlElement(name = "Elukoht.Korternr", required = true)
            protected String elukohtKorternr;
            @XmlElement(name = "Elukoht.Postiindeks", required = true)
            protected String elukohtPostiindeks;
            @XmlElement(name = "Elukoht.Alates", required = true)
            protected String elukohtAlates;
            @XmlElement(name = "Elukoht.AadressTekstina", required = true)
            protected String elukohtAadressTekstina;
            @XmlElement(name = "Elukoht.AdsAdrID")
            protected int elukohtAdsAdrID;
            @XmlElement(name = "Elukoht.AdsOid", required = true)
            protected String elukohtAdsOid;

            /**
             * Gets the value of the elukohtRiigiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtRiigiKood() {
                return elukohtRiigiKood;
            }

            /**
             * Sets the value of the elukohtRiigiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtRiigiKood(String value) {
                this.elukohtRiigiKood = value;
            }

            /**
             * Gets the value of the elukohtRiigiNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtRiigiNimi() {
                return elukohtRiigiNimi;
            }

            /**
             * Sets the value of the elukohtRiigiNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtRiigiNimi(String value) {
                this.elukohtRiigiNimi = value;
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
             * Gets the value of the elukohtMaakonnaNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtMaakonnaNimi() {
                return elukohtMaakonnaNimi;
            }

            /**
             * Sets the value of the elukohtMaakonnaNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtMaakonnaNimi(String value) {
                this.elukohtMaakonnaNimi = value;
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
             * Gets the value of the elukohtVallaNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtVallaNimi() {
                return elukohtVallaNimi;
            }

            /**
             * Sets the value of the elukohtVallaNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtVallaNimi(String value) {
                this.elukohtVallaNimi = value;
            }

            /**
             * Gets the value of the elukohtAsulaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtAsulaKood() {
                return elukohtAsulaKood;
            }

            /**
             * Sets the value of the elukohtAsulaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtAsulaKood(String value) {
                this.elukohtAsulaKood = value;
            }

            /**
             * Gets the value of the elukohtAsulaNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtAsulaNimi() {
                return elukohtAsulaNimi;
            }

            /**
             * Sets the value of the elukohtAsulaNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtAsulaNimi(String value) {
                this.elukohtAsulaNimi = value;
            }

            /**
             * Gets the value of the elukohtVaikekohaNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtVaikekohaNimi() {
                return elukohtVaikekohaNimi;
            }

            /**
             * Sets the value of the elukohtVaikekohaNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtVaikekohaNimi(String value) {
                this.elukohtVaikekohaNimi = value;
            }

            /**
             * Gets the value of the elukohtTanavaNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtTanavaNimi() {
                return elukohtTanavaNimi;
            }

            /**
             * Sets the value of the elukohtTanavaNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtTanavaNimi(String value) {
                this.elukohtTanavaNimi = value;
            }

            /**
             * Gets the value of the elukohtNimiNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtNimiNimi() {
                return elukohtNimiNimi;
            }

            /**
             * Sets the value of the elukohtNimiNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtNimiNimi(String value) {
                this.elukohtNimiNimi = value;
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
             * Gets the value of the elukohtAadressTekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohtAadressTekstina() {
                return elukohtAadressTekstina;
            }

            /**
             * Sets the value of the elukohtAadressTekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohtAadressTekstina(String value) {
                this.elukohtAadressTekstina = value;
            }

            /**
             * Gets the value of the elukohtAdsAdrID property.
             * 
             */
            public int getElukohtAdsAdrID() {
                return elukohtAdsAdrID;
            }

            /**
             * Sets the value of the elukohtAdsAdrID property.
             * 
             */
            public void setElukohtAdsAdrID(int value) {
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
         *         &lt;element name="Sideaadress.RiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.RiigiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.MaakonnaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.VallaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.AsulaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.AsulaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.VaikekohaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.TanavaNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.NimiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.Alates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Sideaadress.AdsAdrID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="Sideaadress.AdsOid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "sideaadressRiigiKood",
            "sideaadressRiigiNimi",
            "sideaadressMaakonnaKood",
            "sideaadressMaakonnaNimi",
            "sideaadressVallaKood",
            "sideaadressVallaNimi",
            "sideaadressAsulaKood",
            "sideaadressAsulaNimi",
            "sideaadressVaikekohaNimi",
            "sideaadressTanavaNimi",
            "sideaadressNimiNimi",
            "sideaadressMajanr",
            "sideaadressKorternr",
            "sideaadressPostiindeks",
            "sideaadressAlates",
            "sideaadressAadressTekstina",
            "sideaadressAdsAdrID",
            "sideaadressAdsOid"
        })
        public static class Sideaadress {

            @XmlElement(name = "Sideaadress.RiigiKood", required = true)
            protected String sideaadressRiigiKood;
            @XmlElement(name = "Sideaadress.RiigiNimi", required = true)
            protected String sideaadressRiigiNimi;
            @XmlElement(name = "Sideaadress.MaakonnaKood", required = true)
            protected String sideaadressMaakonnaKood;
            @XmlElement(name = "Sideaadress.MaakonnaNimi", required = true)
            protected String sideaadressMaakonnaNimi;
            @XmlElement(name = "Sideaadress.VallaKood", required = true)
            protected String sideaadressVallaKood;
            @XmlElement(name = "Sideaadress.VallaNimi", required = true)
            protected String sideaadressVallaNimi;
            @XmlElement(name = "Sideaadress.AsulaKood", required = true)
            protected String sideaadressAsulaKood;
            @XmlElement(name = "Sideaadress.AsulaNimi", required = true)
            protected String sideaadressAsulaNimi;
            @XmlElement(name = "Sideaadress.VaikekohaNimi", required = true)
            protected String sideaadressVaikekohaNimi;
            @XmlElement(name = "Sideaadress.TanavaNimi", required = true)
            protected String sideaadressTanavaNimi;
            @XmlElement(name = "Sideaadress.NimiNimi", required = true)
            protected String sideaadressNimiNimi;
            @XmlElement(name = "Sideaadress.Majanr", required = true)
            protected String sideaadressMajanr;
            @XmlElement(name = "Sideaadress.Korternr", required = true)
            protected String sideaadressKorternr;
            @XmlElement(name = "Sideaadress.Postiindeks", required = true)
            protected String sideaadressPostiindeks;
            @XmlElement(name = "Sideaadress.Alates", required = true)
            protected String sideaadressAlates;
            @XmlElement(name = "Sideaadress.AadressTekstina", required = true)
            protected String sideaadressAadressTekstina;
            @XmlElement(name = "Sideaadress.AdsAdrID")
            protected int sideaadressAdsAdrID;
            @XmlElement(name = "Sideaadress.AdsOid", required = true)
            protected String sideaadressAdsOid;

            /**
             * Gets the value of the sideaadressRiigiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressRiigiKood() {
                return sideaadressRiigiKood;
            }

            /**
             * Sets the value of the sideaadressRiigiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressRiigiKood(String value) {
                this.sideaadressRiigiKood = value;
            }

            /**
             * Gets the value of the sideaadressRiigiNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressRiigiNimi() {
                return sideaadressRiigiNimi;
            }

            /**
             * Sets the value of the sideaadressRiigiNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressRiigiNimi(String value) {
                this.sideaadressRiigiNimi = value;
            }

            /**
             * Gets the value of the sideaadressMaakonnaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressMaakonnaKood() {
                return sideaadressMaakonnaKood;
            }

            /**
             * Sets the value of the sideaadressMaakonnaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressMaakonnaKood(String value) {
                this.sideaadressMaakonnaKood = value;
            }

            /**
             * Gets the value of the sideaadressMaakonnaNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressMaakonnaNimi() {
                return sideaadressMaakonnaNimi;
            }

            /**
             * Sets the value of the sideaadressMaakonnaNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressMaakonnaNimi(String value) {
                this.sideaadressMaakonnaNimi = value;
            }

            /**
             * Gets the value of the sideaadressVallaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressVallaKood() {
                return sideaadressVallaKood;
            }

            /**
             * Sets the value of the sideaadressVallaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressVallaKood(String value) {
                this.sideaadressVallaKood = value;
            }

            /**
             * Gets the value of the sideaadressVallaNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressVallaNimi() {
                return sideaadressVallaNimi;
            }

            /**
             * Sets the value of the sideaadressVallaNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressVallaNimi(String value) {
                this.sideaadressVallaNimi = value;
            }

            /**
             * Gets the value of the sideaadressAsulaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressAsulaKood() {
                return sideaadressAsulaKood;
            }

            /**
             * Sets the value of the sideaadressAsulaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressAsulaKood(String value) {
                this.sideaadressAsulaKood = value;
            }

            /**
             * Gets the value of the sideaadressAsulaNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressAsulaNimi() {
                return sideaadressAsulaNimi;
            }

            /**
             * Sets the value of the sideaadressAsulaNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressAsulaNimi(String value) {
                this.sideaadressAsulaNimi = value;
            }

            /**
             * Gets the value of the sideaadressVaikekohaNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressVaikekohaNimi() {
                return sideaadressVaikekohaNimi;
            }

            /**
             * Sets the value of the sideaadressVaikekohaNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressVaikekohaNimi(String value) {
                this.sideaadressVaikekohaNimi = value;
            }

            /**
             * Gets the value of the sideaadressTanavaNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressTanavaNimi() {
                return sideaadressTanavaNimi;
            }

            /**
             * Sets the value of the sideaadressTanavaNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressTanavaNimi(String value) {
                this.sideaadressTanavaNimi = value;
            }

            /**
             * Gets the value of the sideaadressNimiNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressNimiNimi() {
                return sideaadressNimiNimi;
            }

            /**
             * Sets the value of the sideaadressNimiNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressNimiNimi(String value) {
                this.sideaadressNimiNimi = value;
            }

            /**
             * Gets the value of the sideaadressMajanr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressMajanr() {
                return sideaadressMajanr;
            }

            /**
             * Sets the value of the sideaadressMajanr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressMajanr(String value) {
                this.sideaadressMajanr = value;
            }

            /**
             * Gets the value of the sideaadressKorternr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressKorternr() {
                return sideaadressKorternr;
            }

            /**
             * Sets the value of the sideaadressKorternr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressKorternr(String value) {
                this.sideaadressKorternr = value;
            }

            /**
             * Gets the value of the sideaadressPostiindeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressPostiindeks() {
                return sideaadressPostiindeks;
            }

            /**
             * Sets the value of the sideaadressPostiindeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressPostiindeks(String value) {
                this.sideaadressPostiindeks = value;
            }

            /**
             * Gets the value of the sideaadressAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressAlates() {
                return sideaadressAlates;
            }

            /**
             * Sets the value of the sideaadressAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressAlates(String value) {
                this.sideaadressAlates = value;
            }

            /**
             * Gets the value of the sideaadressAadressTekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressAadressTekstina() {
                return sideaadressAadressTekstina;
            }

            /**
             * Sets the value of the sideaadressAadressTekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressAadressTekstina(String value) {
                this.sideaadressAadressTekstina = value;
            }

            /**
             * Gets the value of the sideaadressAdsAdrID property.
             * 
             */
            public int getSideaadressAdsAdrID() {
                return sideaadressAdsAdrID;
            }

            /**
             * Sets the value of the sideaadressAdsAdrID property.
             * 
             */
            public void setSideaadressAdsAdrID(int value) {
                this.sideaadressAdsAdrID = value;
            }

            /**
             * Gets the value of the sideaadressAdsOid property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSideaadressAdsOid() {
                return sideaadressAdsOid;
            }

            /**
             * Sets the value of the sideaadressAdsOid property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSideaadressAdsOid(String value) {
                this.sideaadressAdsOid = value;
            }

        }

    }

}
