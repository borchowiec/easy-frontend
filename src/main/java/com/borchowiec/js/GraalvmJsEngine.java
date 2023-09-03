package com.borchowiec.js;

import org.graalvm.polyglot.Context;

class GraalvmJsEngine implements JsEngine {
    @Override
    public String evaluate(String script) throws EvaluationException {
        try (Context context = Context.create("js")) {
            return context.eval("js", script).toString();
        } catch (Exception e) {
            throw new EvaluationException(e.getMessage());
        }
    }
}
