"use strict";(self["webpackChunkclient"]=self["webpackChunkclient"]||[]).push([[310],{8314:function(e,t,n){n.d(t,{Z:function(){return d}});var c=n(3396),r=n(7139);const a={class:"currency-item"},u={class:"name"},s={class:"value"};function l(e,t,n,l,o,i){return(0,c.wg)(),(0,c.iD)("div",a,[(0,c._)("span",{class:"icon",style:(0,r.j5)({color:l.currencyColor,"background-color":l.bgColor})},(0,r.zw)(l.currencySymbol),5),(0,c._)("span",u,(0,r.zw)(l.currency),1),(0,c._)("span",s,(0,r.zw)(l.valueFormatted)+" "+(0,r.zw)(l.currencySymbol),1)])}var o={name:"VCurrencyListItem",props:{currencyCode:{type:Number,required:!0},value:{type:Number,required:!0}},setup(e){const t={643:"руб",840:"Доллар",978:"Евро"},n={643:"#E88717",840:"#50B600",978:"#32A2E1"},c={643:"#FFCA2B",840:"#73F010",978:"#3BE9E9"},r={643:"₽",840:"$",978:"€"},a=e.value.toLocaleString(),u=t[e.currencyCode],s=n[e.currencyCode],l=c[e.currencyCode],o=r[e.currencyCode];return{valueFormatted:a,currency:u,currencyColor:s,bgColor:l,currencySymbol:o}}},i=n(89);const y=(0,i.Z)(o,[["render",l]]);var d=y},3310:function(e,t,n){n.r(t),n.d(t,{default:function(){return V}});var c=n(3396),r=n(7139);const a={class:"home"},u={class:"title flex-col"},s=(0,c._)("span",null,"продуктивной работы 🤖❤️💸",-1),l={class:"balance"},o=(0,c._)("span",{class:"balance-title"},"Суммарный баланс",-1),i={class:"balance-value"},y=(0,c._)("span",{class:"plus"},"+",-1),d=(0,c._)("ul",null,[(0,c._)("li")],-1),m=(0,c._)("ul",null,[(0,c._)("li")],-1),p={key:1};function b(e,t,n,b,w,v){const f=(0,c.up)("router-link"),_=(0,c.up)("VCurrencyListItem"),k=(0,c.up)("VList");return(0,c.wg)(),(0,c.iD)("div",a,[(0,c._)("h1",u,[(0,c._)("span",null,"Привет "+(0,r.zw)(b.user.name)+"!",1),s]),(0,c._)("div",l,[o,(0,c._)("span",i,(0,r.zw)(b.balanceFormatted),1),b.user.balance?(0,c.kq)("",!0):((0,c.wg)(),(0,c.j4)(f,{key:0,to:{name:"PayIn-Out"},class:"base-button base-button--active"},{default:(0,c.w5)((()=>[(0,c.Uk)(" пополнить баланс ")])),_:1})),b.user.balance?(0,c.kq)("",!0):((0,c.wg)(),(0,c.iD)(c.HY,{key:1},[(0,c.Wm)(_,{"currency-code":643,value:0,onClick:t[0]||(t[0]=e=>b.toAccount(643))}),(0,c.Wm)(f,{to:{name:"CreateAccount"},class:"base-button base-button--accent",style:{margin:"15px auto 0"}},{default:(0,c.w5)((()=>[y,(0,c.Uk)(" Открыть новый счет ")])),_:1})],64))]),b.user.accounts&&b.user.accounts.length?((0,c.wg)(),(0,c.j4)(k,{key:0,title:"Ваши счета"},{default:(0,c.w5)((()=>[d])),_:1})):(0,c.kq)("",!0),(0,c.Wm)(k,{title:"История операций"},{default:(0,c.w5)((()=>[b.user.history&&b.user.history.length?((0,c.wg)(),(0,c.iD)(c.HY,{key:0},[m,(0,c.Wm)(f,{to:{name:"History"}},{default:(0,c.w5)((()=>[(0,c.Uk)(" открыть историю операций ")])),_:1})],64)):((0,c.wg)(),(0,c.iD)("p",p," нет ни одной операции :( "))])),_:1})])}n(7658);var w=n(4870);const v={class:"list flex-col"},f={class:"list-title"};function _(e,t,n,a,u,s){return(0,c.wg)(),(0,c.iD)("div",v,[(0,c._)("span",f,(0,r.zw)(n.title),1),(0,c.WI)(e.$slots,"default")])}var k={name:"VList",props:{title:{type:String,required:!1,default:""}}},g=n(89);const C=(0,g.Z)(k,[["render",_]]);var h=C,q=n(8314),F=n(2483),L={name:"VHome",components:{VList:h,VCurrencyListItem:q.Z},setup(){const e=(0,F.tv)(),t=(0,w.qj)({name:"Никита",history:null,balance:0,accounts:[]});function n(t){e.push({name:"Account",query:{currency:t}})}const c=(0,w.Fl)((()=>t.balance.toLocaleString()));return{user:t,balanceFormatted:c,toAccount:n}}};const z=(0,g.Z)(L,[["render",b]]);var V=z}}]);
//# sourceMappingURL=310.e759c84e.js.map