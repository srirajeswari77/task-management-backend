
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh '''
                    docker run --rm \
                      --user 1000:1000 \
                      -e HOME=/tmp \
                      -e MAVEN_CONFIG=/tmp/.m2 \
                      -v jenkins_home:/jenkins_home \
                      -w "/jenkins_home/workspace/${JOB_NAME}" \
                      maven:3.9.11-eclipse-temurin-21 \
                      mvn clean test package
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build -t task-management-backend:${BUILD_NUMBER} .
                    docker tag task-management-backend:${BUILD_NUMBER} task-management-backend:latest
                '''
            }
        }
    }

    post {
        success {
            echo 'Backend CI pipeline completed successfully!'
        }
        failure {
            echo 'Backend CI pipeline failed. Check the console output.'
        }
    }
}
