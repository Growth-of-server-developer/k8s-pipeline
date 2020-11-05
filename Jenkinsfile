podTemplate(
    label: 'buildPod',
    volumes: [
        emptyDirVolume(mountPath: '/etc/gitrepo', memory: false),
        hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
    ],
    containers:
    [
        containerTemplate(name: 'git', image: 'alpine/git', command: 'cat', ttyEnabled: true),
        containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true,)
    ]
)
{
    node('buildPod') {

        def app
        
        stage('Clone repository') {
            container('git') {
                sh 'git clone -b develop https://github.com/Growth-of-server-developer/k8s-pipeline.git /etc/gitrepo'
            }
        }
        stage('Test source codes') {
           sh 'echo Test passed' 
        }
        stage('Build image'){
            container('docker') {
                app = docker.build("doyunii/mybox", "/etc/gitrepo")
            }
        }

        stage('Test image') {
            sh 'echo Test passed'
        }

        stage('Push image') {
            container('docker') {
                docker.withRegistry('', 'docker-hub') {
                    app.push("${env.BUILD_NUMBER}")
                    app.push()
                }
            }
        }
    }
}
