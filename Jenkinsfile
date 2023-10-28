def gv

pipeline {
    agent { label 'rust-agent' }
    environment {
        TEL_NOTIFIER_TOKEN = credentials("TEL_NOTIFIER_TOKEN")
    }
    stages {
        stage("init") {
            steps {
                gv = load("script.groovy")
            }
        }
        stage('test') {
            when {
                expression {
                    BRANCH_NAME == 'dev' || BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    gv.notify("running tests")
                    sh 'cargo test'
                }
            }
        }
        stage('build') {
            steps {
                sh 'RUST_LOG=debug ./notifier/telnotif -t $TEL_NOTIFIER_TOKEN -r 6488784421 -m "building project"'
                sh 'cargo build --release'
            }
        }
        stage('build image') {
            when {
                expression {
                    BRANCH_NAME == 'dev' || BRANCH_NAME == 'master'
                }
            }
            steps {
                withCredentials(
                    [
                        usernamePassword(
                            credentialsId: 'nexus-docker', 
                            usernameVariable: 'USER_NAME', 
                            passwordVariable: 'PASSWORD'
                        )
                    ]
                ) {
                    sh 'RUST_LOG=debug ./notifier/telnotif -t $TEL_NOTIFIER_TOKEN -r 6488784421 -m "building image"'
                    sh 'docker build -t localhost:8083/mini-redis:$BUILD_ID .'
                    sh 'docker login -u $USER_NAME -p $PASSWORD http://localhost:8083'
                    sh 'RUST_LOG=debug ./notifier/telnotif -t $TEL_NOTIFIER_TOKEN -r 6488784421 -m "pushing image"'
                    sh 'docker push localhost:8083/mini-redis:$BUILD_ID'
                }
            }
        }
    }
    post {
        always {
            sh 'docker logout'
        }
        success {
            sh 'RUST_LOG=debug ./notifier/telnotif -t $TEL_NOTIFIER_TOKEN -r 6488784421 -m "build succeed"'
        }
        failure {
            sh 'RUST_LOG=debug ./notifier/telnotif -t $TEL_NOTIFIER_TOKEN -r 6488784421 -m "pipline failed"'
        }
    }
}