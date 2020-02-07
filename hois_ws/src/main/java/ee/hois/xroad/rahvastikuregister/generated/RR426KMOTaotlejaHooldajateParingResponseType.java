
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR426KMOTaotlejaHooldajateParingResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR426KMOTaotlejaHooldajateParingResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Hooldajad" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="HooldajaIsik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
 *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Kodakondsus" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Pereseis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="Elukoht" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                       &lt;element name="KehtibAlatesKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Hooldusoigused" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Liik"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                                 &lt;element name="Sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                                                 &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                                 &lt;element name="AlguseDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
 *                                                 &lt;element name="LopuDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
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
 *                   &lt;element name="HooldajaAsutus" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Liik"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                           &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                                 &lt;element name="Sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                                                 &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                                                 &lt;element name="AlguseDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
 *                                                 &lt;element name="LopuDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
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
 *                             &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
 *                             &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *                             &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "RR426KMOTaotlejaHooldajateParingResponseType", propOrder = {
    "hooldajad",
    "isikud"
})
public class RR426KMOTaotlejaHooldajateParingResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Hooldajad")
    protected RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad hooldajad;
    @XmlElement(name = "Isikud")
    protected RR426KMOTaotlejaHooldajateParingResponseType.Isikud isikud;

    /**
     * Gets the value of the hooldajad property.
     * 
     * @return
     *     possible object is
     *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad }
     *     
     */
    public RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad getHooldajad() {
        return hooldajad;
    }

    /**
     * Sets the value of the hooldajad property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad }
     *     
     */
    public void setHooldajad(RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad value) {
        this.hooldajad = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Isikud }
     *     
     */
    public RR426KMOTaotlejaHooldajateParingResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Isikud }
     *     
     */
    public void setIsikud(RR426KMOTaotlejaHooldajateParingResponseType.Isikud value) {
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
     *         &lt;element name="HooldajaIsik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
     *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Kodakondsus" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Pereseis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="Elukoht" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                             &lt;element name="KehtibAlatesKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Hooldusoigused" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Liik"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                       &lt;element name="Sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                                       &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                                       &lt;element name="AlguseDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
     *                                       &lt;element name="LopuDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
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
     *         &lt;element name="HooldajaAsutus" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Liik"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                                 &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                       &lt;element name="Sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                                       &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                                       &lt;element name="AlguseDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
     *                                       &lt;element name="LopuDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
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
        "hooldajaIsik",
        "hooldajaAsutus"
    })
    public static class Hooldajad {

        @XmlElement(name = "HooldajaIsik")
        protected List<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik> hooldajaIsik;
        @XmlElement(name = "HooldajaAsutus")
        protected List<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus> hooldajaAsutus;

        /**
         * Gets the value of the hooldajaIsik property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hooldajaIsik property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHooldajaIsik().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik }
         * 
         * 
         */
        public List<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik> getHooldajaIsik() {
            if (hooldajaIsik == null) {
                hooldajaIsik = new ArrayList<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik>();
            }
            return this.hooldajaIsik;
        }

        /**
         * Gets the value of the hooldajaAsutus property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hooldajaAsutus property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHooldajaAsutus().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus }
         * 
         * 
         */
        public List<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus> getHooldajaAsutus() {
            if (hooldajaAsutus == null) {
                hooldajaAsutus = new ArrayList<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus>();
            }
            return this.hooldajaAsutus;
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
         *         &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Liik"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                             &lt;element name="Sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
         *                             &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                             &lt;element name="AlguseDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
         *                             &lt;element name="LopuDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
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
            "nimetus",
            "hooldusoigused"
        })
        public static class HooldajaAsutus {

            @XmlElement(name = "Nimetus", required = true)
            protected String nimetus;
            @XmlElement(name = "Hooldusoigused")
            protected RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused hooldusoigused;

            /**
             * Gets the value of the nimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNimetus() {
                return nimetus;
            }

            /**
             * Sets the value of the nimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNimetus(String value) {
                this.nimetus = value;
            }

            /**
             * Gets the value of the hooldusoigused property.
             * 
             * @return
             *     possible object is
             *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused }
             *     
             */
            public RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused getHooldusoigused() {
                return hooldusoigused;
            }

            /**
             * Sets the value of the hooldusoigused property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused }
             *     
             */
            public void setHooldusoigused(RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused value) {
                this.hooldusoigused = value;
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
             *         &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Liik"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                   &lt;element name="Sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
             *                   &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *                   &lt;element name="AlguseDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
             *                   &lt;element name="LopuDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
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
                "hooldusoigus"
            })
            public static class Hooldusoigused {

                @XmlElement(name = "Hooldusoigus")
                protected List<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused.Hooldusoigus> hooldusoigus;

                /**
                 * Gets the value of the hooldusoigus property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the hooldusoigus property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getHooldusoigus().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused.Hooldusoigus }
                 * 
                 * 
                 */
                public List<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused.Hooldusoigus> getHooldusoigus() {
                    if (hooldusoigus == null) {
                        hooldusoigus = new ArrayList<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused.Hooldusoigus>();
                    }
                    return this.hooldusoigus;
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
                 *         &lt;element name="Liik"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
                 *         &lt;element name="Sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
                 *         &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
                 *         &lt;element name="AlguseDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
                 *         &lt;element name="LopuDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
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
                    "liik",
                    "sisu",
                    "alates",
                    "kuni",
                    "alguseDokument",
                    "lopuDokument"
                })
                public static class Hooldusoigus {

                    @XmlElement(name = "Liik", required = true)
                    protected RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused.Hooldusoigus.Liik liik;
                    @XmlElement(name = "Sisu")
                    protected String sisu;
                    @XmlElement(name = "Alates", required = true)
                    protected String alates;
                    @XmlElement(name = "Kuni")
                    protected String kuni;
                    @XmlElement(name = "AlguseDokument")
                    protected HooldusoiguseDokument alguseDokument;
                    @XmlElement(name = "LopuDokument")
                    protected HooldusoiguseDokument lopuDokument;

                    /**
                     * Gets the value of the liik property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused.Hooldusoigus.Liik }
                     *     
                     */
                    public RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused.Hooldusoigus.Liik getLiik() {
                        return liik;
                    }

                    /**
                     * Sets the value of the liik property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused.Hooldusoigus.Liik }
                     *     
                     */
                    public void setLiik(RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaAsutus.Hooldusoigused.Hooldusoigus.Liik value) {
                        this.liik = value;
                    }

                    /**
                     * Gets the value of the sisu property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSisu() {
                        return sisu;
                    }

                    /**
                     * Sets the value of the sisu property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSisu(String value) {
                        this.sisu = value;
                    }

                    /**
                     * Gets the value of the alates property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAlates() {
                        return alates;
                    }

                    /**
                     * Sets the value of the alates property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAlates(String value) {
                        this.alates = value;
                    }

                    /**
                     * Gets the value of the kuni property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getKuni() {
                        return kuni;
                    }

                    /**
                     * Sets the value of the kuni property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setKuni(String value) {
                        this.kuni = value;
                    }

                    /**
                     * Gets the value of the alguseDokument property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link HooldusoiguseDokument }
                     *     
                     */
                    public HooldusoiguseDokument getAlguseDokument() {
                        return alguseDokument;
                    }

                    /**
                     * Sets the value of the alguseDokument property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link HooldusoiguseDokument }
                     *     
                     */
                    public void setAlguseDokument(HooldusoiguseDokument value) {
                        this.alguseDokument = value;
                    }

                    /**
                     * Gets the value of the lopuDokument property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link HooldusoiguseDokument }
                     *     
                     */
                    public HooldusoiguseDokument getLopuDokument() {
                        return lopuDokument;
                    }

                    /**
                     * Sets the value of the lopuDokument property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link HooldusoiguseDokument }
                     *     
                     */
                    public void setLopuDokument(HooldusoiguseDokument value) {
                        this.lopuDokument = value;
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
                     *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                        "kood",
                        "nimetus"
                    })
                    public static class Liik {

                        @XmlElement(name = "Kood", required = true)
                        protected String kood;
                        @XmlElement(name = "Nimetus", required = true)
                        protected String nimetus;

                        /**
                         * Gets the value of the kood property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getKood() {
                            return kood;
                        }

                        /**
                         * Sets the value of the kood property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setKood(String value) {
                            this.kood = value;
                        }

                        /**
                         * Gets the value of the nimetus property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getNimetus() {
                            return nimetus;
                        }

                        /**
                         * Sets the value of the nimetus property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setNimetus(String value) {
                            this.nimetus = value;
                        }

                    }

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
         *         &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode" minOccurs="0"/&gt;
         *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Kodakondsus" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Pereseis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="Elukoht" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                   &lt;element name="KehtibAlatesKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Hooldusoigused" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Liik"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                       &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                             &lt;element name="Sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
         *                             &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *                             &lt;element name="AlguseDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
         *                             &lt;element name="LopuDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
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
            "isikuID",
            "isikukood",
            "eesnimi",
            "perenimi",
            "synniaeg",
            "sugu",
            "seisund",
            "kodakondsus",
            "pereseis",
            "synnikoht",
            "elukoht",
            "hooldusoigused"
        })
        public static class HooldajaIsik {

            @XmlElement(name = "IsikuID", required = true)
            protected BigInteger isikuID;
            @XmlElement(name = "Isikukood")
            protected String isikukood;
            @XmlElement(name = "Eesnimi", required = true)
            protected String eesnimi;
            @XmlElement(name = "Perenimi", required = true)
            protected String perenimi;
            @XmlElement(name = "Synniaeg", required = true)
            protected String synniaeg;
            @XmlElement(name = "Sugu", required = true)
            protected String sugu;
            @XmlElement(name = "Seisund", required = true)
            protected String seisund;
            @XmlElement(name = "Kodakondsus")
            protected RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Kodakondsus kodakondsus;
            @XmlElement(name = "Pereseis")
            protected String pereseis;
            @XmlElement(name = "Synnikoht")
            protected String synnikoht;
            @XmlElement(name = "Elukoht")
            protected RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Elukoht elukoht;
            @XmlElement(name = "Hooldusoigused")
            protected RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused hooldusoigused;

            /**
             * Gets the value of the isikuID property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getIsikuID() {
                return isikuID;
            }

            /**
             * Sets the value of the isikuID property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setIsikuID(BigInteger value) {
                this.isikuID = value;
            }

            /**
             * Gets the value of the isikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikukood() {
                return isikukood;
            }

            /**
             * Sets the value of the isikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikukood(String value) {
                this.isikukood = value;
            }

            /**
             * Gets the value of the eesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEesnimi() {
                return eesnimi;
            }

            /**
             * Sets the value of the eesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEesnimi(String value) {
                this.eesnimi = value;
            }

            /**
             * Gets the value of the perenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPerenimi() {
                return perenimi;
            }

            /**
             * Sets the value of the perenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPerenimi(String value) {
                this.perenimi = value;
            }

            /**
             * Gets the value of the synniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSynniaeg() {
                return synniaeg;
            }

            /**
             * Sets the value of the synniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSynniaeg(String value) {
                this.synniaeg = value;
            }

            /**
             * Gets the value of the sugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSugu() {
                return sugu;
            }

            /**
             * Sets the value of the sugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSugu(String value) {
                this.sugu = value;
            }

            /**
             * Gets the value of the seisund property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeisund() {
                return seisund;
            }

            /**
             * Sets the value of the seisund property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeisund(String value) {
                this.seisund = value;
            }

            /**
             * Gets the value of the kodakondsus property.
             * 
             * @return
             *     possible object is
             *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Kodakondsus }
             *     
             */
            public RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Kodakondsus getKodakondsus() {
                return kodakondsus;
            }

            /**
             * Sets the value of the kodakondsus property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Kodakondsus }
             *     
             */
            public void setKodakondsus(RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Kodakondsus value) {
                this.kodakondsus = value;
            }

            /**
             * Gets the value of the pereseis property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPereseis() {
                return pereseis;
            }

            /**
             * Sets the value of the pereseis property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPereseis(String value) {
                this.pereseis = value;
            }

            /**
             * Gets the value of the synnikoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSynnikoht() {
                return synnikoht;
            }

            /**
             * Sets the value of the synnikoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSynnikoht(String value) {
                this.synnikoht = value;
            }

            /**
             * Gets the value of the elukoht property.
             * 
             * @return
             *     possible object is
             *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Elukoht }
             *     
             */
            public RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Elukoht getElukoht() {
                return elukoht;
            }

            /**
             * Sets the value of the elukoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Elukoht }
             *     
             */
            public void setElukoht(RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Elukoht value) {
                this.elukoht = value;
            }

            /**
             * Gets the value of the hooldusoigused property.
             * 
             * @return
             *     possible object is
             *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused }
             *     
             */
            public RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused getHooldusoigused() {
                return hooldusoigused;
            }

            /**
             * Sets the value of the hooldusoigused property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused }
             *     
             */
            public void setHooldusoigused(RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused value) {
                this.hooldusoigused = value;
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
             *         &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *         &lt;element name="KehtibAlatesKp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
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
                "aadressTekstina",
                "postiindeks",
                "kehtibAlatesKp"
            })
            public static class Elukoht {

                @XmlElement(name = "AadressTekstina")
                protected String aadressTekstina;
                @XmlElement(name = "Postiindeks")
                protected String postiindeks;
                @XmlElement(name = "KehtibAlatesKp")
                protected String kehtibAlatesKp;

                /**
                 * Gets the value of the aadressTekstina property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAadressTekstina() {
                    return aadressTekstina;
                }

                /**
                 * Sets the value of the aadressTekstina property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAadressTekstina(String value) {
                    this.aadressTekstina = value;
                }

                /**
                 * Gets the value of the postiindeks property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPostiindeks() {
                    return postiindeks;
                }

                /**
                 * Sets the value of the postiindeks property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPostiindeks(String value) {
                    this.postiindeks = value;
                }

                /**
                 * Gets the value of the kehtibAlatesKp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKehtibAlatesKp() {
                    return kehtibAlatesKp;
                }

                /**
                 * Sets the value of the kehtibAlatesKp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKehtibAlatesKp(String value) {
                    this.kehtibAlatesKp = value;
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
             *         &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Liik"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                             &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                   &lt;element name="Sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
             *                   &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
             *                   &lt;element name="AlguseDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
             *                   &lt;element name="LopuDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
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
                "hooldusoigus"
            })
            public static class Hooldusoigused {

                @XmlElement(name = "Hooldusoigus")
                protected List<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused.Hooldusoigus> hooldusoigus;

                /**
                 * Gets the value of the hooldusoigus property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the hooldusoigus property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getHooldusoigus().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused.Hooldusoigus }
                 * 
                 * 
                 */
                public List<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused.Hooldusoigus> getHooldusoigus() {
                    if (hooldusoigus == null) {
                        hooldusoigus = new ArrayList<RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused.Hooldusoigus>();
                    }
                    return this.hooldusoigus;
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
                 *         &lt;element name="Liik"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                   &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
                 *         &lt;element name="Sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="Alates" type="{http://rr.x-road.eu/producer}date"/&gt;
                 *         &lt;element name="Kuni" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
                 *         &lt;element name="AlguseDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
                 *         &lt;element name="LopuDokument" type="{http://rr.x-road.eu/producer}HooldusoiguseDokument" minOccurs="0"/&gt;
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
                    "liik",
                    "sisu",
                    "alates",
                    "kuni",
                    "alguseDokument",
                    "lopuDokument"
                })
                public static class Hooldusoigus {

                    @XmlElement(name = "Liik", required = true)
                    protected RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused.Hooldusoigus.Liik liik;
                    @XmlElement(name = "Sisu")
                    protected String sisu;
                    @XmlElement(name = "Alates", required = true)
                    protected String alates;
                    @XmlElement(name = "Kuni")
                    protected String kuni;
                    @XmlElement(name = "AlguseDokument")
                    protected HooldusoiguseDokument alguseDokument;
                    @XmlElement(name = "LopuDokument")
                    protected HooldusoiguseDokument lopuDokument;

                    /**
                     * Gets the value of the liik property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused.Hooldusoigus.Liik }
                     *     
                     */
                    public RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused.Hooldusoigus.Liik getLiik() {
                        return liik;
                    }

                    /**
                     * Sets the value of the liik property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused.Hooldusoigus.Liik }
                     *     
                     */
                    public void setLiik(RR426KMOTaotlejaHooldajateParingResponseType.Hooldajad.HooldajaIsik.Hooldusoigused.Hooldusoigus.Liik value) {
                        this.liik = value;
                    }

                    /**
                     * Gets the value of the sisu property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSisu() {
                        return sisu;
                    }

                    /**
                     * Sets the value of the sisu property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSisu(String value) {
                        this.sisu = value;
                    }

                    /**
                     * Gets the value of the alates property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAlates() {
                        return alates;
                    }

                    /**
                     * Sets the value of the alates property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAlates(String value) {
                        this.alates = value;
                    }

                    /**
                     * Gets the value of the kuni property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getKuni() {
                        return kuni;
                    }

                    /**
                     * Sets the value of the kuni property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setKuni(String value) {
                        this.kuni = value;
                    }

                    /**
                     * Gets the value of the alguseDokument property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link HooldusoiguseDokument }
                     *     
                     */
                    public HooldusoiguseDokument getAlguseDokument() {
                        return alguseDokument;
                    }

                    /**
                     * Sets the value of the alguseDokument property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link HooldusoiguseDokument }
                     *     
                     */
                    public void setAlguseDokument(HooldusoiguseDokument value) {
                        this.alguseDokument = value;
                    }

                    /**
                     * Gets the value of the lopuDokument property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link HooldusoiguseDokument }
                     *     
                     */
                    public HooldusoiguseDokument getLopuDokument() {
                        return lopuDokument;
                    }

                    /**
                     * Sets the value of the lopuDokument property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link HooldusoiguseDokument }
                     *     
                     */
                    public void setLopuDokument(HooldusoiguseDokument value) {
                        this.lopuDokument = value;
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
                     *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                     *         &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                        "kood",
                        "nimetus"
                    })
                    public static class Liik {

                        @XmlElement(name = "Kood", required = true)
                        protected String kood;
                        @XmlElement(name = "Nimetus", required = true)
                        protected String nimetus;

                        /**
                         * Gets the value of the kood property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getKood() {
                            return kood;
                        }

                        /**
                         * Sets the value of the kood property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setKood(String value) {
                            this.kood = value;
                        }

                        /**
                         * Gets the value of the nimetus property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getNimetus() {
                            return nimetus;
                        }

                        /**
                         * Sets the value of the nimetus property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setNimetus(String value) {
                            this.nimetus = value;
                        }

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
             *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "kood",
                "nimetus"
            })
            public static class Kodakondsus {

                @XmlElement(name = "Kood", required = true)
                protected String kood;
                @XmlElement(name = "Nimetus", required = true)
                protected String nimetus;

                /**
                 * Gets the value of the kood property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKood() {
                    return kood;
                }

                /**
                 * Sets the value of the kood property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKood(String value) {
                    this.kood = value;
                }

                /**
                 * Gets the value of the nimetus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNimetus() {
                    return nimetus;
                }

                /**
                 * Sets the value of the nimetus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNimetus(String value) {
                    this.nimetus = value;
                }

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
     *         &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
     *                   &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
     *                   &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        protected List<RR426KMOTaotlejaHooldajateParingResponseType.Isikud.Isik> isik;

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
         * {@link RR426KMOTaotlejaHooldajateParingResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR426KMOTaotlejaHooldajateParingResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR426KMOTaotlejaHooldajateParingResponseType.Isikud.Isik>();
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
         *         &lt;element name="IsikuID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="Isikukood" type="{http://rr.x-road.eu/producer}PersonalCode"/&gt;
         *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
         *         &lt;element name="Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Seisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isaeesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "isikuID",
            "isikukood",
            "eesnimi",
            "perenimi",
            "synniaeg",
            "sugu",
            "seisund",
            "isaeesnimi"
        })
        public static class Isik {

            @XmlElement(name = "IsikuID", required = true)
            protected BigInteger isikuID;
            @XmlElement(name = "Isikukood", required = true)
            protected String isikukood;
            @XmlElement(name = "Eesnimi", required = true)
            protected String eesnimi;
            @XmlElement(name = "Perenimi", required = true)
            protected String perenimi;
            @XmlElement(name = "Synniaeg")
            protected String synniaeg;
            @XmlElement(name = "Sugu", required = true)
            protected String sugu;
            @XmlElement(name = "Seisund", required = true)
            protected String seisund;
            @XmlElement(name = "Isaeesnimi")
            protected String isaeesnimi;

            /**
             * Gets the value of the isikuID property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getIsikuID() {
                return isikuID;
            }

            /**
             * Sets the value of the isikuID property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setIsikuID(BigInteger value) {
                this.isikuID = value;
            }

            /**
             * Gets the value of the isikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikukood() {
                return isikukood;
            }

            /**
             * Sets the value of the isikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikukood(String value) {
                this.isikukood = value;
            }

            /**
             * Gets the value of the eesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEesnimi() {
                return eesnimi;
            }

            /**
             * Sets the value of the eesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEesnimi(String value) {
                this.eesnimi = value;
            }

            /**
             * Gets the value of the perenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPerenimi() {
                return perenimi;
            }

            /**
             * Sets the value of the perenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPerenimi(String value) {
                this.perenimi = value;
            }

            /**
             * Gets the value of the synniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSynniaeg() {
                return synniaeg;
            }

            /**
             * Sets the value of the synniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSynniaeg(String value) {
                this.synniaeg = value;
            }

            /**
             * Gets the value of the sugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSugu() {
                return sugu;
            }

            /**
             * Sets the value of the sugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSugu(String value) {
                this.sugu = value;
            }

            /**
             * Gets the value of the seisund property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeisund() {
                return seisund;
            }

            /**
             * Sets the value of the seisund property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeisund(String value) {
                this.seisund = value;
            }

            /**
             * Gets the value of the isaeesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsaeesnimi() {
                return isaeesnimi;
            }

            /**
             * Sets the value of the isaeesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsaeesnimi(String value) {
                this.isaeesnimi = value;
            }

        }

    }

}
