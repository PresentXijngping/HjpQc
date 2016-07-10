$(function () {
	
	$('#nav').tree({
		url : 'queryNavMenu.do',
		lines : true,
		onLoadSuccess : function (node, data) {
			if (data) {
				$(data).each(function (index, value) {
					if (this.state == 'closed') {
						$('#nav').tree('expandAll');
					}
				});
			}
		},
		onClick : function (node) {
			if (node.url) {
				if ($('#tabs').tabs('exists', node.text)) {
					$('#tabs').tabs('select', node.text)
				} else {
					$('#tabs').tabs('add', {
						title : node.text,
						iconCls : node.iconCls,
						closable : true,
						//href : node.url,
						content: createFrame(node.url),
					});
				}
			}
		}
	});
	
	function createFrame(url) {
    	var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
        return s;
    };
	
	$('#tabs').tabs({
		fit : true,
		border : false,
	});
	
});