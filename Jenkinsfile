pipeline {
    agent any
    stages {
        stage('test') {
            sh 'cargo test'
        }
        stage('build') {
            sh 'cargo build'
        }
    }
}