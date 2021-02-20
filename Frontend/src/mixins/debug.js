export default {
  methods: {
        // tl;dr - it's magic, just use it
        // console log bind thanks to https://stackoverflow.com/a/32928812/2533660
        // returns a function for console logging if in a development environment (where all logged statements are
        // preceded by '>>>'), otherwise returns a empty function that does nothing
        $dbg_console_log: (process.env.VUE_APP_SHOW_DEBUG=='true') ? console.log.bind(window.console,'>>>') : function(){}
      }
}