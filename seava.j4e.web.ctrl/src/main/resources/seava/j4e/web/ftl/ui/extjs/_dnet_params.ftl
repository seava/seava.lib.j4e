	<script>
	
		/* product info */
	
		Main.productInfo.name = "${productName}";
		Main.productInfo.description = "${productDescription}";
		Main.productInfo.version = "${productVersion}";
		Main.productInfo.vendor = "${productVendor}";
		Main.productInfo.url = "${productUrl}";
		Main.logo = "${logo}";

		/* application urls */

		Main.urlHost = "${hostUrl}";
		Main.urlWeb = Main.urlHost + "${ctxpath}";
		Main.urlDs = Main.urlWeb + "${statics["seava.j4e.api.Constants"].URL_DATA_DS}";
		Main.urlAsgn = Main.urlWeb + "${statics["seava.j4e.api.Constants"].URL_DATA_ASGN}";
		Main.urlWorkflow = Main.urlWeb + "${statics["seava.j4e.api.Constants"].URL_WORKFLOW}";
		Main.urlUiExtjs = Main.urlWeb + "${statics["seava.j4e.api.Constants"].URL_UI_EXTJS}";
		Main.urlSession = Main.urlWeb + "${statics["seava.j4e.api.Constants"].URL_SESSION}";
		Main.urlUpload = Main.urlWeb + "${statics["seava.j4e.api.Constants"].URL_UPLOAD}";
	
		/* ui-extjs paths */
	
		Main.urlStaticCore = "${urlUiExtjsCore}";
		Main.urlStaticCoreI18n = "${urlUiExtjsCoreI18n}";
		Main.urlStaticModules = "${urlUiExtjsModules}";
		Main.urlStaticModuleSubpath = "${urlUiExtjsModuleSubpath}";
		Main.urlStaticModuleUseBundle = ${urlUiExtjsModuleUseBundle?string};

		/* date format masks */
	
	    <#list dateFormatMasks?keys as key>
		Main.${key} = "${dateFormatMasks[key]}";
		</#list>
		Main.MODEL_DATE_FORMAT = "${modelDateFormat}";
	
		/* number format */
	
		Main.DECIMAL_SEP = "${decimalSeparator}";
		Main.THOUSAND_SEP = "${thousandSeparator}";
	
	</script>
