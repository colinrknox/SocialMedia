pipeline {
    agent any

    // Basic maven jar creation
    stages {
        stage('Build') {
            steps {
                cd backend/SocialMedia/
                mvn clean package
            }
        }
    }
}
