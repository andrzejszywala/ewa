package pl.ewa.business.exchange.control;


import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import pl.ewa.business.exchange.entity.TabelaKursow;

public class JAXBUtils {

    public static Object xmlToObject(String xml) {
        JAXBContext context;
        StringReader sr = new StringReader(xml);
        try {
            context = JAXBContext.newInstance(TabelaKursow.class);
            return context.createUnmarshaller().unmarshal(sr);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <T> String objToXml(JAXBElement<T> obj) {
        JAXBContext context;
        StringWriter sw = new StringWriter();
        try {
            context = JAXBContext.newInstance(obj.getDeclaredType().getPackage().getName());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return sw.toString();
    }
}
