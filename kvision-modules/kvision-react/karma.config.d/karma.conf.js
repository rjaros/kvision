config.set({
    reporters: ['karma-kotlin-reporter', 'junit'],
    junitReporter: {
        outputDir: '../../../../reports/junit',
        outputFile: 'kvision-react.xml',
        useBrowserName: false
    },
    captureTimeout: 360000,
    browserDisconnectTimeout: 360000,
    browserNoActivityTimeout: 360000
});
