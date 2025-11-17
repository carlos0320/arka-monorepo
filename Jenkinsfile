pipeline {
    agent any

    environment {
        AWS_REGION     = 'us-east-1'
        AWS_ACCOUNT_ID = '634547947021'
    }

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

        stage('Push Docker image to ECR - order-mcsv') {
            steps {
                script {
                    echo 'Haciendo login en ECR y haciendo push de la imagen de order-mcsv...'

                    // Imagen local que construimos en el stage anterior
                    def localImage = "arka-order-mcsv:jenkins-${env.BUILD_NUMBER}"

                    // Repo y tag en ECR
                    def ecrRepo = "${env.AWS_ACCOUNT_ID}.dkr.ecr.${env.AWS_REGION}.amazonaws.com/order-service"
                    def ecrTag  = "jenkins-${env.BUILD_NUMBER}"
                    def ecrImage = "${ecrRepo}:${ecrTag}"

                    withCredentials([
                        usernamePassword(
                            credentialsId: 'aws-arka-creds',
                            usernameVariable: 'AWS_ACCESS_KEY_ID',
                            passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                        )
                    ]) {
                        // AWS CLI usa estas variables de entorno automáticamente
                        sh """
                          # Login en ECR
                          aws ecr get-login-password --region ${env.AWS_REGION} \
                            | docker login --username AWS --password-stdin ${ecrRepo}

                          # Tag local -> ECR
                          docker tag ${localImage} ${ecrImage}

                          # Push a ECR
                          docker push ${ecrImage}
                        """
                    }

                    echo "Imagen subida a ECR: ${ecrImage}"
                }
            }
        }
    }
}
