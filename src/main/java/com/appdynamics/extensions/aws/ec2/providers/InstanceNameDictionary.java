package com.appdynamics.extensions.aws.ec2.providers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Florencio Sarmiento
 *
 */
public class InstanceNameDictionary {
	
	private Map<String, String> ec2Instaces = new ConcurrentHashMap<String, String>();
	
	public Map<String, String> getEc2Instaces() {
		return ec2Instaces;
	}

	public void setEc2Instaces(Map<String, String> ec2Instaces) {
		this.ec2Instaces = new ConcurrentHashMap<String, String>(ec2Instaces);
	}
	
	public void addEc2Instance(String instanceId, String instanceName) {
		this.ec2Instaces.put(instanceId, instanceName);
	}
}
