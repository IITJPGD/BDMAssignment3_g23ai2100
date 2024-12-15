provider "aws" {
  region = "us-east-1"
}

resource "aws_db_instance" "example" {
  allocated_storage    = 20                  # Storage size in GB
  engine               = "mysql"             # Database engine (e.g., mysql, postgres)
  engine_version       = "8.0.30"            # Engine version
  instance_class       = "db.t3.micro"       # Instance type
  name                 = "iitbdm"         # Database name
  username             = "admin"             # Master username
  password             = "iit@2023" # Master password
  parameter_group_name = "default.mysql8.0"  # Parameter group
  skip_final_snapshot  = true                # Avoid final snapshot when deleting

  publicly_accessible    = true 
  vpc_security_group_ids = [aws_security_group.db_sg.id]
  db_subnet_group_name   = aws_db_subnet_group.default.name
}

resource "aws_db_subnet_group" "default" {
  name       = "example-db-subnet-group"
  subnet_ids = ["subnet-12345678", "subnet-87654321"]

  tags = {
    Name = "example-db-subnet-group"
  }
}

resource "aws_security_group" "db_sg" {
  name_prefix = "rds-db-sg"

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "example-db-sg"
  }
}
