// webpack.config.js
const path = require('path')
const merge = require('webpack-merge')
const VueLoaderPlugin = require('vue-loader/lib/plugin')
const UglifyJsPlugin = require('uglifyjs-webpack-plugin')
const babelPolyfill = 'babel-polyfill'

module.exports = {
  entry: {
    stex: [babelPolyfill, './src/main/webapp/resources/js/application/stex.js']
  },
  output: {
    path: path.resolve(__dirname, 'src/main/resources/static'),
    chunkFilename: '[name].build.js',
    publicPath: '/build/',
    filename: '[name].build.js'
  },
  mode: 'development',
  module: {
    rules: [
      {
        enforce: 'pre',
        test: /¥.(js|vue|html)$/,
        loader: 'eslint-loader',
        exclude: /node_modules/,
        options: {
          fix: false,
          failOnWarning: false,
          failOnError: false
        }
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/
      },
      {
        test: /\.css$/,
        use: [
          'vue-style-loader',
          'css-loader'
        ]
      },
      {
        test: /¥.(png|jpg|gif|svg)$/,
        use: ['file-loader']
      },
      {
        test: /\.(otf|eot|svg|ttf|woff|woff2)(\?.+)?$/,
        loader: 'url-loader'
      },
    ]
  },
  resolve: {
    alias: {
      vue$: 'vue/dist/vue.esm.js'
    },
    extensions: ['*', '.js', '.vue', '.json']
  },
  performance: {
    hints: false
  },
  devtool: 'source-map',
  devServer: {
    port: 8081,
    proxy: {
      '/api/*': {
        target: 'http://localhost:8082/',
        changeOrigin: true
      }
    },
    contentBase: path.join(__dirname, 'src/main/resources/templates'),
    watchContentBase: true,
    hot: true,
    inline: true,
    historyApiFallback: true,
    noInfo: false,
    overlay: true,
    open: true,
    openPage: 'stex.html'
  },
  optimization: {
    splitChunks: {
      cacheGroups: {
        vendor: {
          test: /node_modules/,
          name: 'vendor',
          chunks: 'initial',
          enforce: true
        }
      }
    }
  },
  plugins: [
    new VueLoaderPlugin()
  ]
}

if (process.env.NODE_ENV === 'production'
) {
  module.exports = merge(module.exports, {
    mode: 'production',
    devtool: undefined,
    optimization: {
      minimizer: [
        new UglifyJsPlugin({
          parallel: true,
          uglifyOptions: {
            compress: true,
            ecma: 8,
            mangle: true
          },
          sourceMap: true
        })
      ]
    }
  })
}
