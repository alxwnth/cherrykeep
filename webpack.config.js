const {resolve} = require("node:path");
const path = require("node:path");
const MiniCssExtractPlugin = require("mini-css-extract-plugin")
const CssMinimizerPlugin = require("css-minimizer-webpack-plugin");

module.exports = (env, argv) => ({
    entry: './src/main/resources/static/js/app.js',
    output: {
        path: resolve(__dirname, './target/classes/static'),
        filename: 'js/bundle.js'
    },
    devtool: argv.mode === 'production' ? false : 'eval-source-map',
    optimization: {
        minimize: true,
        minimizer: [
            new CssMinimizerPlugin()
        ]
    },
    plugins: [
        new MiniCssExtractPlugin({
            filename: "css/bundle.css"
        }),
    ],
    module: {
        rules: [
            {
                test: /\.js$/,
                include: path.resolve(__dirname, './src/main/resources/static/js'),
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env'],
                    },
                },
            },
            {
                test: /\.scss$/,
                use: [
                    // argv.mode === 'production' ? MiniCssExtractPlugin.loader : 'style-loader',
                    MiniCssExtractPlugin.loader,
                    {
                        loader: 'css-loader',
                        options: {
                            importLoaders: 1,
                            sourceMap: true
                        }
                    },
                    {
                        loader: 'sass-loader',
                        options: {sourceMap: true}
                    }
                ]
            },
            {
                test: /\.(woff|woff2|eot|ttf|otf)$/i,
                type: 'asset/resource',
            },
        ]
    },
    resolve: {
        modules: [
            path.resolve(__dirname, './src/main/resources'),
            'node_modules'
        ],
    },
    devServer: {
        port: 8081,
        compress: true,
        watchFiles: [
            'src/main/resources/templates/**/*.html',
            'src/main/resources/static/js/**/*.js',
            'src/main/resources/static/scss/**/*.scss'
        ],
        proxy: [{
            context: ['/'],
            target: 'http://localhost:8080',
            secure: false,
            prependPath: false,
            headers: {
                'X-Devserver': '1',
            }
        }]
    },
})
