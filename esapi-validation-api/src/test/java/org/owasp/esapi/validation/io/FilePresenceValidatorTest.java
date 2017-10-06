package org.owasp.esapi.validation.io;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

public class FilePresenceValidatorTest {
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	private FilePresenceValidator testInstance = new FilePresenceValidator();

	@Test
	public void testFileExists() throws IOException {
		File file = folder.newFile("junitTestFile.jtf");
		ValidationResponse response = testInstance.validate(file);
		Assert.assertEquals(ValidationResponse.OK, response);
	}
	@Test
	public void testFileDoesNotExist() throws IOException {
		File file = folder.newFile("junitTestFile.jtf");
		file.delete();
		ValidationResponse response = testInstance.validate(file);
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());
		Assert.assertTrue(response.getResponseDetail().contains("location does not exist"));
	}
	@Test
	public void testDirectoryExists() throws IOException {
		File file = folder.newFolder();
		ValidationResponse response = testInstance.validate(file);
		Assert.assertEquals(ValidationResponse.OK, response);
	}
	@Test
	public void testDirectoryDoesNotExist() throws IOException {
		File file = folder.newFolder();
		file.delete();
		ValidationResponse response = testInstance.validate(file);
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());
		Assert.assertTrue(response.getResponseDetail().contains("location does not exist"));
	}
}
