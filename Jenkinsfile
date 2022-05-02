pipeline {

    agent any

    environment {
        dockerhub=credentials('dockerhub')
    }

    stages {

        stage('Initialize') {
            steps {
                echo "PATH = ${PATH}"
            }
        }

        stage('Build') {
            steps {
                echo "Build Stage"
                sh 'mvn -D maven.test.failure.ignore=true install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

   stage ('Package') {
            steps {
                sh 'mvn clean package'
                sh 'docker build -t pos-auth-api .'
                sh 'echo $dockerhub_PSW | docker login -u $dockerhub_USR --password-stdin'
                sh 'docker push networkninjadh/pos-auth-api:latest'
            }
        }

        stage('Deploy') {
            steps {

            }
        }
    }
}
