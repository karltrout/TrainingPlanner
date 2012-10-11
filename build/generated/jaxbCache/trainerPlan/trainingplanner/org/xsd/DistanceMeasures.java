//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.11 at 03:35:25 PM EDT 
//


package trainingplanner.org.xsd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for distanceMeasures.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="distanceMeasures">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="kilometer"/>
 *     &lt;enumeration value="meter"/>
 *     &lt;enumeration value="mile"/>
 *     &lt;enumeration value="yard"/>
 *     &lt;enumeration value="feet"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "distanceMeasures")
@XmlEnum
public enum DistanceMeasures {

    @XmlEnumValue("kilometer")
    KILOMETER("kilometer"),
    @XmlEnumValue("meter")
    METER("meter"),
    @XmlEnumValue("mile")
    MILE("mile"),
    @XmlEnumValue("yard")
    YARD("yard"),
    @XmlEnumValue("feet")
    FEET("feet");
    private final String value;

    DistanceMeasures(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DistanceMeasures fromValue(String v) {
        for (DistanceMeasures c: DistanceMeasures.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
