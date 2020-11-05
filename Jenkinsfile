pipeline {
    agnet any 
    tools {
        maven 'Maven3.6.3'
        docker 'docker-latest'
     }
    stages {
        stage('Test maven installation') {
            steps {
              sh 'mvn -version' 
            }
        }
        stage('Test Docker installation') {
            steps {
              sh 'docker --version'
            }
        }
    }
}

node {
     def app

     tools {
        maven 'Maven3.6.3'
        docker 'docker-latest'
     }

     stage('Clone repository') {
         /* Let's make sure we have the repository cloned to our workspace */

         checkout scm
     }

     stage('Build image') {
         /* This builds the actual image; synonymous to
         * docker build on the command line */

         app = docker.build("doyunii/mybox")
     }

     stage('Test image') {
         app.inside {
             sh 'echo "Tests passed"'
         }
     }

     stage('Push image') {
         /* Finally, we'll push the image with two tags:
         * First, the incremental build number from Jenkins
         * Second, the 'latest' tag.
         * Pushing multiple tags is cheap, as all the layers are reused. */
         docker.withRegistry('https://regstry.hub.docker.com', 'docker-hub') {
             app.push("${env.BUILD_NUMBER}")
             app.push("latest")
         }
     }
 }
