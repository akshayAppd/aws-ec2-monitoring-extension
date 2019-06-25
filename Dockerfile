FROM vishakasekar/machineagent:latest AS MA

RUN apt-get update
RUN apt-get -y install wget unzip
RUN wget https://releases.hashicorp.com/terraform/0.11.11/terraform_0.11.11_linux_amd64.zip
RUN unzip terraform_0.11.11_linux_amd64.zip
RUN mv terraform /usr/local/bin/


WORKDIR /home/ubuntu/terraform/
COPY creds /usr/local/bin


WORKDIR /usr/local/bin/
ADD main.tf /usr/local/bin

RUN chmod +x /usr/local/bin/terraform

RUN terraform init
RUN echo "hello world"
RUN echo "${APPDYNAMICS_AGENT_ACCOUNT_NAME}"


RUN terraform plan
RUN terraform apply -auto-approve

RUN sleep 60

ADD target/AWSEC2Monitor-*.zip /opt/appdynamics/monitors

RUN unzip -q "/opt/appdynamics/monitors/AWSEC2Monitor-*.zip" -d /opt/appdynamics/monitors
RUN find /opt/appdynamics/monitors/ -name '*.zip' -delete


CMD ["sh", "-c", "java ${MACHINE_AGENT_PROPERTIES} -jar /opt/appdynamics/machineagent.jar"]

RUN sleep 300

RUN terraform destroy -auto-approve -target aws_instance.aws_btd
