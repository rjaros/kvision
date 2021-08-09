;(function() {
    const webpack = require('webpack')

    config.plugins.push(new webpack.ProvidePlugin({
	$: "jquery",
	jQuery: "jquery",
	"window.jQuery": "jquery"
    }));
})();
