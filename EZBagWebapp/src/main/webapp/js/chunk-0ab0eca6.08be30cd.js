(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0ab0eca6"],{1148:function(t,e,r){"use strict";var n=r("a691"),a=r("1d80");t.exports="".repeat||function(t){var e=String(a(this)),r="",o=n(t);if(o<0||o==1/0)throw RangeError("Wrong number of repetitions");for(;o>0;(o>>>=1)&&(e+=e))1&o&&(r+=e);return r}},"159b":function(t,e,r){var n=r("da84"),a=r("fdbc"),o=r("17c2"),c=r("9112");for(var i in a){var s=n[i],f=s&&s.prototype;if(f&&f.forEach!==o)try{c(f,"forEach",o)}catch(u){f.forEach=o}}},"17c2":function(t,e,r){"use strict";var n=r("b727").forEach,a=r("a640"),o=r("ae40"),c=a("forEach"),i=o("forEach");t.exports=c&&i?[].forEach:function(t){return n(this,t,arguments.length>1?arguments[1]:void 0)}},3560:function(t,e,r){"use strict";r.r(e);var n=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{attrs:{id:"store"}},[r("Carts",{attrs:{carts:t.carts},on:{verify:t.markPaid}})],1)},a=[];r("99af"),r("a9e3");function o(t,e){(null==e||e>t.length)&&(e=t.length);for(var r=0,n=new Array(e);r<e;r++)n[r]=t[r];return n}function c(t){if(Array.isArray(t))return o(t)}r("a4d3"),r("e01a"),r("d28b"),r("a630"),r("d3b7"),r("3ca3"),r("ddb0");function i(t){if("undefined"!==typeof Symbol&&Symbol.iterator in Object(t))return Array.from(t)}r("fb6a"),r("b0c0"),r("25f0");function s(t,e){if(t){if("string"===typeof t)return o(t,e);var r=Object.prototype.toString.call(t).slice(8,-1);return"Object"===r&&t.constructor&&(r=t.constructor.name),"Map"===r||"Set"===r?Array.from(t):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?o(t,e):void 0}}function f(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}function u(t){return c(t)||i(t)||s(t)||f()}var d=r("5530"),l=r("2f62"),b=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",t._l(t.carts.slice().reverse(),(function(e){return r("div",{key:e.cartHash,staticClass:"carts"},[r("div",{staticClass:"cart",class:{"is-paid":e.verified},on:{click:function(r){return t.expandCart(e)}}},[r("h4",[r("v-icon",[t._v("mdi-cart")]),t._v(t._s(e.cartHash.substring(e.cartHash.length-3)))],1),r("h5",[t._v(" "+t._s(e.dt)+" ")]),r("small",[r("p",[r("v-btn",{on:{click:[function(t){t.stopPropagation()},function(r){return t.markPaid(e)}]}},[t._v("Verify")]),1==e.expanded?r("u",[r("v-icon",{staticClass:"chevron",attrs:{size:"50"}},[t._v("mdi-chevron-up")])],1):r("u",[r("v-icon",{staticClass:"chevron",attrs:{size:"50"}},[t._v("mdi-chevron-down")])],1),r("b",[t._v("Total: $"+t._s(t.showTwoDecimal(e.total)))])],1)]),1==e.expanded?r("div",t._l(e.names,(function(n,a){return r("div",{key:n,staticClass:"cartItem"},[t._v(" ("+t._s(e.quantities[a])+") "+t._s(n)+" ")])})),0):t._e()])])})),0)},h=[],p=(r("b680"),{name:"Carts",props:["carts"],components:{},computed:Object(l["c"])(["getBusinessID"]),methods:Object(d["a"])(Object(d["a"])({},Object(l["b"])(["verifyCart"])),{},{showTwoDecimal:function(t){return t.toFixed(2)},expandCart:function(t){t.expanded=!t.expanded},markPaid:function(t){var e=this,r=t.cartHash,n=this.getBusinessID;this.verifyCart({businessID:n,cartHash:r}).then((function(r){e.$dbg_console_log(r),1==r.success?t.verified=!0:console.log("Failed")})).catch((function(t){e.$dbg_console_log(t)})),this.$dbg_console_log("mark paid")}})}),v=p,g=(r("a180"),r("2877")),m=r("6544"),y=r.n(m),w=r("8336"),x=r("132d"),_=Object(g["a"])(v,b,h,!1,null,"28e1e92c",null),E=_.exports;y()(_,{VBtn:w["a"],VIcon:x["a"]});var O=r("a78e"),I=r.n(O),A={name:"store",components:{Carts:E},mounted:function(){var t=this;this.$emit("toggleHeader","business");var e=I.a.get("token"),r=this.getBusinessID;console.log(r),console.log("AUTH",e),this.fetchCarts(r).then((function(e){if(t.$dbg_console_log(e),1==e.success){t.$dbg_console_log("Successful fetch of carts");for(var r=e.carts.length,n=0;n<r;n++){e.carts[n]["expanded"]=!1;var a=Number(e.carts[n]["time"]["$numberLong"]);e.carts[n]["dt"]=new Date(a).toLocaleString()}t.carts=e.carts}else console.log("Failed")})).catch((function(e){t.show_popup=!0,t.popupHeader="Internal Server Error",t.popupText="Something went wrong",t.carts=t.debugCarts,t.$dbg_console_log(e)}))},data:function(){return{carts:[],debugCarts:[{barcodes:["12345678","12345678"],names:["Tooth brush","Extra gum"],quantities:[3,2],businessID:"cad1ab052ffff19ff3f595c569f7a37f826921d07c4262946d81ef04ec72d727",subtotal:34.95,session:"2",tax:23.08,total:58.03,time:{$numberLong:"1614190765106"},cartHash:"meeauCHFaWFP6lCPEsyM",verified:!1,expanded:!1},{barcodes:["12345678","12345678"],names:["Tooth brush","Extra gum"],quantities:[3,2],businessID:"cad1ab052ffff19ff3f595c569f7a37f826921d07c4262946d81ef04ec72d727",subtotal:34.95,session:"2",tax:23.08,total:58.03,time:{$numberLong:"1614190804970"},cartHash:"wLtqzC34bfFaxErqnVW2",verified:!1,expanded:!1},{barcodes:["12345678","12345678"],names:["Tooth brush","Extra gum"],quantities:[3,2],businessID:"cad1ab052ffff19ff3f595c569f7a37f826921d07c4262946d81ef04ec72d727",subtotal:34.95,session:"2",tax:23.08,total:58.03,time:{$numberLong:"1614190805635"},cartHash:"KpaE791tFEiK2zC2P2Fx",verified:!1,expanded:!1}]}},computed:Object(l["c"])(["getBusinessID"]),methods:Object(d["a"])(Object(d["a"])({},Object(l["b"])(["fetchCarts"])),{},{markPaid:function(){this.$dbg_console_log("mark paid"),this.cart.paid=!this.cart.paid},addCart:function(t){this.cart=[].concat(u(this.carts),[t])}})},j=A,C=Object(g["a"])(j,n,a,!1,null,null,null);e["default"]=C.exports},"408a":function(t,e,r){var n=r("c6b6");t.exports=function(t){if("number"!=typeof t&&"Number"!=n(t))throw TypeError("Incorrect invocation");return+t}},4160:function(t,e,r){"use strict";var n=r("23e7"),a=r("17c2");n({target:"Array",proto:!0,forced:[].forEach!=a},{forEach:a})},"4df4":function(t,e,r){"use strict";var n=r("0366"),a=r("7b0b"),o=r("9bdd"),c=r("e95a"),i=r("50c4"),s=r("8418"),f=r("35a1");t.exports=function(t){var e,r,u,d,l,b,h=a(t),p="function"==typeof this?this:Array,v=arguments.length,g=v>1?arguments[1]:void 0,m=void 0!==g,y=f(h),w=0;if(m&&(g=n(g,v>2?arguments[2]:void 0,2)),void 0==y||p==Array&&c(y))for(e=i(h.length),r=new p(e);e>w;w++)b=m?g(h[w],w):h[w],s(r,w,b);else for(d=y.call(h),l=d.next,r=new p;!(u=l.call(d)).done;w++)b=m?o(d,g,[u.value,w],!0):u.value,s(r,w,b);return r.length=w,r}},5530:function(t,e,r){"use strict";r.d(e,"a",(function(){return o}));r("a4d3"),r("4de4"),r("4160"),r("e439"),r("dbb4"),r("b64b"),r("159b");function n(t,e,r){return e in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function a(t,e){var r=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),r.push.apply(r,n)}return r}function o(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{};e%2?a(Object(r),!0).forEach((function(e){n(t,e,r[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(r)):a(Object(r)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(r,e))}))}return t}},5899:function(t,e){t.exports="\t\n\v\f\r                　\u2028\u2029\ufeff"},"58a8":function(t,e,r){var n=r("1d80"),a=r("5899"),o="["+a+"]",c=RegExp("^"+o+o+"*"),i=RegExp(o+o+"*$"),s=function(t){return function(e){var r=String(n(e));return 1&t&&(r=r.replace(c,"")),2&t&&(r=r.replace(i,"")),r}};t.exports={start:s(1),end:s(2),trim:s(3)}},8418:function(t,e,r){"use strict";var n=r("c04e"),a=r("9bf2"),o=r("5c6c");t.exports=function(t,e,r){var c=n(e);c in t?a.f(t,c,o(0,r)):t[c]=r}},"99af":function(t,e,r){"use strict";var n=r("23e7"),a=r("d039"),o=r("e8b5"),c=r("861d"),i=r("7b0b"),s=r("50c4"),f=r("8418"),u=r("65f0"),d=r("1dde"),l=r("b622"),b=r("2d00"),h=l("isConcatSpreadable"),p=9007199254740991,v="Maximum allowed index exceeded",g=b>=51||!a((function(){var t=[];return t[h]=!1,t.concat()[0]!==t})),m=d("concat"),y=function(t){if(!c(t))return!1;var e=t[h];return void 0!==e?!!e:o(t)},w=!g||!m;n({target:"Array",proto:!0,forced:w},{concat:function(t){var e,r,n,a,o,c=i(this),d=u(c,0),l=0;for(e=-1,n=arguments.length;e<n;e++)if(o=-1===e?c:arguments[e],y(o)){if(a=s(o.length),l+a>p)throw TypeError(v);for(r=0;r<a;r++,l++)r in o&&f(d,l,o[r])}else{if(l>=p)throw TypeError(v);f(d,l++,o)}return d.length=l,d}})},"9bdd":function(t,e,r){var n=r("825a"),a=r("2a62");t.exports=function(t,e,r,o){try{return o?e(n(r)[0],r[1]):e(r)}catch(c){throw a(t),c}}},a180:function(t,e,r){"use strict";r("f5da")},a630:function(t,e,r){var n=r("23e7"),a=r("4df4"),o=r("1c7e"),c=!o((function(t){Array.from(t)}));n({target:"Array",stat:!0,forced:c},{from:a})},a9e3:function(t,e,r){"use strict";var n=r("83ab"),a=r("da84"),o=r("94ca"),c=r("6eeb"),i=r("5135"),s=r("c6b6"),f=r("7156"),u=r("c04e"),d=r("d039"),l=r("7c73"),b=r("241c").f,h=r("06cf").f,p=r("9bf2").f,v=r("58a8").trim,g="Number",m=a[g],y=m.prototype,w=s(l(y))==g,x=function(t){var e,r,n,a,o,c,i,s,f=u(t,!1);if("string"==typeof f&&f.length>2)if(f=v(f),e=f.charCodeAt(0),43===e||45===e){if(r=f.charCodeAt(2),88===r||120===r)return NaN}else if(48===e){switch(f.charCodeAt(1)){case 66:case 98:n=2,a=49;break;case 79:case 111:n=8,a=55;break;default:return+f}for(o=f.slice(2),c=o.length,i=0;i<c;i++)if(s=o.charCodeAt(i),s<48||s>a)return NaN;return parseInt(o,n)}return+f};if(o(g,!m(" 0o1")||!m("0b1")||m("+0x1"))){for(var _,E=function(t){var e=arguments.length<1?0:t,r=this;return r instanceof E&&(w?d((function(){y.valueOf.call(r)})):s(r)!=g)?f(new m(x(e)),r,E):x(e)},O=n?b(m):"MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,EPSILON,isFinite,isInteger,isNaN,isSafeInteger,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,parseFloat,parseInt,isInteger".split(","),I=0;O.length>I;I++)i(m,_=O[I])&&!i(E,_)&&p(E,_,h(m,_));E.prototype=y,y.constructor=E,c(a,g,E)}},b64b:function(t,e,r){var n=r("23e7"),a=r("7b0b"),o=r("df75"),c=r("d039"),i=c((function(){o(1)}));n({target:"Object",stat:!0,forced:i},{keys:function(t){return o(a(t))}})},b680:function(t,e,r){"use strict";var n=r("23e7"),a=r("a691"),o=r("408a"),c=r("1148"),i=r("d039"),s=1..toFixed,f=Math.floor,u=function(t,e,r){return 0===e?r:e%2===1?u(t,e-1,r*t):u(t*t,e/2,r)},d=function(t){var e=0,r=t;while(r>=4096)e+=12,r/=4096;while(r>=2)e+=1,r/=2;return e},l=s&&("0.000"!==8e-5.toFixed(3)||"1"!==.9.toFixed(0)||"1.25"!==1.255.toFixed(2)||"1000000000000000128"!==(0xde0b6b3a7640080).toFixed(0))||!i((function(){s.call({})}));n({target:"Number",proto:!0,forced:l},{toFixed:function(t){var e,r,n,i,s=o(this),l=a(t),b=[0,0,0,0,0,0],h="",p="0",v=function(t,e){var r=-1,n=e;while(++r<6)n+=t*b[r],b[r]=n%1e7,n=f(n/1e7)},g=function(t){var e=6,r=0;while(--e>=0)r+=b[e],b[e]=f(r/t),r=r%t*1e7},m=function(){var t=6,e="";while(--t>=0)if(""!==e||0===t||0!==b[t]){var r=String(b[t]);e=""===e?r:e+c.call("0",7-r.length)+r}return e};if(l<0||l>20)throw RangeError("Incorrect fraction digits");if(s!=s)return"NaN";if(s<=-1e21||s>=1e21)return String(s);if(s<0&&(h="-",s=-s),s>1e-21)if(e=d(s*u(2,69,1))-69,r=e<0?s*u(2,-e,1):s/u(2,e,1),r*=4503599627370496,e=52-e,e>0){v(0,r),n=l;while(n>=7)v(1e7,0),n-=7;v(u(10,n,1),0),n=e-1;while(n>=23)g(1<<23),n-=23;g(1<<n),v(1,1),g(2),p=m()}else v(0,r),v(1<<-e,0),p=m()+c.call("0",l);return l>0?(i=p.length,p=h+(i<=l?"0."+c.call("0",l-i)+p:p.slice(0,i-l)+"."+p.slice(i-l))):p=h+p,p}})},dbb4:function(t,e,r){var n=r("23e7"),a=r("83ab"),o=r("56ef"),c=r("fc6a"),i=r("06cf"),s=r("8418");n({target:"Object",stat:!0,sham:!a},{getOwnPropertyDescriptors:function(t){var e,r,n=c(t),a=i.f,f=o(n),u={},d=0;while(f.length>d)r=a(n,e=f[d++]),void 0!==r&&s(u,e,r);return u}})},e439:function(t,e,r){var n=r("23e7"),a=r("d039"),o=r("fc6a"),c=r("06cf").f,i=r("83ab"),s=a((function(){c(1)})),f=!i||s;n({target:"Object",stat:!0,forced:f,sham:!i},{getOwnPropertyDescriptor:function(t,e){return c(o(t),e)}})},f5da:function(t,e,r){},fb6a:function(t,e,r){"use strict";var n=r("23e7"),a=r("861d"),o=r("e8b5"),c=r("23cb"),i=r("50c4"),s=r("fc6a"),f=r("8418"),u=r("b622"),d=r("1dde"),l=r("ae40"),b=d("slice"),h=l("slice",{ACCESSORS:!0,0:0,1:2}),p=u("species"),v=[].slice,g=Math.max;n({target:"Array",proto:!0,forced:!b||!h},{slice:function(t,e){var r,n,u,d=s(this),l=i(d.length),b=c(t,l),h=c(void 0===e?l:e,l);if(o(d)&&(r=d.constructor,"function"!=typeof r||r!==Array&&!o(r.prototype)?a(r)&&(r=r[p],null===r&&(r=void 0)):r=void 0,r===Array||void 0===r))return v.call(d,b,h);for(n=new(void 0===r?Array:r)(g(h-b,0)),u=0;b<h;b++,u++)b in d&&f(n,u,d[b]);return n.length=u,n}})}}]);
//# sourceMappingURL=chunk-0ab0eca6.08be30cd.js.map