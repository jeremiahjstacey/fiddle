package org.owasp.esapi.validation.io;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

public class IsDirectoryValidatorTest {
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	private IsDirectoryValidator testInstance = new IsDirectoryValidator();

	@Test
	public void testFile() throws IOException {
		File file = folder.newFile("junitTestFile.jtf");
		ValidationResponse response = testInstance.validate(file);
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());
		Assert.assertTrue(response.getResponseDetail().contains("not a directory"));
	}
	@Test
	public void testDirectory() throws IOException {
		File file = folder.newFolder();
		ValidationResponse response = testInstance.validate(file);
		Assert.assertEquals(ValidationResponse.OK, response);
	}
	
}
