var exec = require('cordova/exec');

window.zendesk = {
    init: function(arg0, success, error) {
        exec(success, error, "FabricZendesk", "init", [arg0]);
    },
    tickets: function(arg0, success, error) {
        exec(success, error, "FabricZendesk", "tickets", [arg0]);
    },
    help: function(arg0, success, error) {
        exec(success, error, "FabricZendesk", "help", [arg0]);
    },
    chat: function(user, device, success, error) {
        user = user || {};
        device = device || {};
        exec(success, error, "FabricZendesk", "chat", [user.name || '', user.email || '', user.msisdn || '', JSON.stringify(device)]);
    }
}