package com.borchowiec.project;

import com.borchowiec.Properties;
import com.borchowiec.ioc.IocContainer;
import com.borchowiec.js.JsEngine;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GlobalVariables {
    private final JsEngine jsEngine;

    @Getter
    private Map<String, String> globalVariables = Collections.unmodifiableMap(new HashMap<>());

    private GlobalVariables() {
        IocContainer iocContainer = IocContainer.getInstance();
        this.jsEngine = iocContainer.getBean(JsEngine.class);
    }

    public static GlobalVariables getInstance() {
        return new GlobalVariables();
    }

    public void recalculateGlobalVariables() {
        this.globalVariables = Collections.unmodifiableMap(readGlobalVariablesFromFile());
    }

    @SneakyThrows
    private Map<String, String> readGlobalVariablesFromFile() {
        Map<String, String> globalVariables = new HashMap<>();
        Path globalVariablesPath = Path.of(Properties.GLOBAL_VARIABLES_SCRIPT_PATH);
        if (!globalVariablesPath.toFile().isFile()) {
            return globalVariables;
        }

        String globalVariablesScript = Files.readString(globalVariablesPath);
        String script = globalVariablesScript + "\n" + "JSON.stringify(global);";

        try {
            String rawGlobalVariables = jsEngine.evaluate(script);
            JsonObject jsonGlobalVariables = new Gson().fromJson(rawGlobalVariables, JsonObject.class);

            jsonGlobalVariables.entrySet()
                               .forEach(entry -> globalVariables.put(entry.getKey(), readValueAsString(entry.getValue())));
        } catch (Exception e) {
            log.error("Error while evaluating global variables, due to: {}", e.getMessage());
        }

        return globalVariables;
    }

    private String readValueAsString(JsonElement value) {
        if (value.isJsonPrimitive()) {
            return value.getAsString();
        }
        return value.toString();
    }

    public String getGlobalVariable(String variableName) {
        return globalVariables.getOrDefault(variableName, "");
    }
}
