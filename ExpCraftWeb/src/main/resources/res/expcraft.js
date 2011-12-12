
var playerFaces = false;

var playerDetail = function(event) {
	var player = $(event.currentTarget).text();
	$.getJSON('json.php/'+player, playerDetailCallback);
};

var playerDetailCallback = function(data) {
	if (data.status != 'ok') {
		return;
	}
	var cont = $('#playerdetail .content');
	cont.html('');
	var tr, i, z = 0, player;
	for (i in data.exp) {
		player = data.exp[i].player;
		cont.append('<div><img src="res/'+data.modules[i].short+'.png" alt="'+data.modules[i].name+'" title="'+data.modules[i].name+'" style="float: left;"/><p>lv'+data.exp[i].level+'<br />'+data.exp[i].exp+'</p></div>');
	}
	$('#playerdetail h1').html(player);
	$.mobile.changePage($('#playerdetail'));
};

var refresh = function() {
	$('#mainpage .content').html('<img src="res/loading.gif" alt="loading" title="loading" />');
	$.getJSON('json.php/all', refreshCallback);
};

var refreshCallback = function(data) {
	if (data.status != 'ok') {
		$('#mainpage .content').html('<p>ERROR!</p>');
		alert('Error fetching data:\n' + data.status);
		return;
	}
	$('#mainpage .content').html('<table><tr class="head"><th class="player">Player</th></tr></table>');
	var i,table = $('#mainpage tr');
	for (i in data.modules) {
		table.append('<th><img src="res/'+data.modules[i].short+'.png" alt="'+data.modules[i].name+'" title="'+data.modules[i].name+'" /></td>');
	}
	
	var name = '';
	var tr = null;
	for (i in data.exp) {
		if (data.exp[i].player != name) {
			name = data.exp[i].player;
			$('#mainpage .content table').append("<tr></tr>");
			tr = $('#mainpage .content tr:last');
			var img = '';
			if (playerFaces) {
				img = '<span class="face" style="background-image: url(http://s3.amazonaws.com/MinecraftSkins/'+name+'.png)"></span>';
			}
			var s = '<a data-shadow="false" data-corners="false" data-role="button" href="#">'+img+name+'</a>';
			tr.append('<td class="player">'+s+'</td>');
		}
		tr.append('<td>'+data.exp[i].level+'</td>');
	}
	$('#mainpage').trigger('create');
	$('#mainpage table a').css('margin','0');
	$('#mainpage td a').click(playerDetail);
	
	// FIX for update bug in jquerymobile
	$('#mainpage a.refresh').removeClass('ui-btn-active');
}

$(document).ready(function(){
	$('#mainpage a.refresh').click(refresh);
	$.getJSON('json.php/config', loadConfigCallback);
});

var loadConfigCallback = function(data) {
	if (data.status != 'ok') {
		alert('Error fetching data:\n' + data.status);
	} else {
		playerFaces = data.playerFaces;
	}
	refresh();
};
