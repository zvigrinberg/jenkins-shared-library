def test(String releaseName,String releaseNamespace)
{
    sh "helm test ${releaseName} -n ${releaseNamespace}"
}

def uninstall(String releaseName,String releaseNamespace)
{
    sh "helm uninstall ${releaseName} -n ${releaseNamespace}"
}

