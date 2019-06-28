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
	#sudo mkdir terraform
	#cd terraform/
	sudo wget https://releases.hashicorp.com/terraform/0.11.11/terraform_0.11.11_linux_amd64.zip -P terraform
	cd ./terraform
	ls
	sudo unzip terraform/terraform_0.11.11_linux_amd64.zip -d terraform
	@echo Terraform downloaded
	#cd ..
	#sudo mv main.tf ./terraform
	#cd terraform/
	sudo terraform/terraform init
	sudo terraform/terraform plan
	@echo Terraform initialised
	sudo terraform/terraform apply -var access_key="${AWS_ACCESS_KEY_ID}" -var secret_key="${AWS_SECRET_ACCESS_KEY}" -auto-approve
	@echo Terraform setup done