DOCKER_COMPOSE=APPDYNAMICS_AGENT_ACCOUNT_NAME=$(APPDYNAMICS_AGENT_ACCOUNT_NAME) APPDYNAMICS_AGENT_ACCOUNT_ACCESS_KEY=$(APPDYNAMICS_AGENT_ACCOUNT_ACCESS_KEY) APPDYNAMICS_CONTROLLER_HOST_NAME=$(APPDYNAMICS_CONTROLLER_HOST_NAME) APPDYNAMICS_CONTROLLER_SSL_ENABLED=$(APPDYNAMICS_CONTROLLER_SSL_ENABLED) APPDYNAMICS_CONTROLLER_PORT=$(APPDYNAMICS_CONTROLLER_PORT) docker-compose
DOCKER_RUN=$(DOCKER_COMPOSE) up -d --build
DOCKER_STOP=$(DOCKER_COMPOSE) down

dockerRun: ## Run MA in docker
	@echo starting container ##################%%%%%%%%%%%%%%%%%%%&&&&&&&&&&&&&&&&&&&&&&
	${DOCKER_RUN}
	@echo started container ##################%%%%%%%%%%%%%%%%%%%&&&&&&&&&&&&&&&&&&&&&&

dockerStop:
	${DOCKER_STOP}

sleep:
	@echo Waiting for 5 minutes to read the metrics
	sleep 60
	@echo Wait finished

terraformApply:
	@echo Download terraform
	sudo wget https://releases.hashicorp.com/terraform/0.11.11/terraform_0.11.11_linux_amd64.zip
	sudo unzip -o terraform_0.11.11_linux_amd64.zip
	@echo Terraform downloaded
	mv main.tf ./terraform
	cd terraform/
	terraform init
	@echo Terraform initialised
	terraform apply auto-approve access_key=${AWS_ACCESS_KEY_ID}  secret_key=${AWS_SECRET_ACCESS_KEY}
	@echo Terraform setup done