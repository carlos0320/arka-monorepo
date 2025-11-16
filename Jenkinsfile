pipeline {
    agent any // ejecuta estos pasos en cualquier máquina disponible

    stages {
        stage('Checkout') {
            steps {
                checkout scm // clonamos el repositorio
            }
        }

        stage('Verify tools (Java & Maven)') {
            steps { // verificamos si tenemos java y maven instalados
                echo 'Mostrando versiones de Java y Maven...'
                sh 'java -version || echo "Java no encontrado"'
                sh 'mvn -version || echo "Maven no encontrado"'
            }
        }

        stage('Build (sin tests)') {
            steps {
                echo 'Compilando y empaquetando monorepo Arka (sin tests)...'
                // Compila todo y genera jars de cada microservicio, pero no ejecuta tests
                sh 'mvn clean package -DskipTests=true'
            }
        }

        stage('Tests') {
            steps {
                echo 'Ejecutando tests (cuando existan)...'
                // Ejecuta los tests del proyecto (si no hay tests, este comando pasa sin problemas)
                sh 'mvn test'
            }
        }
    }
}
