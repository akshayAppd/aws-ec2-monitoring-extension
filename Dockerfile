FROM appdynamics/machine:4.5 AS MA

RUN apt-get install wget unzip
RUN wget https://releases.hashicorp.com/terraform/0.11.11/terraform_0.11.11_linux_amd64.zip
RUN unzip terraform_0.11.11_linux_amd64.zip
RUN mv terraform /usr/local/bin/

ADD main.tf /usr/local/bin/terraform
ADD variable.tf /usr/local/bin/terraform

ADD target/AWSEC2Monitor-*.zip /opt/appdynamics/monitors

RUN unzip -q "/opt/appdynamics/monitors/AWSEC2Monitor-*.zip" -d /opt/appdynamics/monitors
RUN find /opt/appdynamics/monitors/ -name '*.zip' -delete


RUN terraform init
RUN terraform apply -auto-approve /usr/local/bin/terraform -var 'access_key=${AWS_ACCESS_KEY}' -var 'secret_key=${AWS_SECRET_KEY}'


CMD ["sh", "-c", "java ${MACHINE_AGENT_PROPERTIES} -jar /opt/appdynamics/machineagent.jar"]