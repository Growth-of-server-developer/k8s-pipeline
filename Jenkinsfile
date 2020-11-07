podTemplate(yaml: """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: git
    image: alpine/git
    command: ['cat']
    tty: true
    volumeMounts:
    - name: repo-volume
      mountPath: /etc/gitrepo
  - name: docker
    image: docker
    command: ['cat']
    tty: true
    volumeMounts:
    - name: docker-volume
      mountPath: /var/run/docker.sock
    env: 
      - name: DB_HOST
        valueFrom:
          configMapKeyRef:
            name: db-config
            key: host
      - name: DB_NAME
        valueFrom:
          configMapKeyRef:
            name: db-config
            key: name
      - name: DB_USER
        valueFrom:
          configMapKeyRef:
            name: db-config
            key: user
      - name: DB_PASSWORD
        valueFrom:
          configMapKeyRef:
            name: db-config
            key: password
  volumes:
  - name: repo-volume
    emptyDir: {}
  - name: docker-volume
    hostPath:
      path: /var/run/docker.sock
""")
{
    node(POD_LABEL) {

        def app
        
        stage('Clone repository') {
            container('git') {
                sh 'git clone -b develop https://github.com/Growth-of-server-developer/k8s-pipeline.git /etc/gitrepo'
            }
        }
        stage('Test source codes') {
            
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