pipeline {
    agent any

    stages {
        stage('Cloning git repo') {
            steps {
                sh "git clone https://github.com/rasc0l/SocialMedia.git"
                sh "ls -a"
            }
        }
        stage('Build') {
            steps {
                sh 'cd SocialMedia/backend/SocialMedia/'
                sh "ls -a"
                sh 'mvn clean package'
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
