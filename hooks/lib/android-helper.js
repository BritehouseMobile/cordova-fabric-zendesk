var fs = require("fs");
var path = require("path");
var utilities = require("./utilities");

module.exports = {

    addFabricBuildToolsGradle: function() {

        var buildGradle = utilities.readBuildGradle();

        buildGradle += [
            "",
            "// Fabric Cordova Plugin - Start Fabric Build Tools ",
            "buildscript {",
            "    repositories {",
            "        maven { url 'https://maven.fabric.io/public' }",
            "        maven { url 'https://zendesk.jfrog.io/zendesk/repo' }",
            "    }",
            "    dependencies {",
            "        classpath 'io.fabric.tools:gradle:1.+'",
            "    }",
            "}",
            "",
            "repositories {",
            "    maven { url 'https://zendesk.jfrog.io/zendesk/repo' }",
            "    maven { url 'https://maven.fabric.io/public' }",
            "}",
            "",
            "dependencies {",
            "    compile('com.zendesk:sdk:1.10.1.1@aar') {",
            "      transitive = true;",
            "    }",
            "    compile('io.fabric.sdk.android:fabric:1.3.17@aar') {",
            "      transitive = true;",
            "    }",
            "    compile group: 'com.zopim.android', name: 'sdk', version: '1.3.5.1'",
            "}",
            "apply plugin: 'io.fabric'",
            "// Fabric Cordova Plugin - End Fabric Build Tools",
        ].join("\n");

        utilities.writeBuildGradle(buildGradle);
    },

    removeFabricBuildToolsFromGradle: function() {

        var buildGradle = utilities.readBuildGradle();

        buildGradle = buildGradle.replace(/\n\/\/ Fabric Cordova Plugin - Start Fabric Build Tools[\s\S]*\/\/ Fabric Cordova Plugin - End Fabric Build Tools/, "");

        utilities.writeBuildGradle(buildGradle);
    }
};