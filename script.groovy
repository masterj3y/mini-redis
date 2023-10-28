def runTests() {
    gv.notif("Running tests")
    sh 'cargo test'
}

def buildProject() {
    gv.notif("Building project")
    sh 'cargo build --release'
}

def notif(message) {
    sh "RUST_LOG=debug ./notifier/telnotif -t $TEL_NOTIFIER_TOKEN -r 6488784421 -m \"$message\""
}

return this