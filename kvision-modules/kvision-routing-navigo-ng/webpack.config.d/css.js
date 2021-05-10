config.module.rules.push({ test: /\.css$/, use: ["style-loader", { loader: "css-loader", options: {sourceMap: false} } ] });

