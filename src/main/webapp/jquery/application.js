$(function () {
    "use strict";

    var content = $('#content');
    var socket = $.atmosphere;
    var request = { url: document.location.toString() + 'chat',
                    contentType : "application/json",
                    logLevel : 'debug',
                    transport : 'sse' ,
                    fallbackTransport: 'long-polling'};
     
    request.onOpen = function(response) {
    	  content.append($('<p>', { text: 'Atmosphere connected using bla bla ' + response.transport }));
    };

    request.onMessage = function (response) {
        var message = response.responseBody;
        try {
            var json = jQuery.parseJSON(message);
        } catch (e) {
        	content.append($('<p>', { text: 'This doesn\'t look like a valid JSON: ' + message.data }));
            return;
        }
        addMessage(json.a, json.b, json.c);
    };

    request.onClose = function(response) {
    };

    request.onError = function(response) {
        content.append($('<p>', { text: 'Sorry, but there\'s some problem with your socket or the server is down'}));
    };
   
    var subSocket = socket.subscribe(request);

    input.keydown(function(e) {
        if (e.keyCode === 13) {
            subSocket.push(jQuery.stringifyJSON({ author: author, message: msg }));    
        }
    });
    
    function addMessage(a, b, c) {
        content.append($('<p>', { text: a + ' : ' + b + ' : ' + c}));
    }
});