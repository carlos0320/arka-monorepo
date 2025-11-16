pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Descarga el repositorio donde vive este Jenkinsfile
                checkout scm
            }
        }

        stage('Verify tools (Java & Maven)') {
            steps {
                echo 'Mostrando versiones de Java y Maven...'
                sh 'java -version || echo "Java no encontrado"'

                // Si tu proyecto tiene Maven Wrapper (mvnw), usamos eso:
                sh 'ls'
                sh './mvnw -version || mvn -version || echo "Maven / Maven Wrapper no encontrados"'
            }
        }

        stage('Build monorepo') {
            steps {
                echo 'Construyendo proyecto Arka con Maven...'

                // Opción 1: si tienes mvnw en la raíz del repo:
                sh './mvnw clean package -DskipTests=true'

                // Opción 2 (solo si NO tienes mvnw y sí tienes mvn instalado en Jenkins):
                // sh 'mvn clean package -DskipTests=true'
            }
        }
    }
}
