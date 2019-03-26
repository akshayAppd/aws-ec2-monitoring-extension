provider "aws" {
	region = "us-west-2"
	#access_key = "$${AWS_ACCESS_KEY}"
	#secret_key = "$${AWS_SECRET_KEY}"
}

resource "aws_instance" "aws_btd" {
  ami = "ami-032509850cf9ee54e"
  instance_type = "t2.micro"

  #Reading the subnet_id from variables.tf
  subnet_id = "subnet-3c3bbb75"
  
  #Security group
  security_groups = ["sg-ccb3ebb"]

}
