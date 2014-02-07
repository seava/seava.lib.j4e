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
			src="${ctxpath}/ui-extjs/frame/${bundle}/${shortLanguage}/${itemSimpleName}.js"></script>
		<script type="text/javascript"
			src="${ctxpath}/ui-extjs/frame/${bundle}/${itemSimpleName}.js"></script>
	</#if>

	${extensions}

	<script type="text/javascript">
		if (document && document.getElementById("n21-loading-msg")) {
			document.getElementById("n21-loading-msg").innerHTML = Main
					.translate("msg", "initialize")
					+ " ${item}...";
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

			var map = new Ext.util.KeyMap({
				target : document.body,
				eventName : "keydown",
				processEvent : function(event, source, options) {
					return event;
				},
				binding : [ Ext.apply(Main.keyBindings.dc.doClearQuery, {
					fn : function(keyCode, e) {
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.doClearQuery();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.doEnterQuery, {
					fn : function(keyCode, e) {
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.doEnterQuery();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.doQuery, {
					fn : function(keyCode, e) {
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.doQuery();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.doNew, {
					fn : function(keyCode, e) {
						//console.log("indexFrame.doNew");
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.doNew();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.doCancel, {
					fn : function(keyCode, e) {
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.doCancel();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.doSave, {
					fn : function(keyCode, e) {
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.doSave();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.doCopy, {
					fn : function(keyCode, e) {
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.doCopy();
						ctrl.doEditIn();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.doEditIn, {
					fn : function(keyCode, e) {
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.doEditIn();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.doEditOut, {
					fn : function(keyCode, e) {
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.doEditOut();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.nextRec, {
					fn : function(keyCode, e) {
						//console.log("indexFrame.nextRec");
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.setNextAsCurrent();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.prevRec, {
					fn : function(keyCode, e) {
						//console.log("indexFrame.prevRec");
						e.stopEvent();
						var ctrl = theFrameInstance._getRootDc_();
						ctrl.setPreviousAsCurrent();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.nextPage, {
					fn : function(keyCode, e) {
						//console.log("indexFrame.nextPage");
						e.stopEvent();
						theFrameInstance._getRootDc_().nextPage();
					},
					scope : this
				}), Ext.apply(Main.keyBindings.dc.prevPage, {
					fn : function(keyCode, e) {
						//console.log("indexFrame.prevPage");
						e.stopEvent();
						theFrameInstance._getRootDc_().previousPage();
					},
					scope : this
				}) ]
			});

		});

	<#include "_loading_mask_remove.ftl">
		
	</script>
</body>
</html>