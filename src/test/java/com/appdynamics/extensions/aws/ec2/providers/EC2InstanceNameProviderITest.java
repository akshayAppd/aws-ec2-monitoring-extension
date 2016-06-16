package com.appdynamics.extensions.aws.ec2.providers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.appdynamics.extensions.aws.ec2.config.Tag;
import org.junit.Test;

import com.appdynamics.extensions.aws.config.Account;
import com.appdynamics.extensions.aws.config.CredentialsDecryptionConfig;
import com.appdynamics.extensions.aws.config.ProxyConfig;
import com.appdynamics.extensions.aws.ec2.config.Ec2InstanceNameConfig;

public class EC2InstanceNameProviderITest {
	
	@Test
	public void testInstanceIdIsReturnedWhenUseNameInMetricsIsFalse() throws Exception {
		EC2InstanceNameProvider classUnderTest = EC2InstanceNameProvider.getInstance();
		
		Account testAccount = getTestAccount();
		
		Ec2InstanceNameConfig testEc2InstanceNameConfig = getTestEc2InstanceNameConfig();
		testEc2InstanceNameConfig.setUseNameInMetrics("false");

		List<Tag> tags = getTestTags();
		
		CredentialsDecryptionConfig testCredentialsConfig = getTestCredentialsConfig();
		
		classUnderTest.initialise(Arrays.asList(testAccount), 
				testCredentialsConfig, 
				new ProxyConfig(), 
				testEc2InstanceNameConfig,
				tags, 3);
		
		String testInstanceId = "myTestInstance";
		
		String result = classUnderTest.getInstanceName(testAccount.getDisplayAccountName(), 
				"us-east-1", testInstanceId);
		
		assertEquals(testInstanceId, result);
	}



	@Test
	public void testInstanceNameIsReturnedWhenUseNameInMetricsIsFalse() throws Exception {
		EC2InstanceNameProvider classUnderTest = EC2InstanceNameProvider.getInstance();
		
		Account testAccount = getTestAccount();
		Ec2InstanceNameConfig testEc2InstanceNameConfig = getTestEc2InstanceNameConfig();
		CredentialsDecryptionConfig testCredentialsConfig = getTestCredentialsConfig();
		List<Tag> tags = getTestTags();
		
		classUnderTest.initialise(Arrays.asList(testAccount), 
				testCredentialsConfig, 
				new ProxyConfig(), 
				testEc2InstanceNameConfig,
				tags, 3);
		
		String testInstanceId = "i-68792461";
		
		String result = classUnderTest.getInstanceName(testAccount.getDisplayAccountName(), 
				"us-east-1", testInstanceId);
		
		assertNotEquals(testInstanceId, result);
	}
	
	private Account getTestAccount() {
		Account account = new Account();
		account.setAwsAccessKey("Nw5XVvMq+xpUGs1kGS9MP3Vv3IUUCaMH9ZE90zvJ9vE=");
		account.setAwsSecretKey("3W+gxGX8xP/F72fu2UJDKSl2tanWH7F8/Lv8VD3ap6vkVJlIPOuOtyp6rfTgxHpC");
		account.setDisplayAccountName("AppDAccount");
		account.setRegions(new HashSet<String>(Arrays.asList("us-east-1", "us-west-2", "us-west-1")));
		return account;
	}
	
	private Ec2InstanceNameConfig getTestEc2InstanceNameConfig() {
		Ec2InstanceNameConfig ec2InstanceNameConfig = new Ec2InstanceNameConfig();
		ec2InstanceNameConfig.setUseNameInMetrics("true");
		ec2InstanceNameConfig.setTagFilterName("tag-key");
		ec2InstanceNameConfig.setTagKey("Name");
		return ec2InstanceNameConfig;
	}
	
	private CredentialsDecryptionConfig getTestCredentialsConfig() {
		CredentialsDecryptionConfig credentialsDecryptionConfig = new CredentialsDecryptionConfig();
		credentialsDecryptionConfig.setEnableDecryption("true");
		credentialsDecryptionConfig.setDecryptionKey("test");
		return credentialsDecryptionConfig;
	}

	private List<Tag> getTestTags() {

		List<Tag> tags = new ArrayList<Tag>();

		return tags;
	}
}
