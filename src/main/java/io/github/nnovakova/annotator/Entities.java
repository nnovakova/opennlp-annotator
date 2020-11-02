package io.github.nnovakova.annotator;

import java.util.Map;

import static io.github.nnovakova.annotator.Utils.transformToken;

class Entities {
    private Map<String, String> entities;

    Entities(Map<String, String> entities) {
        this.entities = entities;
    }

    void add(String key) {
        entities.put(transformToken(key), Config.ENTITY);
    }

    int size() {
        return entities.size();
    }


    boolean has(String key) {
        return entities.containsKey(transformToken(key));
    }

    String get(String key) {
        return entities.get(transformToken(key));
    }
}
