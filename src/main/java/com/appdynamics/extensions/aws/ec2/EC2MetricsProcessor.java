package com.appdynamics.extensions.aws.ec2;

import static com.appdynamics.extensions.aws.Constants.METRIC_PATH_SEPARATOR;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.appdynamics.extensions.aws.config.MetricType;
import com.appdynamics.extensions.aws.ec2.providers.EC2InstanceNameProvider;
import com.appdynamics.extensions.aws.metric.AccountMetricStatistics;
import com.appdynamics.extensions.aws.metric.MetricStatistic;
import com.appdynamics.extensions.aws.metric.NamespaceMetricStatistics;
import com.appdynamics.extensions.aws.metric.RegionMetricStatistics;
import com.appdynamics.extensions.aws.metric.StatisticType;
import com.appdynamics.extensions.aws.metric.processors.MetricsProcessor;
import com.appdynamics.extensions.aws.metric.processors.MetricsProcessorHelper;

/**
 * @author Florencio Sarmiento
 *
 */
public class EC2MetricsProcessor implements MetricsProcessor {
	
	private static final String NAMESPACE = "AWS/EC2";
	
	private static final String[] DIMENSIONS = {"InstanceId"};
	
	private static final String INSTANCE = "Instance";
	
	private List<MetricType> metricTypes;
	
	private Pattern excludeMetricsPattern;
	
	public EC2MetricsProcessor(List<MetricType> metricTypes,
			Set<String> excludeMetrics) {
		this.metricTypes = metricTypes;
		this.excludeMetricsPattern = MetricsProcessorHelper.createPattern(excludeMetrics);
	}

	public List<Metric> getMetrics(AmazonCloudWatch awsCloudWatch) {
		return MetricsProcessorHelper.getFilteredMetrics(awsCloudWatch, 
				NAMESPACE, 
				excludeMetricsPattern, 
				DIMENSIONS);
	}
	
	public StatisticType getStatisticType(Metric metric) {
		return MetricsProcessorHelper.getStatisticType(metric, metricTypes);
	}
	
	public Map<String, Double> createMetricStatsMapForUpload(NamespaceMetricStatistics namespaceMetricStats) {
		EC2InstanceNameProvider ec2InstanceNameProvider = EC2InstanceNameProvider.getInstance();
		Map<String, Double> statsMap = new HashMap<String, Double>();
		
		if (namespaceMetricStats != null) {
			for (AccountMetricStatistics accountStats : namespaceMetricStats.getAccountMetricStatisticsList()) {
				for (RegionMetricStatistics regionStats : accountStats.getRegionMetricStatisticsList()) {
					for (MetricStatistic metricStat : regionStats.getMetricStatisticsList()) {
						String metricPath = createMetricPath(accountStats.getAccountName(), 
								regionStats.getRegion(), metricStat, ec2InstanceNameProvider);
						statsMap.put(metricPath, metricStat.getValue());
					}
				}
			}
		}
		
		return statsMap;
	}
	
	public String getNamespace() {
		return NAMESPACE;
	}
	
	private String createMetricPath(String accountName, String region, 
			MetricStatistic metricStatistic, EC2InstanceNameProvider ec2InstanceNameProvider) {
		
		String instanceId = metricStatistic.getMetric().getDimensions().get(0).getValue();
		String instanceName = ec2InstanceNameProvider.getInstanceName(accountName, region, instanceId);
		
		StringBuilder metricPath = new StringBuilder(accountName)
			.append(METRIC_PATH_SEPARATOR)
			.append(region)
			.append(METRIC_PATH_SEPARATOR)
			.append(INSTANCE)
			.append(METRIC_PATH_SEPARATOR)
			.append(instanceName)
			.append(METRIC_PATH_SEPARATOR)
			.append(metricStatistic.getMetric().getMetricName());
		
		return metricPath.toString();
	}

}
