#variable "aws_access_key" {}
#variable "aws_secret_key" {}

provider "aws" {
  #access_key = "${var.aws_access_key}"
  #secret_key = "${var.aws_secret_key}"
  region     = "eu-central-1"
}

resource "aws_instance" "aws_btd" {
  ami = "ami-09def150731bdbcc2"
  instance_type = "t2.micro"
  iam_instance_profile = "extensions-teamcity-artifact-storage-role"

  #Reading the subnet_id from variables.tf
  #subnet_id = "subnet-3c3bbb75"
  
  #Security group
  #security_groups = ["sg-0a15a568fbcd99f41"]
}