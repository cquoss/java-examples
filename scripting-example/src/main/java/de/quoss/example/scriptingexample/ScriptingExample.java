package de.quoss.example.scriptingexample;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptingExample {

    private void run() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        System.out.println("Engine factories: " + scriptEngineManager.getEngineFactories());
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("groovy");
        if (scriptEngine == null) {
            System.err.println("Unable to get script engine for 'groovy'.");
        }
    }

    public static void main(String[] args) {
        new ScriptingExample().run();
    }

}
