pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh 'mvn clean install'
      }
    }
    stage('tests') {
      steps {
        sh 'mvn test'
      }
    }
  }
  post {
          always {
              junit 'build/reports/**/*.xml'
          }
      }
}