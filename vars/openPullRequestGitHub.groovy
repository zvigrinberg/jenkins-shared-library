/**
 * param: ff
 *
 */
def call(String gitHubToken,String gitHubAccountOrganizationName, String gitRepoName, String sourceBranch, String targetBranch, String title)
{
    String prNumber
    stage('Open PR to main branch')
            {
                prNumber = sh(script: "curl -X POST -H \"Accept: application/vnd.github+json\" -H \"Authorization: token ${gitHubToken}\" https://api.github.com/repos/${gitHubAccountOrganizationName}/${gitRepoName}/pulls -d '{\"title\": ${title} ,\"head\": \"${sourceBranch}\",\"base\": \"${targetBranch}\"}' | jq .number ", returnStdout: true).trim()
                echo "Number of PR Created : "
                echo "$prNumber"
                return prNumber

            }
}