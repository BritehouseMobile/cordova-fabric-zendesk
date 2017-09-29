package io.atajo.cordova.zendesk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import io.fabric.sdk.android.Fabric;

import com.zendesk.logger.Logger;
import com.zendesk.sdk.model.access.AnonymousIdentity;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zendesk.sdk.requests.RequestActivity;
import com.zendesk.sdk.support.SupportActivity;

import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.ZopimChatActivity;
import com.zopim.android.sdk.prechat.PreChatForm;

public class FabricZendesk extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("init")) {
            String username = args.getString(0);

            this.init(username, callbackContext);
            return true;
        }
        if (action.equals("tickets")) {

            this.openTickets(callbackContext);
            return true;
        }

        if (action.equals("help")) {

            this.openHelpCenter(callbackContext);
            return true;
        }

        if (action.equals("chat")) {

            String name = args.getString(0);
            String email = args.getString(1);
            String msisdn = args.getString(2);
            JSONObject device = new JSONObject(args.getString(3));

            this.openChat(name, email, msisdn, callbackContext);
            return true;
        }

        return false;
    }

    private void openTickets(CallbackContext callbackContext) {

        try {
            RequestActivity.startActivity(this.cordova.getActivity(), null);
            callbackContext.success();
        } catch (Exception e) {
            callbackContext.error("Could not open ZenDesk " + e);
        }

    }

    private void openHelpCenter(CallbackContext callbackContext) {

        try {
            new SupportActivity.Builder().show(this.cordova.getActivity());
            callbackContext.success();
        } catch (Exception e) {
            callbackContext.error("Could not open ZenDesk " + e);
        }

    }

    private void openChat(String name, String email, String msisdn, CallbackContext callbackContext) {

        try {

            String chatKey = cordova.getActivity().getString(cordova.getActivity().getResources()
                    .getIdentifier("chat_api_key", "string", cordova.getActivity().getPackageName()));

            if (name.equals("")) {

                PreChatForm defaultPreChat = new PreChatForm.Builder().name(PreChatForm.Field.OPTIONAL)
                        .phoneNumber(PreChatForm.Field.OPTIONAL).message(PreChatForm.Field.OPTIONAL).build();

                ZopimChat.init(chatKey).preChatForm(defaultPreChat);

            } else {

                ZopimChat.init(chatKey);
                VisitorInfo visitorData = new VisitorInfo.Builder().name(name).email(email).phoneNumber(msisdn).build();
                ZopimChat.setVisitorInfo(visitorData);

            }

            Context context = this.cordova.getActivity().getApplicationContext();
            this.cordova.getActivity().startActivity(new Intent(context, ZopimChatActivity.class));

            callbackContext.success();
        } catch (Exception e) {
            callbackContext.error("Could not open ZenDesk " + e);
        }

    }

    private void init(String username, CallbackContext callbackContext) {

        Context context = this.cordova.getActivity().getApplicationContext();

        if (username != null && username.length() > 0) {

            Fabric.with(this.cordova.getActivity());
            Logger.setLoggable(true);

            String zendeskDomain = cordova.getActivity().getString(cordova.getActivity().getResources()
                    .getIdentifier("zendesk_domain", "string", cordova.getActivity().getPackageName()));

            String zendeskAppId = cordova.getActivity().getString(cordova.getActivity().getResources()
                    .getIdentifier("zendesk_app_id", "string", cordova.getActivity().getPackageName()));

            String zendeskClientId = cordova.getActivity().getString(cordova.getActivity().getResources()
                    .getIdentifier("zendesk_client_id", "string", cordova.getActivity().getPackageName()));

            // Initialize the Support SDK with your Zendesk Support subdomain, mobile SDK app ID, and client ID.
            // Get these details from your Zendesk Support dashboard: Admin -> Channels -> Mobile SDK
            ZendeskConfig.INSTANCE.init(context, zendeskDomain, zendeskAppId, zendeskClientId);

            // Authenticate anonymously as a Zendesk Support user
            ZendeskConfig.INSTANCE.setIdentity(new AnonymousIdentity.Builder().withNameIdentifier(username).build());

            callbackContext.success();
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
