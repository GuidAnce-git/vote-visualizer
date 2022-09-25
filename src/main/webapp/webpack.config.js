const Dotenv = require('dotenv-webpack');
module.exports = {
    devServer: {
        historyApiFallback: true,
        contentBase: './',
        hot: true
    },
    plugins: [
        new Dotenv()
    ]
}