config.module.rules.push({test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?$/, loader: 'url-loader?limit=10000&mimetype=application/font-woff'});
config.module.rules.push({test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: 'url-loader?limit=10000&mimetype=application/octet-stream'});
config.module.rules.push({test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, loader: 'file-loader'});
config.module.rules.push({test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: 'url-loader?limit=10000&mimetype=image/svg+xml'});
