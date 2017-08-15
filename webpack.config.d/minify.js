if (defined.PRODUCTION) {
    config.plugins.push(new webpack.optimize.UglifyJsPlugin({
        minimize: true
    }));
}
