package org.owasp.esapi.validation;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;


public class ConfigurationSupportTest {
	@Test
	public void validateSchemaSupport() throws Exception {
		InputStream xsd = ConfigurationSupportTest.class.getResourceAsStream("/esapi-validation-lib.xsd");
		InputStream xml = ConfigurationSupportTest.class.getResourceAsStream("/valid_app.xml");
		
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(new StreamSource(xsd));
		javax.xml.validation.Validator validator = schema.newValidator();
		validator.validate(new StreamSource(xml));
	}
	
	@Test
	public void testJAXBUnmarshall() throws Exception {
	/*	JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		Esapi validationConfig = (Esapi) unmarshaller.unmarshal(ESAPIValidation.class.getResourceAsStream("/valid_app.xml"));		
		Assert.assertEquals(5, validationConfig.getValidators().getValidator().size());	*/	
	}
}
