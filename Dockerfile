FROM vishakasekar/machineagent:latest AS MA

RUN apt-get update
RUN apt-get -y install wget unzip

RUN sleep 60

ADD target/AWSEC2Monitor-*.zip /opt/appdynamics/monitors

RUN unzip -q "/opt/appdynamics/monitors/AWSEC2Monitor-*.zip" -d /opt/appdynamics/monitors
RUN find /opt/appdynamics/monitors/ -name '*.zip' -delete


CMD ["sh", "-c", "java ${MACHINE_AGENT_PROPERTIES} -jar /opt/appdynamics/machineagent.jar"]