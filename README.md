## Docker Security Scanner Trend Reports - Jenkins Plugin ##

*   This Jenkins plugin generates the graph from the report generated from Docker Security Scanner Plugin.

## Prerequisites for the plugin to be operational ##

*	Docker Security Scanner Plugin is required to build in build step.
*   SeverityCount.properties file should be available in Jenkins master home directory.

## Usage of plugin in Jenkins ##

*	In the configuration page for your project, add "Docker Security Scanner Trend Reports" step from the "Post build step" dropdown list.
*   Provide the file path of properties file generated from Docker Security Scanner Plugin.
	
## Installing manually ##

*	Copy the *target/ Docker Security Scanner Trend Reports.hpi* file to *$JENKINS/plugins/* where *JENKINS* is the Jenkins root directory, by default it is */var/lib/jenkins/*.
