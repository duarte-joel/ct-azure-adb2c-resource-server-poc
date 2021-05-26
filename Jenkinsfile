@Library('CxLibrary') _

mavenAppPipeline(
    // customParameters
    [
        // notificationsStatuses: ["success", "failure"],
        // notificationsWebhook: 'https://outlook.office.com/webhook/c00b1d32-9f5a-4fd0-ad21-92340e4f1fa3@882d47f6-50f4-4554-9aa6-43a46416b0f0/JenkinsCI/91d8e5cd795a46778576a8f4d101bc82/0b6b294b-7a8b-434f-ad47-8b52c4c2013e',
        //confluenceKey: '???',
        //confluenceAncestorId: '???',
        //confluenceCredentialsId: '???',
        ignoreTestFailure: 'false',
        junitTestResults: 'service-app/target/surefire-reports/*.xml',
        dockerRepositoryName: 'cx-ct-service-template',
        dockerBuildParameters: '-f ./service-app/Dockerfile ./service-app',
        deploySnapshots: true,
        sonarProjectKey: 'com.carlsberg.consumertech:service-template-app',
        unreleasedEnvironment:
        [
            enabled: true,
            awsCredentialsId: 'AWS-Dev',
            environmentRegion: 'eu-west-1',
            ecsService:
            [
                clusterName: 'dev-layer2-backend-01',
                serviceName: 'dev-service-cx-ct-service-template'
            ]
        ]
    ]
)

