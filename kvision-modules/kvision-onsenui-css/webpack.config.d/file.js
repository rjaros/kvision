config.module.rules.push(
    {
        test: /\.(jpe?g|png|gif|svg)$/i,
        loader: 'file-loader',
        options: {
              esModule: false,
        },
    }
);
