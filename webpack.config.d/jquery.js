config.plugins.push(new webpack.ProvidePlugin({
    $: "jquery",
    jQuery: "jquery"
}));

config.module.rules.push({
          test: require.resolve('jquery'),
          use: [{
              loader: 'expose-loader',
              options: 'jQuery'
          }]
})
