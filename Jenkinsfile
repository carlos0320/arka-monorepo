pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Verify tools (Java & Maven)') {
            steps {
                echo 'Mostrando versiones de Java y Maven...'
                sh 'java -version || echo "Java no encontrado"'
                sh 'mvn -version || echo "Maven no encontrado"'
            }
        }

        stage('Build monorepo') {
            steps {
                echo 'Construyendo proyecto Arka con Maven...'
                sh 'mvn clean package -DskipTests=true'
            }
        }
    }
}
