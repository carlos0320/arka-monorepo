pipeline {
    agent any

    environment {
        AWS_REGION     = 'us-east-1'
        AWS_ACCOUNT_ID = '634547947021'
        ECS_CLUSTER_NAME = 'arka-prod-ecs-cluster'
        ECS_ORDER_SERVICE_NAME = 'order-mcsv'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Detect changed microservices') {
            steps {
                script {
                    echo 'Detectando microservicios cambiados...'

                    // Commit actual
                    def currentCommit = sh(
                        script: 'git rev-parse HEAD',
                        returnStdout: true
                    ).trim()

                    // Commit exitoso previo
                    def previousCommit = env.GIT_PREVIOUS_SUCCESSFUL_COMMIT

                    // Microservicios a monitorear
                    def services = [
                        'user-mcsv',
                        'api-gateway',
                        'inventory-mcsv',
                        'order-mcsv',
                        'cart-mcsv'
                    ]

                    if (!previousCommit) {
                        echo 'No hay build exitoso previo. Marcando todos los microservicios como cambiados.'
                        env.CHANGED_SERVICES = services.join(',')
                    } else {
                        echo "Comparando cambios entre ${previousCommit} y ${currentCommit}..."

                        // Archivos cambiados
                        def changedFilesRaw = sh(
                            script: "git diff --name-only ${previousCommit} ${currentCommit} || echo ''",
                            returnStdout: true
                        ).trim()

                        // Convertimos a lista (seguro)
                        def changedFiles = changedFilesRaw ? changedFilesRaw.split('\n') as List : []

                        if (!changedFiles) {
                            echo 'No se detectaron archivos modificados.'
                        } else {
                            echo "Archivos modificados:"
                            changedFiles.each { echo " - ${it}" }
                        }

                        // Detectamos microservicios
                        def changedServices = services.findAll { svc ->
                            changedFiles.any { path -> path.startsWith("${svc}/") }
                        }

                        if (!changedServices) {
                            echo 'No se detectaron cambios en microservicios.'
                            env.CHANGED_SERVICES = ''
                        } else {
                            echo "Microservicios cambiados: ${changedServices}"
                            env.CHANGED_SERVICES = changedServices.join(',')
                        }
                    }

                    echo "➡ Resultado final: CHANGED_SERVICES = '${env.CHANGED_SERVICES}'"
                }
            }
        }

        stage('Verify tools (Java & Maven)') {
            steps {
                echo 'Mostrando versiones de Java y Maven...'
                sh 'java -version || echo "Java no encontrado"'
                sh 'mvn -version || echo "Maven no encontrado"'
            }
        }

        stage('Build (sin tests)') {
            steps {
                echo 'Compilando y empaquetando monorepo Arka (sin tests)...'
                sh 'mvn clean package -DskipTests=true'
            }
        }

        stage('Tests (por ahora deshabilitados)') {
            steps {
                echo 'Tests deshabilitados temporalmente en CI (SECRET_APP_DEV_PATH requerido).'
                sh 'mvn test -DskipTests=true'
            }
        }


        stage('SonarQube Analysis') {
            steps {
                echo 'Ejecutando análisis de SonarQube...'
                // Usa la configuración "sonarqube-local" que creamos en Manage Jenkins → System
                withSonarQubeEnv('sonarqube-local') {
                    sh '''
                      mvn sonar:sonar \
                        -DskipTests=true \
                        -Dsonar.projectKey=arka-monorepo
                    '''
                }
            }
        }

        stage('Docker sanity check') {
            steps {
                echo 'Verificando que Jenkins puede usar Docker...'
                sh 'docker version'
                sh 'docker ps'
            }
        }

        stage('Build Docker image - order-mcsv') {
            steps {
                script {
                    echo 'Construyendo imagen Docker para order-mcsv...'

                    // Nombre de la imagen local, incluyendo el número de build de Jenkins
                    def imageName = "arka-order-mcsv:jenkins-${env.BUILD_NUMBER}"

                    sh """
                      docker build \
                        -f order-mcsv/Dockerfile \
                        -t ${imageName} \
                        .
                    """

                    echo "Imagen construida: ${imageName}"
                }
            }
        }


        stage('AWS CLI sanity check') {
            steps {
                echo 'Verificando que Jenkins puede usar AWS CLI...'
                sh 'aws --version || echo "AWS CLI no disponible"'
            }
        }

    }
}
