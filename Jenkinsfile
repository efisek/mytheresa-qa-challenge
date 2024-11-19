pipeline {
    agent any
    stages {
        stage('Load Config') {
            steps {
                script {
                    // to load the config.properties file
                    def props = readProperties file: 'src/main/resources/config.properties'

                    // to fetch the environments
                    env.LOCAL_ENV = 'local'
                    env.STAGING_ENV = 'staging'
                    env.DEFAULT_ENV = props['env'] ?: 'production'
                }
            }
        }
        stage('Run Tests for Local') {
            steps {
                sh 'docker pull pocketaces2/fashionhub-demo-app'
                echo "image is pulled"
                sh 'docker run -d --name fashionhub -p 4000:4000 pocketaces2/fashionhub-demo-app:latest'
                echo "Testing Local Environment"
                sh "mvn test -Denv=${env.LOCAL_ENV}"
                sh 'docker stop fashionhub'
                sh 'docker rm fashionhub'
            }
        }
        stage('Run Tests for Staging') {
            steps {
                echo "Testing Staging Environment"
                sh "mvn test -Denv=${env.STAGING_ENV}"
            }
        }
        stage('Run Tests for Production') {
            steps {
                echo "Testing Production Environment"
                sh "mvn test -Denv=${env.DEFAULT_ENV}"
            }
        }
    }

}
