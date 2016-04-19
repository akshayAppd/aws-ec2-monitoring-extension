package com.appdynamics.extensions.aws.ec2.config;

import com.appdynamics.extensions.aws.config.Configuration;

/**
 * @author Florencio Sarmiento
 *
 */
public class EC2Configuration extends Configuration {
	
	private Ec2InstanceNameConfig ec2InstanceNameConfig;
	private String ec2Instance;

	public Ec2InstanceNameConfig getEc2InstanceNameConfig() {
		return ec2InstanceNameConfig;
	}

	public void setEc2InstanceNameConfig(Ec2InstanceNameConfig ec2InstanceNameConfig) {
		this.ec2InstanceNameConfig = ec2InstanceNameConfig;
	}

	public String getEc2Instance() {
		return ec2Instance;
	}

	public void setEc2Instance(String ec2Instance) {
		this.ec2Instance = ec2Instance;
	}
}
