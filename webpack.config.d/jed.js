;(function() {
    const webpack = require('webpack')

    config.plugins.push(new webpack.ProvidePlugin({
	Jed: "jed"
    }));
})();
