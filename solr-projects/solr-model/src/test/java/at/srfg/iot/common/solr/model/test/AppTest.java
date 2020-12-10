package at.srfg.iot.common.solr.model.test;

import java.util.Iterator;

import at.srfg.iot.common.solr.model.model.solr.FacetResult.Entry;
import at.srfg.iot.common.solr.model.model.solr.SearchResult;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
//    public void testItemProperties()
//    {
////    	Concept c = Concept.buildNew();
////    	c.addProperty(10.0, "volumen","liter");
//    	
//    	ItemType item = new ItemType();
//    	// add to stringValue with key "property" and "LangString"
//    	item.addMultiLingualProperty("Prop EN", "en", "English Property");
//    	item.addMultiLingualProperty("Prop DE", "de", "Deutsch Eigenschaft");
//    	item.addProperty("prop y2", "m", 2.0);
//    	item.addProperty("prop y2", "cm", 200.0);
//    	assertTrue(item.getStringValue().get("Prop EN").contains("English Property@en"));
//    	assertTrue(item.getStringValue().get("Prop DE").contains("Deutsch Eigenschaft@de"));
//    	assertTrue(item.getCustomPropertyKeys().containsValue("prop y2@m"));
//    	assertTrue(item.getCustomPropertyKeys().containsValue("prop y2@cm"));
//    	
//    }
//    public void testCustomPropertyAware() {
//    	PartyType pt = new PartyType();
//    	pt.setLabel("Manufacturer", Locale.ENGLISH);
//    	pt.setLabel("hersteller", Locale.GERMAN);
//    	pt.addMultiLingualProperty("Manufacturer", Locale.ENGLISH, "brand");
//    	pt.addMultiLingualProperty("Hersteller", Locale.GERMAN, "brand");
//    	pt.addProperty(23.5, "length", "cm");
//    	PropertyType pt2 = new PropertyType();
//    	pt2.setUri("urn:test:property1");
//    	pt2.setLabel("Trust-Score", Locale.ENGLISH);
//    	pt2.setLabel("Vertrauenspunkte", Locale.GERMAN);
//    	pt2.setValueQualifier(ValueQualifier.NUMBER);
//    	pt.addProperty(5.0, pt2, "trust", "score");
//    	assertTrue(pt.getMultiLingualProperties(Locale.ENGLISH, "brand").contains("Manufacturer"));
//    	assertTrue(pt.getMultiLingualProperties(Locale.GERMAN, "brand").contains("Hersteller"));
//    	assertTrue(pt.getCustomPropertyKeys().containsValue("length@cm"));
//    	assertTrue(pt.getCustomPropertyKeys().containsKey("lengthCm"));
//    	
//    	assertTrue(pt.getDoublePropertyValues("length", "cm").contains(23.5));
//    	assertTrue(pt.getCustomProperties().containsKey("trustScore"));
//    	assertTrue(pt.getCustomPropertyKeys().containsValue("trust@score"));
//    }
    
    public void testSorting() {
    	SearchResult<String> result = new SearchResult<>();
    	result.addFacet("text", "XYZ", 2);
    	result.addFacet("text", "Fourth", 1);
    	result.addFacet("text", "First", 10);
    	result.addFacet("text", "ABC", 2);
    	Iterator<Entry> set = result.getFacets().get("text").getEntry().iterator();
    	Entry first = set.next();
    	assertTrue("First".equals(first.getLabel()));
    	Entry second = set.next();
    	assertTrue("ABC".equals(second.getLabel()));
    }
    
}
