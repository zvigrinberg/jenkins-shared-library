package zvikag.jenkins
import zvikag.jenkins.utils.*

enum sideCarType{

    MONGO,
    WIREMOCK,
    KAFKA,
    MYSQL,
    POSTGRESQL
}

//Without sidecar
def call(String releaseName, String chartFileName, String releaseNamespace, String valuesPath, String secretValuesPath,  boolean enableDebug)
{
    GString installOptions = populateInstallOptions(secretValuesPath, enableDebug)
    def installationOutput = sh(script: "helm install ${releaseName} ${chartFileName}  -n ${releaseNamespace} --create-namespace -f ${valuesPath}  ${installOptions} ", returnStdout: true).trim()
}

//With sidecar
def call(String releaseName, String chartFileName, String releaseNamespace, String valuesPath, String secretValuesPath,  boolean enableDebug, boolean injectSideCars,boolean openshiftCluster)
{
    String sideCarsRepoLocation
    GString installOptions = populateInstallOptions(secretValuesPath, enableDebug)
    String postRendererScript
    if(injectSideCars)
    {
        sideCarsRepoLocation = bringSideCars();
        sh(script: "cp -r ${sideCarsRepoLocation}/side-cars .", returnStdout: true).trim()
        if(openshiftCluster)
        {
            postRendererScript = "./side-cars/kustomize/kustomize-openshift"
        }
//            regular kubernetes cluster
        else
        {
            postRendererScript = "./side-cars/kustomize/kustomize"
        }
    }
    def installationOutput = sh(script: "helm install ${releaseName} ${chartFileName}  -n ${releaseNamespace} --create-namespace -f ${valuesPath}  ${installOptions} --post-renderer= ${postRendererScript}", returnStdout: true).trim()

}

private GString populateInstallOptions(String secretValuesPath, boolean enableDebug) {
    GString installOptions = GString.EMPTY;
    if (secretValuesPath.trim() != "")
        installOptions = "$installOptions -f ${secretValuesPath}"
    if (enableDebug) {
        installOptions = "$installOptions --debug"
    }
    return installOptions
}




private String bringSideCars() {

    String gitRepo = "https://github.com/zvigrinberg/jenkins-resources.git"
    def gitClone = sh(script: "git clone ${gitRepo}", returnStdout: true).trim()
    int position = gitRepo.lastIndexOf('/')
    String repoNamePlusGit = gitRepo.substring(position + 1)
    int gitExtensionPos = repoNamePlusGit.indexOf(".git")
    String repoName = repoNamePlusGit.substring(0,gitExtensionPos)
    def repoPathInWorkspace = sh(script: "cd ${repoName} ; git rev-parse --show-toplevel", returnStdout: true).trim()
    return repoPathInWorkspace








}