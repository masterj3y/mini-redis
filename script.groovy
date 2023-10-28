def notif(message) {
    sh "RUST_LOG=debug ./notifier/telnotif -t $TEL_NOTIFIER_TOKEN -r 6488784421 -m \"$message\""
}

return this