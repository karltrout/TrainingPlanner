//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.09.18 at 10:01:31 PM EDT 
//


package trainingplanner.org.xsd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for measureTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="measureTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="speed"/>
 *     &lt;enumeration value="pace"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "measureTypes")
@XmlEnum
public enum MeasureTypes {

    @XmlEnumValue("speed")
    SPEED("speed"),
    @XmlEnumValue("pace")
    PACE("pace");
    private final String value;

    MeasureTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MeasureTypes fromValue(String v) {
        for (MeasureTypes c: MeasureTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
