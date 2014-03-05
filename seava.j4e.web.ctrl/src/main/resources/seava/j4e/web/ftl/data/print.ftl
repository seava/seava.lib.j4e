<html>
<head>
  <title><#if cfg.title??> ${cfg.title} </#if> | By: ${cfg.runBy} / ${printer.print(cfg.runAt)} </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <style>
a,abbr,acronym,address,applet,article,aside,audio,
b,blockquote,big,body,
center,canvas,caption,cite,code,command,datalist,
dd,del,details,dfn,dl,div,dt,
em,embed,fieldset,figcaption,figure,font,footer,form,
h1,h2,h3,h4,h5,h6,header,hgroup,html,i,iframe,img,ins,
kbd,keygen,label,legend,li,meter,nav,object,ol,output,p,pre,progress,q,s,
samp,section,small,span,source,strike,strong,sub,sup,
table,tbody,tfoot,thead,th,tr,tdvideo,tt,u,ul,var{
	background:transparent;
	border:0 none;
	font-size:100%;
	margin:0;
	padding:0;
	border:0;
	outline:0;
	vertical-align:top
}
ul{
	list-style:none
}
blockquote,q{
	quotes:none
}
table,table td{
	padding:0;
	border:none;
	border-collapse:collapse
}
img{
	vertical-align:top
}
* {
	font-family: Arial, tahoma, verdana;
	font-size:13px;
	color: #333;
	line-height:1.6em;
}
body{
	font-family: Arial, Helvetica, sans-serif;
	line-height:1.4em;
	font-size:0.9em;
	color:#555;
}
h1,h2,h3,h4,h5 {
	color:#333;
}
h1 {
   font-size:24px;
}
table.result {
  border: 1px solid gray ;
  border-collapse:collapse;
}

table.result thead tr td {
  border-bottom: 1px solid gray;
  padding:3px 2px 3px 4px;
  font-weight:bold;
}
table.result tbody tr td {
  border-bottom: 1px solid #dedede;
  padding:2px 2px 2px 6px;
}
.number {
 text-align:right;
}
  </style>
</head>
<body>

<table width="<#if cfg.orientation == "landscape">980<#else>660</#if>" style="margin:20px;">
<tr>
	<td style="width:100%">
		<table style="width:100%">
			   <tr>
			       <td><#if cfg.logo?has_content> <img src="${cfg.logo}"> </#if></td>
			       <td><h1><#if cfg.title??> ${cfg.title} </#if></h1></td>
			       <td align=right>
				   	   User: ${cfg.runBy }  <br>
					   Date: ${printer.print(cfg.runAt,"DATETIME_FORMAT")}
				   </td>
			   </tr>
		</table>
	</td>
</tr>
<tr>
	<td >
		<hr width="100%" size=1>
	</td>
</tr>

<#assign filterSize= cfg.filter?size>

<tr>
	<td style="font-weight:bold;">
		Filter condition
	</td>
</tr>
<tr>
	<td >
		<hr width="100%" size=1>
	</td>
</tr>
<tr>
	<td style="width:50%">
		<table>
             <#list cfg.filter as f>
                 <#if (!f.name?ends_with("Id")) >
                 	  <#if filter[f.name]?has_content >
	                 	  <tr><td style="padding-right:10px;">${f.title}:</td><td>${printer.print(filter[f.name])}</td></tr>
	                 </#if>
                 </#if>
		  	 </#list>
		</table>
	</td>
</tr>
<tr>
	<td >
		<hr width="100%" size=1>
	</td>
</tr>

<tr>
	<td>
		<table style="width:100%" class="result">
		<thead>
		  <tr>
		  <#list cfg.columns as c>
		     <td class='${c.type}'>${c.title} </td>
		  </#list>
		  </tr>
         </thead>
		  <#list data as m>
		  	<tr>
		    <#list cfg.columns as c>
				<td class='col-data ${c.type}'>
					  <#if m[c.name]?has_content >
						 <#if  c.mask ?has_content >
						   ${printer.print(m[c.name], c.mask )}
						 <#else>
                           ${printer.print(m[c.name])}
						 </#if>
                     </#if>
			    </td>
			</#list>
			</tr>
		</#list>
		</table>
	</td>
</tr>
<tr>
	<td >
		<hr width="100%" size=1>
	</td>
</tr>
<tr>
	<td style="width:100%">
		<table style="width:100%">
			   <tr>
			       <td style="font-size:12px;"> <#if  client.name?has_content > ${client.name} </#if> </td>
			       <td></td>
			       <td align=right style="font-size:10px;">                         
				   </td>
			   </tr>
		</table>
	</td>
</tr>

</table>
</body>
</html>