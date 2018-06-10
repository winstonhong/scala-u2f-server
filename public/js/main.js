$(document).ready(function() {
    var REGISTER_BTN = $('[data-input="register"]');
    var AUTH_BTN = $('[data-input="authenticate"]');
    var TOUCH = $('[data-display="touch"]');
    var TOUCH_AUTH = $('[data-display="touch-auth"]');
    var TOKEN_RESPONSE = $('#tokenResponse');
    var AUTH_RESPONSE = $('#authResponse');
    var FORM = $('#form');
    var FORM_AUTH = $('#form-auth');
    console.log(TOKEN_RESPONSE);

    var onTouch = function(data) {
        if(data.errorCode) {
            console.error("U2F failed with error: " + data.errorMessage);
            return;
        }
        TOKEN_RESPONSE.val(JSON.stringify(data));
        FORM.submit();
    };


    var onAuth = function(data) {
        if(data.errorCode) {
            console.error("U2F failed with error: " + data.errorMessage);
            return;
        }
        AUTH_RESPONSE.val(JSON.stringify(data));
        FORM_AUTH.submit();
    };


    TOUCH.hide();
    TOUCH_AUTH.hide();

    REGISTER_BTN.on('click', function() {
        console.log('register');

        $.post('/register', function(data) {
            u2f.register(data.appId, data.registerRequests, data.registeredKeys, onTouch);
            TOUCH.show();
	    TOUCH_AUTH.hide();
        });
    });

    AUTH_BTN.on('click', function() {
        console.log('authenticate');

        $.get('/startAuthentication', function(data) {
            u2f.sign(data.appId, data.challenge, data.signRequests, onAuth);
            TOUCH_AUTH.show();
            TOUCH.hide();
        });
    });

});
