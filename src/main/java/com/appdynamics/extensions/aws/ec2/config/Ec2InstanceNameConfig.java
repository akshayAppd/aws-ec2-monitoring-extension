package com.appdynamics.extensions.aws.ec2.config;

/**
 * @author Florencio Sarmiento
 *
 */
public class Ec2InstanceNameConfig {
	
	private String useNameInMetrics;
	
	private String tagFilterName;
	
	private String tagKey;
	
	public boolean isUseInstanceName() {
		return Boolean.valueOf(getUseNameInMetrics());
	}

	public String getUseNameInMetrics() {
		return useNameInMetrics;
	}

	public void setUseNameInMetrics(String useNameInMetrics) {
		this.useNameInMetrics = useNameInMetrics;
	}

	public String getTagFilterName() {
		return tagFilterName;
	}

	public void setTagFilterName(String tagFilterName) {
		this.tagFilterName = tagFilterName;
	}

	public String getTagKey() {
		return tagKey;
	}

	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

}
