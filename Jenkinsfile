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
                            sh '''
                                mkdir -p src/main/resources
                                cp -f $APP_CONFIG src/main/resources/application.yml

                                mkdir -p src/test/resources
                                cp -f $APP_TEST_CONFIG src/test/resources/application.yml
                            '''
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

                // NOTE: JAR file name should be generated automatically
                stage('JAR Running Test') {
                    environment {
                        BUILD_APP = "build/libs/my-assets-0.0.1-SNAPSHOT.jar"
                        SERVER_PORT = "18080"
                    }
                    steps {
                        sh '''
                            java -jar $BUILD_APP -Dserver.port=$SERVER_PORT &
                            APP_PID=$!

                            for i in {1..10}; do
                                if nc -z localhost $SERVER_PORT; then
                                    echo "Application started successfully."
                                    break
                                fi
                                echo "Wait for application to start..."
                                sleep 10
                            done

                            if curl -s http://localhost:$SERVER_PORT/v1/hello | grep OK; then
                                echo "Application running test passed."
                            else
                                echo "Application running test failed."
                                kill $APP_PID
                                exit 1
                            fi

                            kill $APP_PID
                        '''
                    }
                }
            }
        }
    }
}
