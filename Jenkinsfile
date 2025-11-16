pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // This checks out the same repo where the Jenkinsfile lives
                checkout scm
            }
        }

        stage('Build (dry run)') {
            steps {
                sh 'echo "Here I would run mvn clean package, etc."'
                sh 'ls -R'
            }
        }
    }
}
