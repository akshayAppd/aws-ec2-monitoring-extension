/*
 *   Copyright 2018. AppDynamics LLC and its affiliates.
 *   All Rights Reserved.
 *   This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 *   The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.aws.ec2;

import static com.appdynamics.extensions.aws.Constants.METRIC_PATH_SEPARATOR;

import com.appdynamics.extensions.aws.SingleNamespaceCloudwatchMonitor;
import com.appdynamics.extensions.aws.collectors.NamespaceMetricStatisticsCollector;
import com.appdynamics.extensions.aws.ec2.config.EC2Configuration;
import com.appdynamics.extensions.aws.ec2.providers.EC2InstanceNameProvider;
import com.appdynamics.extensions.aws.metric.processors.MetricsProcessor;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * @author Florencio Sarmiento
 */
public class EC2Monitor extends SingleNamespaceCloudwatchMonitor<EC2Configuration> {

    private static final Logger LOGGER = Logger.getLogger("com.singularity.extensions.aws.EC2Monitor");

    private static final String DEFAULT_METRIC_PREFIX = String.format("%s%s%s%s",
            "Custom Metrics", METRIC_PATH_SEPARATOR, "Amazon EC2", METRIC_PATH_SEPARATOR);

    public EC2Monitor() {
        super(EC2Configuration.class);
        LOGGER.info(String.format("Using AWS EC2 Monitor Version [%s]",
                this.getClass().getPackage().getImplementationTitle()));
    }

    @Override
    protected void initialiseServiceProviders(EC2Configuration config,
                                              Map<String, String> paramArgs) {
        super.initialiseServiceProviders(config, paramArgs);

        EC2InstanceNameProvider ec2InstanceNameProvider = EC2InstanceNameProvider.getInstance();
        ec2InstanceNameProvider.initialise(config.getAccounts(),
                config.getCredentialsDecryptionConfig(),
                config.getProxyConfig(), config.getEc2InstanceNameConfig(), config.getTags(),
                config.getMetricsConfig().getMaxErrorRetrySize());
    }

    @Override
    protected NamespaceMetricStatisticsCollector getNamespaceMetricsCollector(
            EC2Configuration config) {
        MetricsProcessor metricsProcessor = createMetricsProcessor(config);

        return new NamespaceMetricStatisticsCollector
                .Builder(config.getAccounts(),
                config.getConcurrencyConfig(),
                config.getMetricsConfig(),
                metricsProcessor)
                .withCredentialsEncryptionConfig(config.getCredentialsDecryptionConfig())
                .withProxyConfig(config.getProxyConfig())
                .build();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getMetricPrefix(EC2Configuration config) {
        return StringUtils.isNotBlank(config.getMetricPrefix()) ?
                config.getMetricPrefix() : DEFAULT_METRIC_PREFIX;
    }

    private MetricsProcessor createMetricsProcessor(EC2Configuration config) {
        return new EC2MetricsProcessor(
                config.getMetricsConfig().getMetricTypes(),
                config.getMetricsConfig().getExcludeMetrics(),
                config.getEc2Instance());
    }
}