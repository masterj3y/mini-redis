def script

pipeline {
    agent { label 'rust-agent' }
    environment {
        TEL_NOTIFIER_TOKEN = credentials("TEL_NOTIFIER_TOKEN")
    }
    stages {
        stage("init") {
            steps {
                script {
                    script = load "script.groovy"
                }
            }
        }
        stage('test') {
            when {
                expression {
                    env.BRANCH_NAME == 'dev' || env.BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    script.runTests()
                }
            }
        }
        stage('build') {
            steps {
                script {
                    script.buildProject()
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
                    withCredentials(
                        [usernamePassword(
                            credentialsId: 'nexus-docker', 
                            usernameVariable: 'USER_NAME', 
                            passwordVariable: 'PASSWORD')
                        ]
                    ) {
                        script.deploy()
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                script.logOutOfRepository()
            }
        }
        success {
            script {
                script.notif("Pipeline failed")
            }
        }
        failure {
            script {
                script.notif("Pipleine succeed")
            }
        }
    }
}