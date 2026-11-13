
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
                      -v "$WORKSPACE:/workspace" \
                      -w /workspace \
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
