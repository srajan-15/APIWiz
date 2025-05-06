package com.controller;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/polyglot")
public class PolyglotController {

    @PostMapping("/execute")
    public Object runScript(@RequestParam String language, @RequestBody String script) {
        try (Context context = Context.create(language)) {
            // Execute the script and return the result as a Java object
            Value result = context.eval(language, script);
            return result.as(Object.class); // Convert the result to a Java object
        } catch (Exception e) {
            return "Error executing script: " + e.getMessage();
        }
    }
}
