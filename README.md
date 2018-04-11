# AWS EC2 Monitoring Extension

## Use Case
Captures EC2 instances statistics from Amazon CloudWatch and displays them in the AppDynamics Metric Browser.

**Note : By default, the Machine agent can only send a fixed number of metrics to the controller. This extension potentially reports thousands of metrics, so to change this limit, please follow the instructions mentioned [here](https://docs.appdynamics.com/display/PRO40/Metrics+Limits).**

## Installation

1. Run 'mvn clean install' from aws-ec2-monitoring-extension
2. Copy and unzip AWSEC2Monitor-\<version\>.zip from 'target' directory into \<machine_agent_dir\>/monitors/
3. Edit config.yaml file in AWSEC2Monitor/conf and provide the required configuration (see Configuration section)
4. Restart the Machine Agent.

## Configuration

### config.yaml

**Note: Please avoid using tab (\t) when editing yaml files. You may want to validate the yaml file using a [yaml validator](http://yamllint.com/).**

| Section | Fields | Description | Example |
| ----- | ----- | ----- | ----- |
| **accounts** | | Fields under this section can be repeated for multiple accounts config |  |
| | awsAccessKey | AWS Access Key, keep it empty if using instance profile |  |
| | awsSecretKey | AWS Secret Key, keep it empty if using instance profile |  |
| | displayAccountName | Display name used in metric path | "MyAWSEC2" |
| | regions | Regions where ec2 is registered | **Allowed values:**<br/>"ap-southeast-1",<br/>"ap-southeast-2",<br/>"ap-northeast-1",<br/>"eu-central-1",<br/>"eu-west-1",<br/>"us-east-1",<br/>"us-west-1",<br/>"us-west-2",<br/>"sa-east-1" |
| **credentialsDecryptionConfig** | ----- | ----- | ----- |
| | enableDecryption | If set to "true", then all aws credentials provided (access key and secret key) will be decrypted - see AWS Credentials Encryption section |  |
| | decryptionKey | The key used when encypting the credentials |  |
| **proxyConfig** | ----- | ----- | ----- |
| | host | The proxy host (must also specify port) |  |
| | port | The proxy port (must also specify host) |  |
| | username | The proxy username (optional)  |  |
| | password | The proxy password (optional)  |  |
| **ec2InstanceNameConfig** | ----- | ----- | ----- |
| | useNameInMetrics | Set to "true" if you wish to display the instance name rather than  instance Id in the metric browser. Note, name must be configured in your EC2 instance. |  |
| | tagFilterName | **Do not change.** Internal setting to retrieve instance name. |  |
| | tagKey | **Do not change.** Internal setting to retrieve instance name.  |  |
| **metricsConfig** | ----- | ----- | ----- |
| metricTypes | | Fields under this section can be repeated for multiple metric types override |  |
| | metricName | The metric name | "CPUUtilization" |
| | statType | The statistic type | **Allowed values:**<br/>"ave"<br/>"max"<br/>"min"<br/>"sum"<br/>"samplecount" |
| | ----- | ----- | ----- |
| | excludeMetrics | Metrics to exclude - supports regex | "CPUUtilization",<br/>"Swap.*" |
| metricsTimeRange |  |  |  |
| | startTimeInMinsBeforeNow | The no of mins to deduct from current time for start time of query | 5 |
| | endTimeInMinsBeforeNow | The no of mins to deduct from current time for end time of query.<br>Note, this must be less than startTimeInMinsBeforeNow | 0 |
| | ----- | ----- | ----- |
| | maxErrorRetrySize | The max number of retry attempts for failed retryable requests | 1 |
| **concurrencyConfig** |  |  |  |
| | noOfAccountThreads | The no of threads to process multiple accounts concurrently | 3 |
| | noOfRegionThreadsPerAccount | The no of threads to process multiple regions per account concurrently | 3 |
| | noOfMetricThreadsPerRegion | The no of threads to process multiple metrics per region concurrently | 3 |
| | ----- | ----- | ----- |
| | metricPrefix | The path prefix for viewing metrics in the metric browser. | "Custom Metrics\|Amazon EC2\|" |


**Below is an example config for monitoring multiple accounts and regions:**

~~~
accounts:
  - awsAccessKey: "XXXXXXXX1"
    awsSecretKey: "XXXXXXXXXX1"
    displayAccountName: "TestAccount_1"
    regions: ["us-east-1","us-west-1","us-west-2"]

  - awsAccessKey: "XXXXXXXX2"
    awsSecretKey: "XXXXXXXXXX2"
    displayAccountName: "TestAccount_2"
    regions: ["eu-central-1","eu-west-1"]

credentialsDecryptionConfig:
    enableDecryption: "false"
    decryptionKey:
    
proxyConfig:
    host:
    port:
    username:
    password:
    
ec2InstanceNameConfig:
    useNameInMetrics: "true"
    tagFilterName: "tag-key"
    tagKey: "Name"

#EC2 instance for which you want to get the metrics. This is useful when you want to monitor only 1 instance. Keep this empty if you want to monitor all the available instances.
#Only 1 instance id is acceptable here as AWS CloudWatch API does not support multiple dimension values for the dimension
ec2Instance: ""

#Filter the instances by tag name. tagValue is optional. if tagValue is not provided, we will fetch all the instances with the tagName.
tags:
  - name: "Name"
    #value: ["tag1", "tag2", "tag3"]

# Global metrics config for all accounts
metricsConfig:

    # By default, all metrics retrieved from cloudwatch are 'Average' values.	
    # This option allows you to override the metric type. 
    #
    # Allowed statTypes are: ave, max, min, sum, samplecount
    #
    # Note: Irrespective of the metric type, value will still be reported as
    # Observed value to the Controller
    includeMetrics:
       - name: "CPUUtilization"
         alias: "CPUUtilization"
         statType: "ave"
         aggregationType: "OBSERVATION"
         timeRollUpType: "CURRENT"
         clusterRollUpType: "COLLECTIVE"
         delta: false
         multiplier: 1
       - name: "NetworkOut"
       - name: "NetworkIn"

    metricsTimeRange:
      startTimeInMinsBeforeNow: 10
      endTimeInMinsBeforeNow: 0

    # Rate limit ( per second ) for GetMetricStatistics, default value is 400. https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/cloudwatch_limits.html
    getMetricStatisticsRateLimit: 400

    # 
    # The max number of retry attempts for failed retryable requests
    # (ex: 5xx error responses from a service) or throttling errors
    #
    maxErrorRetrySize: 0


#Allowed values are Basic and Detailed. Refer https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/using-cloudwatch-new.html for more information
# Basic will fire CloudWatch API calls every 5 minutes
# Detailed will fire CloudWatch API calls every 1 minutes
cloudWatchMonitoring: "Basic"

#If you want any other interval ( other than the mentioned values in the above configuration ) configure it here, if not leave it empty. This value is in minutes
cloudWatchMonitoringInterval: 0

concurrencyConfig:
  noOfAccountThreads: 3
  noOfRegionThreadsPerAccount: 3
  noOfMetricThreadsPerRegion: 3
  #Thread timeout in seconds
  threadTimeOut: 30

regionEndPoints:
  ap-southeast-1: monitoring.ap-southeast-1.amazonaws.com
  ap-southeast-2: monitoring.ap-southeast-2.amazonaws.com
  ap-northeast-1: monitoring.ap-northeast-1.amazonaws.com
  eu-central-1: monitoring.eu-central-1.amazonaws.com
  eu-west-1: monitoring.eu-west-1.amazonaws.com
  us-east-1: monitoring.us-east-1.amazonaws.com
  us-west-1: monitoring.us-west-1.amazonaws.com
  us-west-2: monitoring.us-west-2.amazonaws.com
  sa-east-1: monitoring.sa-east-1.amazonaws.com

#prefix used to show up metrics in AppDynamics. This will create this metric in all the tiers, under this path
#metricPrefix: "Custom Metrics|Amazon EC2|"

#This will create it in specific Tier/Component. Make sure to replace <COMPONENT_ID> with the appropriate one from your environment.
#To find the <COMPONENT_ID> in your environment, please follow the screenshot https://docs.appdynamics.com/display/PRO42/Build+a+Monitoring+Extension+Using+Java
metricPrefix: "Server|Component:<COMPONENT_ID>|Custom Metrics|Amazon EC2|"
~~~

### AWS Credentials Encryption
To set an encrypted awsAccessKey and awsSecretKey in config.yaml, follow the steps below:

1. Download the util jar to encrypt the AWS Credentials from [here](https://github.com/Appdynamics/maven-repo/blob/master/releases/com/appdynamics/appd-exts-commons/1.1.2/appd-exts-commons-1.1.2.jar).
2. Run command:

   	~~~   
   	java -cp appd-exts-commons-1.1.2.jar com.appdynamics.extensions.crypto.Encryptor EncryptionKey CredentialToEncrypt

   	For example:
   	java -cp "appd-exts-commons-1.1.2.jar" com.appdynamics.extensions.crypto.Encryptor test myAwsAccessKey

   	java -cp "appd-exts-commons-1.1.2.jar" com.appdynamics.extensions.crypto.Encryptor test myAwsSecretKey
   	~~~

3. Set the decryptionKey field in config.yaml with the encryption key used, as well as the resulting encrypted awsAccessKey and awsSecretKey in their respective fields.

## Metrics
Typical metric path: **Application Infrastructure Performance|\<Tier\>|Custom Metrics|Amazon EC2|\<Account Name\>|\<Region\>|Instance|\<instance id or name\>** followed by the metrics defined in the link below:

- [EC2 Metrics](http://docs.aws.amazon.com/AmazonCloudWatch/latest/DeveloperGuide/ec2-metricscollected.html)

## Contributing

Always feel free to fork and contribute any changes directly via [GitHub](https://github.com/Appdynamics/aws-ec2-monitoring-extension).

## Community

Find out more in the [AppSphere](https://www.appdynamics.com/community/exchange/extension/aws-ec2-monitoring-extension) community.

## Support

For any questions or feature request, please contact [AppDynamics Center of Excellence](mailto:help@appdynamics.com).
