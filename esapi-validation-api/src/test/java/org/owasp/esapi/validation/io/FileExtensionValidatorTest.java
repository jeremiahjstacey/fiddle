package org.owasp.esapi.validation.io;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;



public class FileExtensionValidatorTest {
	@Rule
	  public TemporaryFolder folder= new TemporaryFolder();
	
	@Test
	public void testExtensionMatchCaseSensitive() throws IOException {
		FileExtensionValidator validator = new FileExtensionValidator(".jtf", true);
		File file = folder.newFile("junitTestFile.jtf");
		ValidationResponse reponse = validator.validate(file);
		Assert.assertEquals(ValidationResponse.OK, reponse);
	}
	
	@Test
	public void testExtensionNonMatchCaseSensitive() throws IOException {
		FileExtensionValidator validator = new FileExtensionValidator(".jtf", true);
		File file = folder.newFile("junitTestFile.JTF");
		ValidationResponse reponse = validator.validate(file);
		Assert.assertEquals(ValidationStatus.FAIL, reponse.getResponseStatus());
		Assert.assertTrue(reponse.getResponseDetail().contains("does not match the desired extension"));
	}
	
	@Test
	public void testUnmatchedExtension() throws IOException {
		FileExtensionValidator validator = new FileExtensionValidator(".jtf", true);
		File file = folder.newFile("junitTestFile.file");
		ValidationResponse reponse = validator.validate(file);
		Assert.assertEquals(ValidationStatus.FAIL, reponse.getResponseStatus());
		Assert.assertTrue(reponse.getResponseDetail().contains("does not match the desired extension"));
	}
	
	@Test
	public void testExtensionMatchCaseInsensitive() throws IOException {
		FileExtensionValidator validator = new FileExtensionValidator(".jtf", false);
		File file = folder.newFile("junitTestFile.jtf");
		ValidationResponse reponse = validator.validate(file);
		Assert.assertEquals(ValidationResponse.OK, reponse);
	}
	
	@Test
	public void testExtensionNonMatchCaseInsensitive() throws IOException {
		FileExtensionValidator validator = new FileExtensionValidator(".jtf", false);
		File file = folder.newFile("junitTestFile.JTF");
		ValidationResponse reponse = validator.validate(file);
		Assert.assertEquals(ValidationResponse.OK, reponse);
	}
}
