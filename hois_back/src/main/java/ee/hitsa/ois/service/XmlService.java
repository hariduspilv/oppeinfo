package ee.hitsa.ois.service;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Service;

@Service
public class XmlService {
    
    public <T> byte[] generateFromObject(T object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        m.marshal(object, outputStream);
        return outputStream.toByteArray();
    }
}
