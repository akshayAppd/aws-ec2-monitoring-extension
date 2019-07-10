package com.appdynamics.extensions.aws.ec2;

import org.apache.http.impl.client.CloseableHttpClient;

import com.appdynamics.extensions.http.UrlBuilder;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Akshay Srivastava
 */
public class MetricCheckIT {

    private static final String USER_AGENT = "Mozilla/5.0";

    private CloseableHttpClient httpClient;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String APPDYNAMICS_CONTROLLER_HOST_NAME_STRING = "APPDYNAMICS_CONTROLLER_HOST_NAME";
    private static final String APPDYNAMICS_CONTROLLER_PORT_STRING = "APPDYNAMICS_CONTROLLER_PORT";
    private static final String APPDYNAMICS_CONTROLLER_SSL_ENABLED_STRING = "APPDYNAMICS_CONTROLLER_SSL_ENABLED";
    private static final String EC2_INSTANCE_NAME_STRING = "EC2_INSTANCE_NAME";

    private static String CONTROLLER_HOST = null;
    private static String CONTROLLER_PORT = null;
    private static String CONTROLLER_SSL_ENABLED = null;
    private static String EC2_INSTANCE_NAME = null;


    @Before
    public void setup() {

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials("admin@customer1", "admin");
        provider.setCredentials(AuthScope.ANY, credentials);

        httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        CONTROLLER_HOST = System.getenv(APPDYNAMICS_CONTROLLER_HOST_NAME_STRING);
        CONTROLLER_PORT = System.getenv(APPDYNAMICS_CONTROLLER_PORT_STRING);
        CONTROLLER_SSL_ENABLED = System.getenv(APPDYNAMICS_CONTROLLER_SSL_ENABLED_STRING);
        EC2_INSTANCE_NAME = System.getenv(EC2_INSTANCE_NAME_STRING);

    }

    @After
    public void tearDown() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAPICallsMetric() throws IOException {


        UrlBuilder builder = UrlBuilder.builder();
        builder.host(CONTROLLER_HOST).port(CONTROLLER_PORT).ssl(Boolean.valueOf(CONTROLLER_SSL_ENABLED)).path("controller/rest/applications/Server%20&%20Infrastructure%20Monitoring/metric-data");
        builder.query("metric-path", "Application%20Infrastructure%20Performance%7CRoot%7CCustom%20Metrics%7CAmazon%20EC2%7CAWS%20API%20Calls");
        builder.query("time-range-type", "BEFORE_NOW");
        builder.query("duration-in-mins", "1");
        builder.query("output", "JSON");

        CloseableHttpResponse httpResponse = sendGET(builder.build());

        int statusCode = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals("Invalid response code", 200, statusCode);


        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();


        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(response.toString());

        String metricName = jsonNode.get(0).get("metricName").getTextValue();
        int metricValue = jsonNode.get(0).get("metricValues").get(0).get("current").getIntValue();

        Assert.assertEquals("Valid metric name", "Custom Metrics|Amazon EC2|AWS API Calls", metricName);

        Assert.assertTrue("Invalid metric value", metricValue >= 0);

    }

    @Test
    public void testCPUMetric() throws IOException {


        UrlBuilder builder = UrlBuilder.builder();
        builder.host(CONTROLLER_HOST).port(CONTROLLER_PORT).ssl(Boolean.valueOf(CONTROLLER_SSL_ENABLED)).path("controller/rest/applications/Server%20&%20Infrastructure%20Monitoring/metric-data");
        builder.query("metric-path", "Application%20Infrastructure%20Performance%7CRoot%7CCustom%20Metrics%7CAmazon%20EC2%7CAppD%7Cus-west-2%7CInstance%7Cbtd-ec2%7CCPUUtilization");
        builder.query("time-range-type", "BEFORE_NOW");
        builder.query("duration-in-mins", "1");
        builder.query("output", "JSON");

        CloseableHttpResponse httpResponse = sendGET(builder.build());

        int statusCode = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals("Invalid response code", 200, statusCode);


        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(response.toString());

        String metricName = jsonNode.get(0).get("metricName").getTextValue();
        int metricValue = jsonNode.get(0).get("metricValues").get(0).get("current").getIntValue();


        Assert.assertEquals("Valid metric name", "Custom Metrics|Amazon EC2|AppD|us-west-2|Instance|btd-ec2|CPUUtilization", metricName);

        Assert.assertTrue("Invalid metric value", metricValue >= 0);

    }

    private CloseableHttpResponse sendGET(String url) throws IOException {

        HttpGet httpGet = new HttpGet(url);

        httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        return httpResponse;
    }

}
