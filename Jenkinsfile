pipeline {
    agent any

    environment {
        AWS_REGION     = 'us-east-1'
        AWS_ACCOUNT_ID = '634547947021'
        ECS_CLUSTER_NAME = 'arka-prod-ecs-cluster'

        ECS_USER_SERVICE_NAME      = 'user-mcsv-svc'
        ECS_ORDER_SERVICE_NAME     = 'order-mcsv'
        ECS_CART_SERVICE_NAME      = 'cart-mcsv-svc'
        ECS_INVENTORY_SERVICE_NAME = 'inventory-mcsv-svc'
        ECS_APIGW_SERVICE_NAME     = 'api-gateway-svc'
        ECS_NOTIFICATION_SERVICE_NAME = 'notification-service'

        ECS_USER_TASK_FAMILY = 'user-mcsv-taskdef'

        ECR_USER_REPO      = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/user-service"
        ECR_ORDER_REPO     = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/order-service"
        ECR_CART_REPO      = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/cart-service"
        ECR_INVENTORY_REPO = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/inventory-service"
        ECR_APIGW_REPO     = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/api-gateway"
        ECR_NOTIFICATION_REPO     = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/notification-service"
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

        stage('AWS CLI sanity check') {
            steps {
                echo 'Verificando que Jenkins puede usar AWS CLI...'
                sh 'aws --version || echo "AWS CLI no disponible"'
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

        stage('Push Docker image to ECR - order-mcsv') {
            steps {
                script {
                    echo 'Haciendo login en ECR y haciendo push de la imagen de order-mcsv...'

                    def localImage = "arka-order-mcsv:jenkins-${env.BUILD_NUMBER}"

                    def ecrRepo       = env.ECR_ORDER_REPO
                    def ecrTag        = "jenkins-${env.BUILD_NUMBER}"
                    def ecrImage      = "${ecrRepo}:${ecrTag}"
                    def ecrImageLatest = "${ecrRepo}:latest"

                    withCredentials([
                        usernamePassword(
                            credentialsId: 'aws-arka-creds',
                            usernameVariable: 'AWS_ACCESS_KEY_ID',
                            passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                        )
                    ]) {
                        sh """
                          # Login en ECR
                          aws ecr get-login-password --region ${env.AWS_REGION} \
                            | docker login --username AWS --password-stdin ${ecrRepo}

                          # Tag local -> tags en ECR
                          docker tag ${localImage} ${ecrImage}
                          docker tag ${localImage} ${ecrImageLatest}

                          # Push ambos tags
                          docker push ${ecrImage}
                          docker push ${ecrImageLatest}
                        """
                    }

                    echo "Imágenes subidas a ECR:"
                    echo " - ${ecrImage}"
                    echo " - ${ecrImageLatest}"
                }
            }
        }

        stage('Deploy to ECS - order-mcsv') {
            steps {
                script {
                    echo "Desplegando nueva versión de order-mcsv en ECS..."
                    echo "Cluster: ${env.ECS_CLUSTER_NAME}"
                    echo "Service: ${env.ECS_ORDER_SERVICE_NAME}"

                    withCredentials([
                        usernamePassword(
                            credentialsId: 'aws-arka-creds',
                            usernameVariable: 'AWS_ACCESS_KEY_ID',
                            passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                        )
                    ]) {
                        sh """
                          aws ecs update-service \
                            --cluster ${env.ECS_CLUSTER_NAME} \
                            --service ${env.ECS_ORDER_SERVICE_NAME} \
                            --force-new-deployment \
                            --region ${env.AWS_REGION}
                        """
                    }

                    echo "Comando de update-service enviado"
                }
            }
        }

         stage('Build Docker image - user-mcsv') {
            steps {
                script {
                    echo 'Construyendo imagen Docker para user-mcsv...'

                    // Nombre de la imagen local, incluyendo el número de build de Jenkins
                    def imageName = "arka-user-mcsv:jenkins-${env.BUILD_NUMBER}"

                    sh """
                      docker build \
                        -f user-mcsv/Dockerfile \
                        -t ${imageName} \
                        .
                    """

                    echo "Imagen construida: ${imageName}"
                }
            }
        }

        stage('Push Docker image to ECR - user-mcsv') {
            steps {
                script {
                    echo 'Haciendo login en ECR y haciendo push de la imagen de user-mcsv...'

                    def localImage     = "arka-user-mcsv:jenkins-${env.BUILD_NUMBER}"
                    def ecrRepo        = env.ECR_USER_REPO
                    def ecrTag         = "jenkins-${env.BUILD_NUMBER}"
                    def ecrImage       = "${ecrRepo}:${ecrTag}"
                    def ecrImageLatest = "${ecrRepo}:latest"

                    withCredentials([
                        usernamePassword(
                            credentialsId: 'aws-arka-creds',
                            usernameVariable: 'AWS_ACCESS_KEY_ID',
                            passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                        )
                    ]) {
                        sh """
                          # Login en ECR
                          aws ecr get-login-password --region ${env.AWS_REGION} \
                            | docker login --username AWS --password-stdin ${ecrRepo}

                          # Tag local -> tags en ECR
                          docker tag ${localImage} ${ecrImage}
                          docker tag ${localImage} ${ecrImageLatest}

                          # Push ambos tags
                          docker push ${ecrImage}
                          docker push ${ecrImageLatest}
                        """
                    }

                    echo "Imágenes subidas a ECR para user-mcsv:"
                    echo " - ${ecrImage}" // para historial de cambios
                    echo " - ${ecrImageLatest}" // lo usa ecs para desplegar
                }
            }
        }

         stage('Deploy to ECS - user-mcsv') {
            steps {
                script {
                    echo "Registrando nueva Task Definition y desplegando user-mcsv en ECS..."

                    withCredentials([
                        usernamePassword(
                            credentialsId: 'aws-arka-creds',
                            usernameVariable: 'AWS_ACCESS_KEY_ID',
                            passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                        )
                    ]) {
                        sh '''
                          set -e

                          FAMILY="${ECS_USER_TASK_FAMILY}"
                          CLUSTER="${ECS_CLUSTER_NAME}"
                          SERVICE="${ECS_USER_SERVICE_NAME}"
                          REGION="${AWS_REGION}"

                          # Usamos la imagen versionada de este build
                          NEW_IMAGE="${ECR_USER_REPO}:jenkins-${BUILD_NUMBER}"
                          echo "Usando imagen para nueva Task Definition: ${NEW_IMAGE}"

                          echo "Obteniendo Task Definition base..."
                          aws ecs describe-task-definition \
                            --task-definition "${FAMILY}" \
                            --query 'taskDefinition' \
                            --output json > base-td-user.json

                          echo "Construyendo nueva Task Definition con la imagen actual..."
                          cat base-td-user.json | jq --arg IMAGE "$NEW_IMAGE" '
                              .containerDefinitions[0].image = $IMAGE
                              | del(
                                  .taskDefinitionArn,
                                  .revision,
                                  .status,
                                  .requiresAttributes,
                                  .compatibilities,
                                  .registeredAt,
                                  .registeredBy
                                )
                            ' > new-td-user.json

                          echo "Registrando nueva Task Definition..."
                          NEW_TD_ARN=$(aws ecs register-task-definition \
                            --cli-input-json file://new-td-user.json \
                            --query 'taskDefinition.taskDefinitionArn' \
                            --output text)

                          echo "Nueva Task Definition registrada: ${NEW_TD_ARN}"

                          echo "Actualizando servicio ECS para usar la nueva Task Definition..."
                          aws ecs update-service \
                            --cluster "${CLUSTER}" \
                            --service "${SERVICE}" \
                            --task-definition "${NEW_TD_ARN}" \
                            --region "${REGION}"

                          echo "Deploy enviado. ECS hará el rolling deployment con la Task Definition nueva."
                        '''
                    }
                }
            }
        }

    }
}
