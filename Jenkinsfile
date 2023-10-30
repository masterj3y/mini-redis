@Library('shared-lib') _

pipeline {
    agent { label 'rust-agent' }
    environment {
        TEL_NOTIFIER_TOKEN = credentials("TEL_NOTIFIER_TOKEN")
        TEL_NOTIF_RECEIVER = credentials("TEL_NOTIF_RECEIVER")
        IMAGE_NAME = "localhost:8083/mini-redis:$BUILD_ID"
    }
    stages {
        stage('test') {
            when {
                expression {
                    env.BRANCH_NAME == 'dev' || env.BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    runTests()
                }
            }
        }
        stage('build') {
            steps {
                script {
                    buildApp()
                }
            }
        }
        stage('build image') {
            when {
                expression {
                    env.BRANCH_NAME == 'dev' || env.BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    loginInDocker()
                    buildDockerImage IMAGE_NAME
                    pushDockerImage IMAGE_NAME
                }
            }
        }
        stage("run app") {
            steps {
                script {
                    def dockerCmd = "docker run $IMAGE_NAME"
                    notif("Runing App")
                    sshagent("deployer-ssh-key") {
                        sh "ssh -o StrictHostKeyChecking=no jenkins@172.17.02 -p 6235 $dockerCmd"
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                logOutDocker()
            }
        }
        success {
            script {
                notif "Pipeline succeed"
            }
        }
        failure {
            script {
                notif "Pipleine failed"
            }
        }
    }
}