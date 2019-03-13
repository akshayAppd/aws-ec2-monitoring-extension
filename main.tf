provider "aws" {
	region = "us-west-2"
}

resource "aws_instance" "aws_btd" {
  ami = "ami-032509850cf9ee54e"
  instance_type = "t2.micro"

  #Reading the subnet_id from variables.tf
  subnet_id = "${var.aws_btd_subnet}"
  
  #Security group
  security_groups = ["${var.aws_btd_security_group}"]
  
  #Tags can be added as follows:
  tags {
    Name = "aws_btd_terraform"
  }
}
