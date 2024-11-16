pipeline {
    agent any

    stages {
        stage('Info') {
            steps {
                sh 'printenv'
            }
        }

        stage('Build') {
            stages {
                stage('Copy App Config') {
                    steps {
                        withCredentials([
                            file(credentialsId: 'application.yml', variable: 'APP_CONFIG'),
                            file(credentialsId: 'application-test.yml', variable: 'APP_TEST_CONFIG')
                        ]) {
                            sh """
                                mkdir -p src/main/resources
                                cp -f $APP_CONFIG src/main/resources/application.yml

                                mkdir -p src/test/resources
                                cp -f $APP_TEST_CONFIG src/test/resources/application.yml
                            """
                        }
                    }
                }

                // NOTE: Need versioning, my-assets-{version}.jar
                stage('Build') {
                    steps {
                        sh '''
                            chmod +x ./gradlew
                            ./gradlew clean build
                        '''
                    }

                    post {
                        success {
                            sh 'ls -l build/libs/'
                        }
                    }
                }
            }
        }
    }
}
