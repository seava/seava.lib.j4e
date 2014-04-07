<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${item} | ${productName} </title>
	<script type="text/javascript">
		__ITEM__ = "${item}";
		__LANGUAGE__ = "${shortLanguage}";
		__THEME__ = "${theme}";
	</script>
	<script type="text/javascript">${constantsJsFragment}</script>

	<!-- Theme -->	
	<link rel="stylesheet" type="text/css"
		href="${urlUiExtjsThemes}/${theme}/${theme}-all.css" />
		
</head>
<body>

	<#include "_loading_mask.ftl">

	<script type="text/javascript">
		if (document && document.getElementById("n21-loading-msg")) {
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

    <#if sysCfg_workingMode == "dev">
		<#list frameDependenciesTrl as _include>
			   <script type="text/javascript" src="${_include}"></script>
		</#list>
		<#list frameDependenciesCmp as _include>
			   <script type="text/javascript" src="${_include}"></script>
		</#list>
	</#if>
    <#if sysCfg_workingMode == "prod">
		<script type="text/javascript"
			src="${ctxpath}/ui-extjs/frame/${bundle}/${shortLanguage}/${item}.js"></script>
		<script type="text/javascript"
			src="${ctxpath}/ui-extjs/frame/${bundle}/${item}.js"></script>
	</#if>

	${extensions}

	<script type="text/javascript">
		if (document && document.getElementById("n21-loading-msg")) {
			document.getElementById("n21-loading-msg").innerHTML = Main
					.translate("msg", "initialize")
					+ " ${itemSimpleName}...";
		}
	</script>

	<script>
		var theFrameInstance = null;
		var __theViewport__ = null;
		Ext.onReady(function() {
			if (getApplication().getSession().useFocusManager) {
				Ext.FocusManager.enable(true);
			}
		<#include "_on_ready.ftl">
		var frameReports = [];

			${extensionsContent}

			var cfg = {
				layout : "fit",
				xtype : "container",
				items : [ {
					xtype : "${itemSimpleName}",
					border : false,
					_reports_ : frameReports,
					listeners : {
						afterrender : {
							fn : function(p) {
								theFrameInstance = this;
							}
						}
					}
				} ]
			};
			__theViewport__ = new Ext.Viewport(cfg);

			var map = KeyBindings.createFrameKeyMap(theFrameInstance);

		});

	<#include "_loading_mask_remove.ftl">
	

	</script>
</body>
</html>