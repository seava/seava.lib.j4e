	if (!Ext.isEmpty(Ext.get('n21-loading'))) {
		setTimeout(function(){
			if (!Ext.isEmpty(Ext.get('n21-loading-msg'))) {
				Ext.get('n21-loading-msg').remove();
			}
			if (!Ext.isEmpty(Ext.get('n21-loading-indicator'))) {
				Ext.get('n21-loading-indicator').remove();
			}
			if (!Ext.isEmpty(Ext.get('n21-loading'))) {
				Ext.get('n21-loading').remove();
			}
			if (!Ext.isEmpty(Ext.get('n21-loading-mask'))) {
				Ext.get('n21-loading-mask').fadeOut({remove:true});
			}
		}, 250);
	}