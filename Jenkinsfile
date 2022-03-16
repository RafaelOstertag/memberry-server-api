pipeline {
    agent {
        label 'java11'
    }

    triggers {
        cron '@daily'
    }

    tools {
        maven 'Latest Maven'
    }

    options {
        ansiColor('xterm')
        buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '15')
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Build and Test') {
            steps {
                configFileProvider([configFile(fileId: 'b958fc4b-b1bd-4233-8692-c4a26a51c0f4', variable: 'MAVEN_SETTINGS_XML')]) {
                    sh 'mvn -B -s "$MAVEN_SETTINGS_XML" install'
                }
            }

            post {
                always {
                    junit '**/failsafe-reports/*.xml,**/surefire-reports/*.xml'
                }
            }
        }

        stage("Check Dependencies") {
            steps {
                configFileProvider([configFile(fileId: 'b958fc4b-b1bd-4233-8692-c4a26a51c0f4', variable: 'MAVEN_SETTINGS_XML')]) {
                    sh 'mvn -B -s "$MAVEN_SETTINGS_XML" -Psecurity-scan dependency-check:check'
                }
                dependencyCheckPublisher failedTotalCritical: 1, failedTotalHigh: 5, failedTotalLow: 8, failedTotalMedium: 8, pattern: 'target/dependency-check-report.xml', unstableTotalCritical: 0, unstableTotalHigh: 4, unstableTotalLow: 8, unstableTotalMedium: 8
            }
        }

        stage('Deploy to Nexus') {
            when {
                not {
                    triggeredBy "TimerTrigger"
                }
            }

            steps {
                configFileProvider([configFile(fileId: 'b958fc4b-b1bd-4233-8692-c4a26a51c0f4', variable: 'MAVEN_SETTINGS_XML')]) {
                    sh 'mvn -B -s "$MAVEN_SETTINGS_XML" -DskipTests deploy'
                }
            }
        }
    }

    post {
        unsuccessful {
            mail to: "rafi@guengel.ch",
                    subject: "${JOB_NAME} (${BRANCH_NAME};${env.BUILD_DISPLAY_NAME}) -- ${currentBuild.currentResult}",
                    body: "Refer to ${currentBuild.absoluteUrl}"
        }
    }
}
