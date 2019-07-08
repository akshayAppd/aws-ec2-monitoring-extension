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
import com.appdynamics.extensions.aws.config.Account;
import com.appdynamics.extensions.aws.ec2.config.EC2Configuration;
import com.appdynamics.extensions.aws.ec2.providers.EC2InstanceNameProvider;
import com.appdynamics.extensions.aws.metric.processors.MetricsProcessor;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Florencio Sarmiento
 */
public class EC2Monitor extends SingleNamespaceCloudwatchMonitor<EC2Configuration> {

    private static final Logger LOGGER = Logger.getLogger(EC2Monitor.class);

    private static final String DEFAULT_METRIC_PREFIX = String.format("%s%s%s%s",
            "Custom Metrics", METRIC_PATH_SEPARATOR, "Amazon EC2", METRIC_PATH_SEPARATOR);

    public EC2Monitor() {
        super(EC2Configuration.class);
        LOGGER.info(String.format("Using AWS EC2 Monitor Version [%s]",
                this.getClass().getPackage().getImplementationTitle()));
    }

    protected void initialiseServiceProviders(EC2Configuration config) {

        EC2InstanceNameProvider ec2InstanceNameProvider = EC2InstanceNameProvider.getInstance();
        //config.setAccounts(null);
        Account account  = new Account();
        account.setAwsAccessKey(System.getenv("AWS_ACCESS_KEY_ID"));
        account.setAwsSecretKey(System.getenv("AWS_SECRET_ACCESS_KEY"));
        account.setDisplayAccountName("AppD");
        Set<String> regions = new HashSet<>();
        regions.add("us-west-2");
        account.setRegions(regions);
        List<Account> acc = new ArrayList<>();
        acc.add(account);
        config.setAccounts(acc);
        ec2InstanceNameProvider.initialise(config.getAccounts(),
                config.getCredentialsDecryptionConfig(),
                config.getProxyConfig(), config.getEc2InstanceNameConfig(), config.getTags(),
                config.getMetricsConfig().getMaxErrorRetrySize());
    }

    @Override
    public String getDefaultMetricPrefix() {
        return DEFAULT_METRIC_PREFIX;
    }

    @Override
    public String getMonitorName() {
        return "EC2Monitor";
    }

    @Override
    protected int getTaskCount() {
        return 3;
    }

    @Override
    protected void initialize(EC2Configuration config) {
        super.initialize(config);
        initialiseServiceProviders(config);
    }

    @Override
    protected NamespaceMetricStatisticsCollector getNamespaceMetricsCollector(
            EC2Configuration config) {
        MetricsProcessor metricsProcessor = createMetricsProcessor(config);

        return new NamespaceMetricStatisticsCollector
                .Builder(config.getAccounts(),
                config.getConcurrencyConfig(),
                config.getMetricsConfig(),
                metricsProcessor,
                config.getMetricPrefix())
                .withCredentialsDecryptionConfig(config.getCredentialsDecryptionConfig())
                .withProxyConfig(config.getProxyConfig())
                .build();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    private MetricsProcessor createMetricsProcessor(EC2Configuration config) {
        return new EC2MetricsProcessor(
                config.getMetricsConfig().getIncludeMetrics(),
                config.getEc2Instance());
    }

    public static void main(String[] args) throws TaskExecutionException {


        ConsoleAppender ca = new ConsoleAppender();
        ca.setWriter(new OutputStreamWriter(System.out));
        ca.setLayout(new PatternLayout("%-5p [%t]: %m%n"));
        ca.setThreshold(Level.DEBUG);

        EC2Monitor monitor = new EC2Monitor();


        final Map<String, String> taskArgs = new HashMap<>();
        taskArgs.put("config-file", "/Users/akshay.srivastava/AppDynamics/btd/aws-ec2-monitoring-extension-ci/src/main/resources/conf/config.yml");
        //taskArgs.put("metric-file", "/Users/akshay.srivastava/AppDynamics/btd/aws-ec2-monitoring-extension-ci/src/main/resources/conf/metrics.xml");


        monitor.execute(taskArgs, null);

    }
}