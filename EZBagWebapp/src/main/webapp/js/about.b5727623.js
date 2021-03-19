(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["about"],{"0fd9":function(t,e,n){"use strict";n("4b85");var o=n("2b0e"),r=n("d9f7"),a=n("80d2");const c=["sm","md","lg","xl"],i=["start","end","center"];function s(t,e){return c.reduce((n,o)=>(n[t+Object(a["w"])(o)]=e(),n),{})}const u=t=>[...i,"baseline","stretch"].includes(t),l=s("align",()=>({type:String,default:null,validator:u})),d=t=>[...i,"space-between","space-around"].includes(t),f=s("justify",()=>({type:String,default:null,validator:d})),p=t=>[...i,"space-between","space-around","stretch"].includes(t),b=s("alignContent",()=>({type:String,default:null,validator:p})),v={align:Object.keys(l),justify:Object.keys(f),alignContent:Object.keys(b)},h={align:"align",justify:"justify",alignContent:"align-content"};function g(t,e,n){let o=h[t];if(null!=n){if(e){const n=e.replace(t,"");o+="-"+n}return o+="-"+n,o.toLowerCase()}}const m=new Map;e["a"]=o["a"].extend({name:"v-row",functional:!0,props:{tag:{type:String,default:"div"},dense:Boolean,noGutters:Boolean,align:{type:String,default:null,validator:u},...l,justify:{type:String,default:null,validator:d},...f,alignContent:{type:String,default:null,validator:p},...b},render(t,{props:e,data:n,children:o}){let a="";for(const r in e)a+=String(e[r]);let c=m.get(a);if(!c){let t;for(t in c=[],v)v[t].forEach(n=>{const o=e[n],r=g(t,n,o);r&&c.push(r)});c.push({"no-gutters":e.noGutters,"row--dense":e.dense,["align-"+e.align]:e.align,["justify-"+e.justify]:e.justify,["align-content-"+e.alignContent]:e.alignContent}),m.set(a,c)}return t(e.tag,Object(r["a"])(n,{staticClass:"row",class:c}),o)}})},1148:function(t,e,n){"use strict";var o=n("a691"),r=n("1d80");t.exports="".repeat||function(t){var e=String(r(this)),n="",a=o(t);if(a<0||a==1/0)throw RangeError("Wrong number of repetitions");for(;a>0;(a>>>=1)&&(e+=e))1&a&&(n+=e);return n}},"288c":function(t,e,n){"use strict";n("d0cd");var o=n("169a");e["a"]=o["a"].extend({name:"v-bottom-sheet",props:{inset:Boolean,maxWidth:{type:[String,Number],default:"auto"},transition:{type:String,default:"bottom-sheet-transition"}},computed:{classes(){return{...o["a"].options.computed.classes.call(this),"v-bottom-sheet":!0,"v-bottom-sheet--inset":this.inset}}}})},"3b00":function(t,e,n){"use strict";n("4d1a")},"3ce7":function(t,e,n){"use strict";var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-card",[n("v-card-title",[t._v(t._s(t.getProduct.name))]),n("v-card-actions",[n("span",{staticClass:"subheading ml-2"},[t._v(" $"+t._s(t.getProduct.price))]),n("v-spacer"),n("v-btn",{directives:[{name:"show",rawName:"v-show",value:1===t.getProduct.quantity,expression:"getProduct.quantity === 1"}],attrs:{icon:""},on:{click:[function(e){return t.removeProductFromCart(t.barcode)},function(e){return t.$emit("removed-product")}]}},[n("v-icon",[t._v("mdi-delete")])],1),n("v-btn",{directives:[{name:"show",rawName:"v-show",value:1!==t.getProduct.quantity,expression:"getProduct.quantity !== 1"}],attrs:{icon:""},on:{click:function(e){return t.setProductQuantity({barcode:t.barcode,typeOrAmount:"DECREMENT"})}}},[n("v-icon",[t._v("mdi-minus")])],1),n("span",{staticClass:"subheading mr-2 ml-2"},[t._v(" "+t._s(t.getProduct.quantity))]),n("v-btn",{attrs:{icon:""},on:{click:function(e){return t.setProductQuantity({barcode:t.barcode,typeOrAmount:"INCREMENT"})}}},[n("v-icon",[t._v("mdi-plus")])],1)],1)],1)},r=[],a=(n("7db0"),n("5530")),c=n("2f62"),i={props:["barcode"],computed:Object(a["a"])(Object(a["a"])({},Object(c["c"])(["getProductsInCart"])),{},{getProduct:function(){var t=this,e=this.getProductsInCart.find((function(e){return e.barcode==t.barcode}));return void 0===e?{barcode:"Failed to find product in Product.vue",name:"None",price:0,tax:0,description:"None",businessID:"None",quantity:1}:e}}),methods:Object(a["a"])({},Object(c["d"])(["removeProductFromCart","setProductQuantity"]))},s=i,u=(n("7a32"),n("2877")),l=n("6544"),d=n.n(l),f=n("8336"),p=n("b0af"),b=n("99d9"),v=n("132d"),h=n("2fa4"),g=Object(u["a"])(s,o,r,!1,null,"34d07a2c",null);e["a"]=g.exports;d()(g,{VBtn:f["a"],VCard:p["a"],VCardActions:b["a"],VCardTitle:b["d"],VIcon:v["a"],VSpacer:h["a"]})},"408a":function(t,e,n){var o=n("c6b6");t.exports=function(t){if("number"!=typeof t&&"Number"!=o(t))throw TypeError("Incorrect invocation");return+t}},"4d1a":function(t,e,n){},"7a32":function(t,e,n){"use strict";n("b92e")},a523:function(t,e,n){"use strict";n("20f6"),n("4b85");var o=n("2b0e");function r(t){return o["a"].extend({name:"v-"+t,functional:!0,props:{id:String,tag:{type:String,default:"div"}},render(e,{props:n,data:o,children:r}){o.staticClass=`${t} ${o.staticClass||""}`.trim();const{attrs:a}=o;if(a){o.attrs={};const t=Object.keys(a).filter(t=>{if("slot"===t)return!1;const e=a[t];return t.startsWith("data-")?(o.attrs[t]=e,!1):e||"string"===typeof e});t.length&&(o.staticClass+=" "+t.join(" "))}return n.id&&(o.domProps=o.domProps||{},o.domProps.id=n.id),e(n.tag,o,r)}})}var a=n("d9f7");e["a"]=r("container").extend({name:"v-container",functional:!0,props:{id:String,tag:{type:String,default:"div"},fluid:{type:Boolean,default:!1}},render(t,{props:e,data:n,children:o}){let r;const{attrs:c}=n;return c&&(n.attrs={},r=Object.keys(c).filter(t=>{if("slot"===t)return!1;const e=c[t];return t.startsWith("data-")?(n.attrs[t]=e,!1):e||"string"===typeof e})),e.id&&(n.domProps=n.domProps||{},n.domProps.id=e.id),t(e.tag,Object(a["a"])(n,{staticClass:"container",class:Array({"container--fluid":e.fluid}).concat(r||[])}),o)}})},b680:function(t,e,n){"use strict";var o=n("23e7"),r=n("a691"),a=n("408a"),c=n("1148"),i=n("d039"),s=1..toFixed,u=Math.floor,l=function(t,e,n){return 0===e?n:e%2===1?l(t,e-1,n*t):l(t*t,e/2,n)},d=function(t){var e=0,n=t;while(n>=4096)e+=12,n/=4096;while(n>=2)e+=1,n/=2;return e},f=s&&("0.000"!==8e-5.toFixed(3)||"1"!==.9.toFixed(0)||"1.25"!==1.255.toFixed(2)||"1000000000000000128"!==(0xde0b6b3a7640080).toFixed(0))||!i((function(){s.call({})}));o({target:"Number",proto:!0,forced:f},{toFixed:function(t){var e,n,o,i,s=a(this),f=r(t),p=[0,0,0,0,0,0],b="",v="0",h=function(t,e){var n=-1,o=e;while(++n<6)o+=t*p[n],p[n]=o%1e7,o=u(o/1e7)},g=function(t){var e=6,n=0;while(--e>=0)n+=p[e],p[e]=u(n/t),n=n%t*1e7},m=function(){var t=6,e="";while(--t>=0)if(""!==e||0===t||0!==p[t]){var n=String(p[t]);e=""===e?n:e+c.call("0",7-n.length)+n}return e};if(f<0||f>20)throw RangeError("Incorrect fraction digits");if(s!=s)return"NaN";if(s<=-1e21||s>=1e21)return String(s);if(s<0&&(b="-",s=-s),s>1e-21)if(e=d(s*l(2,69,1))-69,n=e<0?s*l(2,-e,1):s/l(2,e,1),n*=4503599627370496,e=52-e,e>0){h(0,n),o=f;while(o>=7)h(1e7,0),o-=7;h(l(10,o,1),0),o=e-1;while(o>=23)g(1<<23),o-=23;g(1<<o),h(1,1),g(2),v=m()}else h(0,n),h(1<<-e,0),v=m()+c.call("0",f);return f>0?(i=v.length,v=b+(i<=f?"0."+c.call("0",f-i)+v:v.slice(0,i-f)+"."+v.slice(i-f))):v=b+v,v}})},b92e:function(t,e,n){},d0cd:function(t,e,n){},fa0b:function(t,e,n){"use strict";n.r(e);var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-container",[n("div",{directives:[{name:"show",rawName:"v-show",value:1==t.show_popup,expression:"show_popup==true"}]},[n("v-row",{attrs:{justify:"center"}},[n("v-dialog",{attrs:{persistent:"","max-width":"290"},model:{value:t.dialog,callback:function(e){t.dialog=e},expression:"dialog"}},[n("v-card",[n("v-card-title",{staticClass:"headline"},[t._v(" Your cart is empty! ")]),n("v-card-text",[t._v("Please add items to your cart before attempting to checkout.")]),n("v-card-actions",[n("v-spacer"),n("v-btn",{attrs:{justify:"center",color:"green darken-1",text:""},on:{click:function(e){t.dialog=!1}}},[t._v(" OK ")])],1)],1)],1)],1)],1),n("CartList"),n("v-bottom-sheet",{attrs:{inset:""},scopedSlots:t._u([{key:"activator",fn:function(e){var o=e.on,r=e.attrs;return[n("v-btn",t._g(t._b({style:{left:"50%",transform:"translateX(-50%)"},attrs:{fab:"",fixed:"",bottom:"",id:"checkout-btn"}},"v-btn",r,!1),o),[n("v-icon",[t._v("mdi-currency-usd")])],1)]}}]),model:{value:t.show_checkout,callback:function(e){t.show_checkout=e},expression:"show_checkout"}},[n("v-sheet",{staticClass:"text-center"},[n("v-btn",{on:{click:function(e){t.show_checkout=!t.show_checkout}}},[t._v("close")]),n("Checkout",{on:{"show-popup":t.showPopUp}})],1)],1)],1)},r=[],a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("v-container",{staticClass:"CartList"},t._l(t.getProductsInCart,(function(t){return n("v-row",{key:t.barcode},[n("v-col",[n("Product",{attrs:{barcode:t.barcode}})],1)],1)})),1)],1)},c=[],i=n("2f62"),s=n("3ce7"),u={name:"CartList",components:{Product:s["a"]},computed:Object(i["c"])(["getProductsInCart"])},l=u,d=n("2877"),f=n("6544"),p=n.n(f),b=(n("4b85"),n("2b0e")),v=n("d9f7"),h=n("80d2");const g=["sm","md","lg","xl"],m=(()=>g.reduce((t,e)=>(t[e]={type:[Boolean,String,Number],default:!1},t),{}))(),w=(()=>g.reduce((t,e)=>(t["offset"+Object(h["w"])(e)]={type:[String,Number],default:null},t),{}))(),y=(()=>g.reduce((t,e)=>(t["order"+Object(h["w"])(e)]={type:[String,Number],default:null},t),{}))(),C={col:Object.keys(m),offset:Object.keys(w),order:Object.keys(y)};function _(t,e,n){let o=t;if(null!=n&&!1!==n){if(e){const n=e.replace(t,"");o+="-"+n}return"col"!==t||""!==n&&!0!==n?(o+="-"+n,o.toLowerCase()):o.toLowerCase()}}const k=new Map;var S=b["a"].extend({name:"v-col",functional:!0,props:{cols:{type:[Boolean,String,Number],default:!1},...m,offset:{type:[String,Number],default:null},...w,order:{type:[String,Number],default:null},...y,alignSelf:{type:String,default:null,validator:t=>["auto","start","end","center","baseline","stretch"].includes(t)},tag:{type:String,default:"div"}},render(t,{props:e,data:n,children:o,parent:r}){let a="";for(const i in e)a+=String(e[i]);let c=k.get(a);if(!c){let t;for(t in c=[],C)C[t].forEach(n=>{const o=e[n],r=_(t,n,o);r&&c.push(r)});const n=c.some(t=>t.startsWith("col-"));c.push({col:!n||!e.cols,["col-"+e.cols]:e.cols,["offset-"+e.offset]:e.offset,["order-"+e.order]:e.order,["align-self-"+e.alignSelf]:e.alignSelf}),k.set(a,c)}return t(e.tag,Object(v["a"])(n,{class:c}),o)}}),j=n("a523"),x=n("0fd9"),O=Object(d["a"])(l,a,c,!1,null,null,null),P=O.exports;p()(O,{VCol:S,VContainer:j["a"],VRow:x["a"]});var V=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-card",[n("v-card-title",{staticClass:"text-center"},[t._v(" Total: $"+t._s(t.showTwoDecimal(t.getSubtotal+t.getTax))+" ")]),n("v-card-subtitle",{staticClass:"text-center"},[t._v(" Subtotal: $"+t._s(t.showTwoDecimal(t.getSubtotal))+" Tax: $"+t._s(t.showTwoDecimal(t.getTax)))]),n("v-card-actions",{staticClass:"justify-center"},[n("v-btn",{on:{click:t.checkout}},[t._v("Checkout")])],1)],1)},N=[],$=(n("b680"),n("5530")),E={computed:Object(i["c"])(["getSubtotal","getTax"]),methods:Object($["a"])(Object($["a"])({},Object(i["b"])(["checkoutCart"])),{},{showTwoDecimal:function(t){return t.toFixed(2)},checkout:function(){var t=this;this.checkoutCart().then((function(e){t.$dbg_console_log("Attempted to checkout",e),e.checkoutSuccesful&&e.cartEmpty?t.$router.push("receipt"):!e.checkoutSuccesful&&e.cartEmpty?t.$emit("show-popup"):e.checkoutSuccesful||e.cartEmpty||t.$dbg_console_log("Backend could not checkout cart for some reason")})).catch((function(e){t.$dbg_console_log("Failed to checkout",e)}))}})},T=E,B=(n("3b00"),n("8336")),F=n("b0af"),I=n("99d9"),A=Object(d["a"])(T,V,N,!1,null,null,null),D=A.exports;p()(A,{VBtn:B["a"],VCard:F["a"],VCardActions:I["a"],VCardSubtitle:I["b"],VCardTitle:I["d"]});var L={name:"Cart",components:{CartList:P,Checkout:D},mounted:function(){this.$emit("toggleHeader","customer")},data:function(){return{show_checkout:!1,show_popup:!1,dialog:!1}},methods:{showPopUp:function(){this.$emit("showPopUp"),this.dialog=!0}}},q=L,R=n("288c"),M=n("169a"),W=n("132d"),Q=n("8dd9"),U=n("2fa4"),G=Object(d["a"])(q,o,r,!1,null,null,null);e["default"]=G.exports;p()(G,{VBottomSheet:R["a"],VBtn:B["a"],VCard:F["a"],VCardActions:I["a"],VCardText:I["c"],VCardTitle:I["d"],VContainer:j["a"],VDialog:M["a"],VIcon:W["a"],VRow:x["a"],VSheet:Q["a"],VSpacer:U["a"]})}}]);
//# sourceMappingURL=about.b5727623.js.map