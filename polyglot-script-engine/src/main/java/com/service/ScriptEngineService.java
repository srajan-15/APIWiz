package com.service;
import org.graalvm.polyglot.*;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

@Service
public class ScriptEngineService {

    public static void main(String[] args) {
        ScriptEngineService service = new ScriptEngineService();

        // Running JavaScript script
        String jsResult = (String) service.runScript("js", "javascript-scripts/script.js");
        System.out.println("JavaScript Result: " + jsResult);

        // Running Python script
        String pyResult = (String) service.runScript("python", "python-scripts/script.py");
        System.out.println("Python Result: " + pyResult);
    }

    // Method to run scripts based on language
    public Object runScript(String language, String scriptPath) {
        try (Context context = Context.newBuilder(language).allowAllAccess(true).build()) {
            String script = new String(Files.readAllBytes(Paths.get(scriptPath)));
            Value result = context.eval(language, script);
            return result.isHostObject() ? result.asHostObject() : result.toString();
        } catch (IOException e) {
            return "Error reading script: " + e.getMessage();
        } catch (Exception e) {
            return "Error in " + language + " script: " + e.getMessage();
        }
    }
}
