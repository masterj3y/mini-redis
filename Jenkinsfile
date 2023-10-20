pipeline {
    agent any
    stages {
        stage('test') {
            steps {
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