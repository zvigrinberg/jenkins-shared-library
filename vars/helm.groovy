def uninstall(String releaseName,String releaseNamespace)
{
    sh "helm uninstall ${releaseName} -n ${releaseNamespace}"
}