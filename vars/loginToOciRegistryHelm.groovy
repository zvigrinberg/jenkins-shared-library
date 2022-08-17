def call(String server, String user, String password) {
    def result = sh(script: "helm registry login ${server} -u ${user} -p ${password} ", returnStdout: true).trim()
    echo "Response from connecting to helm OCI registry : \n ${result}"

}
