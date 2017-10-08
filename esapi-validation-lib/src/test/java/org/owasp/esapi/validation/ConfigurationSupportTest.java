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
		Assert.assertEquals(SampleValidationConfiguration.class.getName(), validationConfig.getValidationProvider().getClazz());	
	}
	
	@Test
	public void testBasicRunthrough() throws Exception {
		ESAPIValidation ev = ESAPIValidation.forConfiguration("/valid_app.xml");
		
		TestRef test = new TestRef();
		Check notNullCheck = TestRef.class.getDeclaredField("value").getDeclaredAnnotation(Check.class);
		Check matchAnyCheck = TestRef.class.getDeclaredField("hasContent").getDeclaredAnnotation(Check.class);
		Check isNullCheck = TestRef.class.getDeclaredField("value2").getDeclaredAnnotation(Check.class);
		Check notEmptyCheck = TestRef.class.getDeclaredField("value3").getDeclaredAnnotation(Check.class);
		
		
		Assert.assertFalse(ev.check(notNullCheck, test.value).isValid());
		test.value = "";
		Assert.assertTrue(ev.check(notNullCheck, test.value).isValid());
		
		Assert.assertTrue(ev.check(isNullCheck, test.value2).isValid());
		test.value3="data";
		Assert.assertTrue(ev.check(notEmptyCheck, test.value3).isValid());
		//This doesn't work.  Need to fight with primitives in the ctr arg list.
		ValidationResponse response = ev.check(matchAnyCheck, test.hasContent); 
		Assert.assertTrue(response.getResponseDetail(), response.isValid());
		
		
		Check classCheck = TestRef.class.getDeclaredAnnotation(Check.class);
		TestRef classRefTest = null;
		Assert.assertFalse(ev.check(classCheck, classRefTest).isValid());
		
		classRefTest = new TestRef();
		classRefTest.value = null;
		
		Assert.assertFalse(ev.check(classCheck, classRefTest).isValid());
		
		classRefTest.value = "";
		Assert.assertFalse(ev.check(classCheck, classRefTest).isValid());
		
		classRefTest.value = "valid";
		Assert.assertTrue(ev.check(classCheck, classRefTest).isValid());
	}
	
	
}
