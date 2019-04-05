FROM appdynamics/machine:local AS MA

RUN apt-get -y install wget unzip
RUN wget https://releases.hashicorp.com/terraform/0.11.11/terraform_0.11.11_linux_amd64.zip
RUN unzip terraform_0.11.11_linux_amd64.zip
RUN mv terraform /usr/local/bin/

ADD main.tf /usr/local/bin

RUN chmod +x /usr/local/bin/terraform

WORKDIR /usr/local/bin/
RUN terraform init
RUN echo "hello world"
RUN terraform plan

RUN export AWS_ACCESS_KEY_ID="$(AWS_ACCESS_KEY)"
RUN export AWS_SECRET_KEY="$(AWS_SECRET_KEY)"

RUN terraform apply -auto-approve

ADD target/AWSEC2Monitor-*.zip /opt/appdynamics/monitors

RUN unzip -q "/opt/appdynamics/monitors/AWSEC2Monitor-*.zip" -d /opt/appdynamics/monitors
RUN find /opt/appdynamics/monitors/ -name '*.zip' -delete
#RUN output "instance_id" {
#  value = "${element(aws_instance.aws_btd.*.id, 0)}"
#}

CMD ["sh", "-c", "java ${MACHINE_AGENT_PROPERTIES} -jar /opt/appdynamics/machineagent.jar"]