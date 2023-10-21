pipeline {
    agent { label 'rust-agent' }
    environment {
        TEL_NOTIFIER_TOKEN = credentials("TEL_NOTIFIER_TOKEN")
    }
    stages {
        stage('test') {
            steps {
                echo user $USER
                sh 'RUST_LOG=debug ./notifier/telnotif -t $TEL_NOTIFIER_TOKEN -r 6488784421 -m "running tests"'
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