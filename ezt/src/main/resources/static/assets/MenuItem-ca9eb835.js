import{g as m,a as F,s as N,C as P,Z as w,_ as r,f as c,r as d,u as G,b as S,aq as x,t as E,q as U,j as I,c as $,d as _}from"./index-d6f2b5bc.js";import{d as M}from"./Menu-1c03912c.js";const H=m("MuiListItemIcon",["root","alignItemsFlexStart"]),O=H,q=m("MuiListItemText",["root","multiline","dense","inset","primary","secondary"]),k=q;function z(e){return F("MuiMenuItem",e)}const D=m("MuiMenuItem",["root","focusVisible","dense","disabled","divider","gutters","selected"]),i=D,W=["autoFocus","component","dense","divider","disableGutters","focusVisibleClassName","role","tabIndex","className"],Z=(e,s)=>{const{ownerState:t}=e;return[s.root,t.dense&&s.dense,t.divider&&s.divider,!t.disableGutters&&s.gutters]},A=e=>{const{disabled:s,dense:t,divider:a,disableGutters:l,selected:p,classes:o}=e,n=_({root:["root",t&&"dense",s&&"disabled",!l&&"gutters",a&&"divider",p&&"selected"]},z,o);return r({},o,n)},J=N(P,{shouldForwardProp:e=>w(e)||e==="classes",name:"MuiMenuItem",slot:"Root",overridesResolver:Z})(({theme:e,ownerState:s})=>r({},e.typography.body1,{display:"flex",justifyContent:"flex-start",alignItems:"center",position:"relative",textDecoration:"none",minHeight:48,paddingTop:6,paddingBottom:6,boxSizing:"border-box",whiteSpace:"nowrap"},!s.disableGutters&&{paddingLeft:16,paddingRight:16},s.divider&&{borderBottom:`1px solid ${(e.vars||e).palette.divider}`,backgroundClip:"padding-box"},{"&:hover":{textDecoration:"none",backgroundColor:(e.vars||e).palette.action.hover,"@media (hover: none)":{backgroundColor:"transparent"}},[`&.${i.selected}`]:{backgroundColor:e.vars?`rgba(${e.vars.palette.primary.mainChannel} / ${e.vars.palette.action.selectedOpacity})`:c(e.palette.primary.main,e.palette.action.selectedOpacity),[`&.${i.focusVisible}`]:{backgroundColor:e.vars?`rgba(${e.vars.palette.primary.mainChannel} / calc(${e.vars.palette.action.selectedOpacity} + ${e.vars.palette.action.focusOpacity}))`:c(e.palette.primary.main,e.palette.action.selectedOpacity+e.palette.action.focusOpacity)}},[`&.${i.selected}:hover`]:{backgroundColor:e.vars?`rgba(${e.vars.palette.primary.mainChannel} / calc(${e.vars.palette.action.selectedOpacity} + ${e.vars.palette.action.hoverOpacity}))`:c(e.palette.primary.main,e.palette.action.selectedOpacity+e.palette.action.hoverOpacity),"@media (hover: none)":{backgroundColor:e.vars?`rgba(${e.vars.palette.primary.mainChannel} / ${e.vars.palette.action.selectedOpacity})`:c(e.palette.primary.main,e.palette.action.selectedOpacity)}},[`&.${i.focusVisible}`]:{backgroundColor:(e.vars||e).palette.action.focus},[`&.${i.disabled}`]:{opacity:(e.vars||e).palette.action.disabledOpacity},[`& + .${M.root}`]:{marginTop:e.spacing(1),marginBottom:e.spacing(1)},[`& + .${M.inset}`]:{marginLeft:52},[`& .${k.root}`]:{marginTop:0,marginBottom:0},[`& .${k.inset}`]:{paddingLeft:36},[`& .${O.root}`]:{minWidth:36}},!s.dense&&{[e.breakpoints.up("sm")]:{minHeight:"auto"}},s.dense&&r({minHeight:32,paddingTop:4,paddingBottom:4},e.typography.body2,{[`& .${O.root} svg`]:{fontSize:"1.25rem"}}))),K=d.forwardRef(function(s,t){const a=G({props:s,name:"MuiMenuItem"}),{autoFocus:l=!1,component:p="li",dense:o=!1,divider:g=!1,disableGutters:n=!1,focusVisibleClassName:R,role:T="menuitem",tabIndex:f,className:B}=a,L=S(a,W),v=d.useContext(x),y=d.useMemo(()=>({dense:o||v.dense||!1,disableGutters:n}),[v.dense,o,n]),u=d.useRef(null);E(()=>{l&&u.current&&u.current.focus()},[l]);const V=r({},a,{dense:y.dense,divider:g,disableGutters:n}),b=A(a),j=U(u,t);let C;return a.disabled||(C=f!==void 0?f:-1),I.jsx(x.Provider,{value:y,children:I.jsx(J,r({ref:j,role:T,tabIndex:C,component:p,focusVisibleClassName:$(b.focusVisible,R),className:$(b.root,B)},L,{ownerState:V,classes:b}))})}),Y=K;export{Y as M};
