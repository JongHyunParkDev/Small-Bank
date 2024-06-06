pipeline {
    agent any
    environment {
        WEB_ROOT_PATH = "$WORKSPACE/was"
        WEBAPP_PATH = "$WORKSPACE/web"
        DOCKER_ROOT_PATH = "/home/docker_admin/docker/small_bank"
        DOCKER_BUILD_PATH = "$DOCKER_ROOT_PATH/was"
        SSH_CONFIG_NAME = "Docker Container Server"
    }
    tools {
        nodejs 'node16'
    	maven 'maven'
    }
    stages {
        stage('Build') {
            stages {
                stage('build - npm install') {
                    steps {
                        sh 'cd ${WEBAPP_PATH} && npm install'
                    }
                }

                stage('build - run build') {
                    steps {
                        sh 'cd ${WEBAPP_PATH} && npm run build'
                    }
                }

                stage('build - maven') {
                    steps {
                        sh 'mvn -f $WEB_ROOT_PATH/pom.xml clean install -DskipTests'
                    }
                }

                stage('build - copy') {
                    steps {
                        sh 'cp -Rf "$WEB_ROOT_PATH/target/SmallBank-1.0.0.jar" "$DOCKER_BUILD_PATH/SmallBank.jar"'
                    }
                }
            }
        }

        stage('Docker') {
            stages {
                stage('docker - compose rm') {
                    steps {
                        sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: "${SSH_CONFIG_NAME}",
                                    transfers: [
                                        sshTransfer(
                                            cleanRemote: false,
                                            excludes: '',
                                            execCommand: "cd ${DOCKER_ROOT_PATH} && docker-compose rm -fsv was", 
                                        )
                                    ]
                                )
                            ]
                        )
                    }
                }

                stage('docker - image') {
                    steps {
                        sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: "${SSH_CONFIG_NAME}",
                                    transfers: [
                                        sshTransfer(
                                            cleanRemote: false,
                                            excludes: '',
                                            execCommand: "cd \"${DOCKER_BUILD_PATH}\" && sh Dockerbuild.sh 0 && docker image prune -f",
                                        )
                                    ]
                                )
                            ]
                        )
                    }
                }

                stage('docker - compose up') {
                    steps {
                        sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: "${SSH_CONFIG_NAME}",
                                    transfers: [
                                        sshTransfer(
                                            cleanRemote: false,
                                            excludes: '',
                                            execCommand: "cd ${DOCKER_ROOT_PATH} && docker-compose up -d was", 
                                        )
                                    ]
                                )
                            ]
                        )
                    }
                }
            }
        }
   }
}
