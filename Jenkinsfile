pipeline {
    agent any

    environment {
        AWS_ACCESS_KEY_ID = credentials('aws-secret-id')
        AWS_SECRET_ACCESS_KEY = credentials('aws-scret-key')
    }
    
    stages {
        stage('AWS cli') {
            steps {
                sh "aws --version"
                
                sh "aws configure set region eu-west-3"
                
                sh "aws s3 ls"
            }
        }
        
        stage('git clone') {
            steps {
                git(
                    url: "https://github.com/ClementGuio/spring-aws.git",
                    branch: "main"
                    )
            }
        }
        
        stage('build') {
            steps {
                sh "mvn clean package -DskipTests=true"
                archive "/var/lib/jenkins/workspace/cmd-aws/target/*.jar"
            }
        }
        
        stage('unit test'){
            steps{
                sh "mvn test"
            }
            
            post{
                success{
                    sh "aws configure set region eu-west-3"
                    sh "aws s3 cp ./target/*.jar s3://cg-jenkins-bucket/spring-aws.jar"
                }
            }
        }
        
        stage('deploy-aws') {
            steps {
                sh "aws elasticbeanstalk create-application-version --application-name jpp-spring --source-bundle S3Bucket=cg-jenkins-bucket,S3Key=spring-aws.jar"
                sh "aws elasticbeanstalk update-environment ----application-name jpp-spring --environment-name jpp-spring-env "
            }
        }
    }
}
