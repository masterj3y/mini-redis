def runTests() {
    notif("Running tests")
    sh 'cargo test'
}

def buildProject() {
    notif("Building project")
    sh 'cargo build --release'
}

def deploy() {
    buildDockerImage()
    pushDockerImageToNexus()
}

def buildDockerImage() {
    notif("Building docker image")
    sh 'docker build -t localhost:8083/mini-redis:$BUILD_ID .'
    notif("Docker image has been built")
}

def pushDockerImageToNexus() {
    notif("Logging in repository")
    sh 'docker login -u $USER_NAME -p $PASSWORD http://localhost:8083'
    notif("Logged in repository")
    notif("Pushig image into repository")
    sh 'docker push localhost:8083/mini-redis:$BUILD_ID'
    notif("Image pushed into repository")
}

def logOutOfRepository() {
    notif("Logging out of repository")
    sh 'docker logout'
    notif("Logged out of repository")
}

def notif(message) {
    sh 'RUST_LOG=debug ./notifier/telnotif -t $TEL_NOTIFIER_TOKEN' + " -r 6488784421 -m \"$message\""
}

return this