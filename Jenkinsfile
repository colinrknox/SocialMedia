pipeline {
    agent any

    // Basic maven jar creation
    stages {
        stage('Build') {
            steps {
                sh 'cd backend/SocialMedia/'
                sh 'mvn clean package'
            }
        }
    }
}
