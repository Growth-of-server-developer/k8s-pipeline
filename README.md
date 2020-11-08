[![Build Status](http://jenkins:8080/jenkins/job/demo/badge/icon)](http://jenkins:8080/jenkins/job/demo/)
[![App Status](https://doyuni-argo.duckdns.org/api/badge?name=covid-demo&revision=true)](https://doyuni-argo.duckdns.org/applications/covid-demo)
# k8s-pipeline
Experience CI/CD pipeline with kubernetes 

- CI 
  - Jenkins 
    - Loacal Host + Docker + ngrok   
     problem: different URL whenever restarting -> It's annoying to register github webhook every time.
    - Google Cloud + Kubernetes
 
- Continuous Delivery
  - Nexus repository
  
- CD (Continuous Deployment)
  - k8s
  - Performance Test
    - nGrinder
    - JBoss & Gatling
    
- APM
  - Grafana(Visualizing) & Prometheus
