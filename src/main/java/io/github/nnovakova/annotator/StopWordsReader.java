package io.github.nnovakova.annotator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.nnovakova.annotator.Utils.fromResourceDir;

class StopWordsReader {

    static Set<String> getStopWords(String relativePath) throws IOException {
        Set<String> stopWords;

        try (BufferedReader br = new BufferedReader(new FileReader(fromResourceDir(relativePath).toFile()))) {
            stopWords = br.lines().map(Utils::transformToken).collect(Collectors.toSet());
        }

        return stopWords;
    }
}
