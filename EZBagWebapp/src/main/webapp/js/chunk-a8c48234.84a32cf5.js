(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-a8c48234"],{"24b2":function(t,e,s){"use strict";var n=s("80d2"),i=s("2b0e");e["a"]=i["a"].extend({name:"measurable",props:{height:[Number,String],maxHeight:[Number,String],maxWidth:[Number,String],minHeight:[Number,String],minWidth:[Number,String],width:[Number,String]},computed:{measurableStyles(){const t={},e=Object(n["f"])(this.height),s=Object(n["f"])(this.minHeight),i=Object(n["f"])(this.minWidth),r=Object(n["f"])(this.maxHeight),o=Object(n["f"])(this.maxWidth),a=Object(n["f"])(this.width);return e&&(t.height=e),s&&(t.minHeight=s),i&&(t.minWidth=i),r&&(t.maxHeight=r),o&&(t.maxWidth=o),a&&(t.width=a),t}}})},"25a8":function(t,e,s){},"5b54":function(t,e,s){"use strict";s("6907")},6907:function(t,e,s){},"7e2b":function(t,e,s){"use strict";var n=s("2b0e");function i(t){return function(e,s){for(const n in s)Object.prototype.hasOwnProperty.call(e,n)||this.$delete(this.$data[t],n);for(const n in e)this.$set(this.$data[t],n,e[n])}}e["a"]=n["a"].extend({data:()=>({attrs$:{},listeners$:{}}),created(){this.$watch("$attrs",i("attrs$"),{immediate:!0}),this.$watch("$listeners",i("listeners$"),{immediate:!0})}})},"8dd9":function(t,e,s){"use strict";s("25a8");var n=s("7e2b"),i=s("a9ad"),r=s("2b0e"),o=r["a"].extend({name:"elevatable",props:{elevation:[Number,String]},computed:{computedElevation(){return this.elevation},elevationClasses(){const t=this.computedElevation;return null==t||isNaN(parseInt(t))?{}:{["elevation-"+this.elevation]:!0}}}}),a=s("24b2"),c=s("a236"),l=s("7560"),u=s("58df");e["a"]=Object(u["a"])(n["a"],i["a"],o,a["a"],c["a"],l["a"]).extend({name:"v-sheet",props:{outlined:Boolean,shaped:Boolean,tag:{type:String,default:"div"}},computed:{classes(){return{"v-sheet":!0,"v-sheet--outlined":this.outlined,"v-sheet--shaped":this.shaped,...this.themeClasses,...this.elevationClasses,...this.roundedClasses}},styles(){return this.measurableStyles}},render(t){const e={class:this.classes,style:this.styles,on:this.listeners$};return t(this.tag,this.setBackgroundColor(this.color,e),this.$slots.default)}})},a236:function(t,e,s){"use strict";var n=s("2b0e");e["a"]=n["a"].extend({name:"roundable",props:{rounded:[Boolean,String],tile:Boolean},computed:{roundedClasses(){const t=[],e="string"===typeof this.rounded?String(this.rounded):!0===this.rounded;if(this.tile)t.push("rounded-0");else if("string"===typeof e){const s=e.split(" ");for(const e of s)t.push("rounded-"+e)}else e&&t.push("rounded");return t.length>0?{[t.join(" ")]:!0}:{}}}})},a9ad:function(t,e,s){"use strict";var n=s("2b0e"),i=s("d9bd"),r=s("7bc6");e["a"]=n["a"].extend({name:"colorable",props:{color:String},methods:{setBackgroundColor(t,e={}){return"string"===typeof e.style?(Object(i["b"])("style must be an object",this),e):"string"===typeof e.class?(Object(i["b"])("class must be an object",this),e):(Object(r["d"])(t)?e.style={...e.style,"background-color":""+t,"border-color":""+t}:t&&(e.class={...e.class,[t]:!0}),e)},setTextColor(t,e={}){if("string"===typeof e.style)return Object(i["b"])("style must be an object",this),e;if("string"===typeof e.class)return Object(i["b"])("class must be an object",this),e;if(Object(r["d"])(t))e.style={...e.style,color:""+t,"caret-color":""+t};else if(t){const[s,n]=t.toString().trim().split(" ",2);e.class={...e.class,[s+"--text"]:!0},n&&(e.class["text--"+n]=!0)}return e}}})},c624:function(t,e,s){"use strict";s.r(e);var n=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("div",{staticClass:"center"},[s("v-app-bar",{attrs:{app:"","elevate-on-scroll":"",color:"primary",dark:""}},[s("p",[t._v("EZBag")])]),s("p",[t._v("Sorry, you don't have permission to access this page.")])],1)])},i=[],r={},o=r,a=(s("5b54"),s("2877")),c=s("6544"),l=s.n(c),u=s("40dc"),d=Object(a["a"])(o,n,i,!1,null,"34e8bffe",null);e["default"]=d.exports;l()(d,{VAppBar:u["a"]})},d9f7:function(t,e,s){"use strict";s.d(e,"a",(function(){return o}));var n=s("80d2");const i={styleList:/;(?![^(]*\))/g,styleProp:/:(.*)/};function r(t){const e={};for(const s of t.split(i.styleList)){let[t,r]=s.split(i.styleProp);t=t.trim(),t&&("string"===typeof r&&(r=r.trim()),e[Object(n["c"])(t)]=r)}return e}function o(){const t={};let e,s=arguments.length;while(s--)for(e of Object.keys(arguments[s]))switch(e){case"class":case"directives":arguments[s][e]&&(t[e]=c(t[e],arguments[s][e]));break;case"style":arguments[s][e]&&(t[e]=a(t[e],arguments[s][e]));break;case"staticClass":if(!arguments[s][e])break;void 0===t[e]&&(t[e]=""),t[e]&&(t[e]+=" "),t[e]+=arguments[s][e].trim();break;case"on":case"nativeOn":arguments[s][e]&&(t[e]=l(t[e],arguments[s][e]));break;case"attrs":case"props":case"domProps":case"scopedSlots":case"staticStyle":case"hook":case"transition":if(!arguments[s][e])break;t[e]||(t[e]={}),t[e]={...arguments[s][e],...t[e]};break;default:t[e]||(t[e]=arguments[s][e])}return t}function a(t,e){return t?e?(t=Object(n["x"])("string"===typeof t?r(t):t),t.concat("string"===typeof e?r(e):e)):t:e}function c(t,e){return e?t&&t?Object(n["x"])(t).concat(e):e:t}function l(...t){if(!t[0])return t[1];if(!t[1])return t[0];const e={};for(let s=2;s--;){const n=t[s];for(const t in n)n[t]&&(e[t]?e[t]=[].concat(n[t],e[t]):e[t]=n[t])}return e}},f2e7:function(t,e,s){"use strict";s.d(e,"b",(function(){return i}));var n=s("2b0e");function i(t="value",e="input"){return n["a"].extend({name:"toggleable",model:{prop:t,event:e},props:{[t]:{required:!1}},data(){return{isActive:!!this[t]}},watch:{[t](t){this.isActive=!!t},isActive(s){!!s!==this[t]&&this.$emit(e,s)}}})}const r=i();e["a"]=r},fe6c:function(t,e,s){"use strict";s.d(e,"b",(function(){return o}));var n=s("2b0e"),i=s("80d2");const r={absolute:Boolean,bottom:Boolean,fixed:Boolean,left:Boolean,right:Boolean,top:Boolean};function o(t=[]){return n["a"].extend({name:"positionable",props:t.length?Object(i["j"])(r,t):r})}e["a"]=o()}}]);
//# sourceMappingURL=chunk-a8c48234.84a32cf5.js.map