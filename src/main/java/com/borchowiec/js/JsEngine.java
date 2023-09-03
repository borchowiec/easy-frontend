package com.borchowiec.js;

public interface JsEngine {
    String evaluate(String script) throws EvaluationException;

    static JsEngine getInstance() {
        return new GraalvmJsEngine();
    }
}
