/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingplanner;

/**
 *
 * @author Karl
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
//import trainingplanner.org.xsd.SportTypes;

public class ExersizeDataBase {
    private JAXBContext jc;
    private String dataBaseFileLocation;
    //private SportTypes sportsType;
    
    ExersizeDataBase( final String  packageName, final String xmlFileLocation) throws IOException{
            dataBaseFileLocation = xmlFileLocation;
        
        try{
            jc = JAXBContext.newInstance( packageName );
        }catch (JAXBException ex) {
            Logger.getLogger(TrainingPlannerWindowController.class.getName()).log(Level.SEVERE, null, ex);  
            jc = null;
        }
        
    }
     /**
    * This method saves (serializes) any java bean object into xml file
    */
    public void serializeObjectToXML(Object objectToSerialize) throws Exception {
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        try (FileOutputStream os = new FileOutputStream(dataBaseFileLocation)) {
            m.marshal( objectToSerialize, os);
        }
    }

    /**
     * Reads Java Bean Object From XML File
     */
    public Object deserializeXMLTodataBase() throws Exception {
        FileInputStream is = new FileInputStream(dataBaseFileLocation);
        Unmarshaller u = jc.createUnmarshaller();
        Object database = u.unmarshal(is);
        return database;
    }
    
}
