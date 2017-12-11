
<!--
# license: Licensed to the Apache Software Foundation (ASF) under one
#         or more contributor license agreements.  See the NOTICE file
#         distributed with this work for additional information
#         regarding copyright ownership.  The ASF licenses this file
#         to you under the Apache License, Version 2.0 (the
#         "License"); you may not use this file except in compliance
#         with the License.  You may obtain a copy of the License at
#
#           http://www.apache.org/licenses/LICENSE-2.0
#
#         Unless required by applicable law or agreed to in writing,
#         software distributed under the License is distributed on an
#         "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
#         KIND, either express or implied.  See the License for the
#         specific language governing permissions and limitations
#         under the License.
-->

# cordova-fabric-zendesk
Cordova plugin for Fabric Zendesk Integration

## PLEASE NOTE THIS IS A BETA RELEASE AND ONLY CURRENTLY SUPPORTS ANDROID

This plugin defines a global `zendesk` object, which allows you to launch various Zendesk related views as exposed by the Zendesk SDK. 

Although the object is in the global scope, it is not available until after the `deviceready` event.

```js
document.addEventListener("deviceready", onDeviceReady, false);
function onDeviceReady() {
    console.log(window.zendesk);
}
```

## Installation

    This plugin requires quite a few variables to work properly. Don't blame me, blame ZenDesk ;}

    cordova plugin add https://github.com/k1dbl4ck/cordova-fabric-zendesk \
    --variable FABRIC_API_KEY=<your fabric.io API key> \ 
    --variable CHAT_API_KEY=<your zendesk chat API key> \ 
    --variable ZENDESK_DOMAIN=<your zendesk domain - e.g. https://mydomain.zendesk.com>
    --variable ZENDESK_APP_ID=<your zendesk app id> \
    --variable ZENDESK_CLIENT_ID=<your zendesk client id> 

## Functions

- window.zendesk.init(username, successCallback, errorCallback)
- window.zendesk.tickets()
- window.zendesk.help()
- window.zendesk.chat()


## window.zendesk.init(username, successCallback, errorCallback)

Only needs to be called once. This initializes the SDK (and on a lower level allocates memory for it) with a username to use for other interactions. 


## window.zendesk.tickets()

Opens the ticket interface.


## window.zendesk.help()

Opens the help interface.


## window.zendesk.chat()

Opens the chat interface.


### Supported Platforms

- Android



### Android Quirks

You may need to add 

```
dexOptions {
    jumboMode = true
}
```

To the android section of your build.gradle (depending on how large your app is)

