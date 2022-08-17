def call(String clusterAddress,String ocpToken,String caLocation) {
    stage('Authenticate to Openshift Cluster') {
        def result = sh(script: "oc login --token ${ocpToken} --server=${clusterAddress} --certificate-authority=${caLocation}", returnStdout: true).trim()
        echo "Response from connecting to openshift cluster : \n ${result}"
    }
}
