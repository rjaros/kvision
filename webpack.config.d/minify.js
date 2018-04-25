if (defined.PRODUCTION) {
    const UglifyJSPlugin = require('uglifyjs-webpack-plugin');

    config.optimization = {
        minimizer: [
             new UglifyJSPlugin({
                uglifyOptions: {
                    compress: {
                        unused: false
                    }
                }
             })
        ]
    }

}
