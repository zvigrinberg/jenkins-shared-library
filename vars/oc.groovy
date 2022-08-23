

def createNewProject(String projectName)
{
    sh "oc new-project ${projectName}"
}

def createPullSecret(String secretName , String username, String password, String server, String namespace)
{
    sh "oc create secret docker-registry ${secretName} --docker-server=${server}    --docker-username=${username}    --docker-password=${password} -n ${namespace}"
}