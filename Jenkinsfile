pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                cd backend/SocialMedia/
                mvn clean package
            }
        }
    }
}