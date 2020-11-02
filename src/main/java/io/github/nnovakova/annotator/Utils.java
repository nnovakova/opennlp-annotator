package io.github.nnovakova.annotator;

import java.nio.file.Path;
import java.nio.file.Paths;

class Utils {

    static String transformToken(String t) {
        return t.toLowerCase();
    }

    static void println(String msg) {
        System.out.println(msg);
    }

    static Path fromResourceDir(String relativePath) {
        return Paths.get(Config.RESOURCE_DIR, relativePath);
    }
}
