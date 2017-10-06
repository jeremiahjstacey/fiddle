package org.owasp.esapi.validation;

import java.io.File;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Assert;
import org.junit.Test;
import org.owasp.esapi.validation.annotation.Check;
import org.owasp.esapi.validation.jaxb.generated.Esapi;
import org.owasp.esapi.validation.jaxb.generated.ObjectFactory;


public class ConfigurationSupportTest {
	@Test
	public void validateSchemaSupport() throws Exception {
		File xsd = new File("./src/main/xsd/esapi-validation-lib.xsd");
		Assert.assertTrue(xsd.exists());

		InputStream xml = ConfigurationSupportTest.class.getResourceAsStream("/valid_app.xml");
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(new StreamSource(xsd));
		javax.xml.validation.Validator validator = schema.newValidator();
		validator.validate(new StreamSource(xml));
	}
	
	@Test
	public void testJAXBUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		Esapi validationConfig = (Esapi) unmarshaller.unmarshal(ESAPIValidation.class.getResourceAsStream("/valid_app.xml"));		
		Assert.assertEquals(5, validationConfig.getValidators().getValidator().size());	
	}
	
	@Test
	public void testBasicRunthrough() throws Exception {
		ESAPIValidation ev = ESAPIValidation.forConfiguration("/valid_app.xml");
		
		TestRef test = new TestRef();
		Check notNullCheck = TestRef.class.getDeclaredField("value").getDeclaredAnnotation(Check.class);
		Check matchAnyCheck = TestRef.class.getDeclaredField("hasContent").getDeclaredAnnotation(Check.class);
		
		Assert.assertFalse(ev.check(notNullCheck, test.value).isValid());
		test.value = "";
		Assert.assertTrue(ev.check(notNullCheck, test.value).isValid());
		
		//This doesn't work.  Need to fight with primitives in the ctr arg list.
		//Assert.assertTrue(ev.check(matchAnyCheck, test.hasContent).isValid());
		
	}
	
	public class TestRef {
		@Check(rule="NOT_NULL")
		String value;
		
		@Check(rule="STR_Match_ANY")
		String hasContent = "hello world";
	}
}
