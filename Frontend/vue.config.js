process.env.NODE_ENV === 'production'
module.exports = {
 publicPath: process.env.NODE_ENV === 'production'? '/EZBagWebapp/': '/',
  devServer: {
    disableHostCheck: true, 
    progress: false  
  }
}
