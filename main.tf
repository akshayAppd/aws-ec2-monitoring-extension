#variable "aws_access_key" {}
#variable "aws_secret_key" {}

provider "aws" {
  #access_key = "${var.aws_access_key}"
  #secret_key = "${var.aws_secret_key}"
  region     = "eu-central-1"
  #region = "us-west-2"
  shared_credentials_file = "creds"
  profile                 = "default"
}

resource "aws_instance" "aws_btd" {
  ami = "ami-061392db613a6357b"
  instance_type = "t2.micro"
  #iam_instance_profile = "extensions-teamcity-artifact-storage-role"
  availability_zone = "us-west-2b"

  #Reading the subnet_id from variables.tf
  #subnet_id = "subnet-3c3bbb75"

  #Security group
  #security_groups = ["sg-ccb3ebb"]
}