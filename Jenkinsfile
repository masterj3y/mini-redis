pipeline {
    agent any
    environment {
        TEL_NOTIFIER_TOKEN = credentials("TEL_NOTIFIER_TOKEN")
    }
    stages {
        stage('test') {
            steps {
                sh 'RUST_LOG=debug ./notifier/telnotif -t TEL_NOTIFIER_TOKEN -r 6488784421 -m "running tests"'
                sh 'cargo test'
            }
        }
        stage('build') {
            steps {
                sh 'cargo build'
            }
        }
    }
}