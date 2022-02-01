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
        stage('Build') {
            steps {
                sh 'mvn -X -f backend/SocialMedia/pom.xml clean package'
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
