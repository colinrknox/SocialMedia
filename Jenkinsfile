pipeline {
    agent any
    
    options {
        skipDefaultCheckout(true)
    }
    
    tools {
        maven 'Maven 3.5.4'
        jdk 'jdk11'
    }

    stages {
        stage('Clean workspace') {
            steps {
                cleanWs()
                checkout scm
                echo "Building ${env.JOB_NAME}..."
            }
        }
        stage('Initialization') {
            steps{
                sh '''
                    echo "AWS_DB_ENDPOINT = ${AWS_DB_ENDPOINT}"
                    echo "AWS_USERNAME = ${AWS_USERNAME}"
                    echo "AWS_PASSWORD = ${AWS_PASSWORD}"
                    echo "AWS_BUCKET_NAME = ${AWS_BUCKET_NAME}"
                    echo "SPRING_EMAIL = ${SPRING_EMAIL}"
                    echo "SPRING_EMAIL_PD = ${SPRING_EMAIL_PD}"
                '''
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -f backend/SocialMedia/pom.xml clean package'
            }
        }
    }
    // Clean workspace with options
    post {
        always {
            cleanWs(cleanWhenNotBuilt: false,
                    deleteDirs: true,
                    disableDeferredWipeout: true,
                    notFailBuild: true,
                    patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                               [pattern: '.propsfile', type: 'EXCLUDE']])
        }
    }
}
