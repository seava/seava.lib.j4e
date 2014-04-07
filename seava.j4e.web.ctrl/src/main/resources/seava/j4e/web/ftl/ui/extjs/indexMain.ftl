<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${productName} | ${productVersion}</title>
	<script type="text/javascript">
		${constantsJsFragment}
	</script>

	
	<!-- Theme /-->	
	<link rel="stylesheet" type="text/css"
		href="${urlUiExtjsThemes}/${theme}/${theme}-all.css" />

</head>
<body>

	<#include "_loading_mask.ftl">

	<script type="text/javascript">
    	if(document &&  document.getElementById("n21-loading-msg")) {
        	document.getElementById("n21-loading-msg").innerHTML = "...";
        }
	</script>

	<#if sysCfg_workingMode == "dev">
		<#include "_includes_dev.ftl">
	</#if>
    <#if sysCfg_workingMode == "prod">
		<#include "_includes_prod.ftl">
	</#if>

    <#include "_dnet_params.ftl">

	${extensions}

	<script type="text/javascript">
  		if(document && document.getElementById("n21-loading-msg")) {
  	  		document.getElementById("n21-loading-msg").innerHTML = Main.translate("msg", "initialize");
  	  	}
	</script>

	<script>

    Ext.onReady(function(){

		<#include "_on_ready.ftl">

		e4e.base.Session.user.code = "${user.code}";
		e4e.base.Session.user.loginName = "${user.loginName}";
		e4e.base.Session.user.name = "${user.name}";
        e4e.base.Session.user.systemUser = ${user.systemUser?string};

        <#if ((user.client??) && (user.client.id??)) >
			e4e.base.Session.client.id = "${user.client.id}";
			e4e.base.Session.client.code = "${user.client.code}";
			e4e.base.Session.client.name = "${user.client.name}";
		</#if>
		e4e.base.Session.locked = false;
		e4e.base.Session.roles = [${userRolesStr}];

		${extensionsContent}
        
      	var tr = e4e.base.TemplateRepository;

		__application__ = e4e.base.Application;
		__application__.menu = new e4e.base.ApplicationMenu({ region:"north" });
		__application__.view = new Ext.Viewport({
			 layout:"card", 
			 activeItem:0 ,
			 forceLayout:true,
			 items:[
				  { html:"" } ,
			   	  {  padding:0,
			    	layout:"border",
				    forceLayout:true,
				    items:[{	
						region:"center",
					   	enableTabScroll : true,
					   	xtype:"tabpanel",
						deferredRender:false,
						activeTab:0,
						plain : true,
						cls: "dnet-home",
						plugins: Ext.create("Ext.ux.TabCloseMenu", {}),
				   		id:"dnet-application-view-body",
				    	items:[{
				    		xtype:"dnetHomePanel",
							id:"dnet-application-view-home"}
				    	]},
				    	__application__.menu
				    ]}
			]
      	});

		__application__.run();
		var map = KeyBindings.createMainKeyMap();

    });

    <#include "_loading_mask_remove.ftl">

    var confirm_leave = true;
    var leave_message = "Are you sure you want to leave?"
    function _leave_page(e) {
            if( confirm_leave ===true)
            {
                if(!e) e = window.event;
                e.cancelBubble = true;
                e.returnValue = leave_message;
                if (e.stopPropagation) 
                {
                    e.stopPropagation();
                    e.preventDefault();
                }
                return leave_message;
            }
        }   
    window.onbeforeunload=_leave_page;

  </script>
</body>
</html>