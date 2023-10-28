def sc

pipeline {
    agent { label 'rust-agent' }
    environment {
        TEL_NOTIFIER_TOKEN = credentials("TEL_NOTIFIER_TOKEN")
    }
    stages {
        stage("init") {
            steps {
                script {
                    sc = load "script.groovy"
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
                    sc.runTests()
                }
            }
        }
        stage('build') {
            steps {
                script {
                    sc.buildProject()
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
                        sc.deploy()
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                sc.logOutOfRepository()
            }
        }
        success {
            script {
                sc.notif("Pipeline failed")
            }
        }
        failure {
            script {
                sc.notif("Pipleine succeed")
            }
        }
    }
}