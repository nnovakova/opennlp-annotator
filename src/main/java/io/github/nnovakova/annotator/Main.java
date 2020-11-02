package io.github.nnovakova.annotator;

import java.io.*;
import java.util.Set;

import static io.github.nnovakova.annotator.Config.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Entities entities = EntitiesReader.getEntities();
        Set<String> stopWords = StopWordsReader.getStopWords(STOP_WORDS_PATH);

        annotateInStream(entities, stopWords);
    }

    private static void annotateInStream(Entities entities, Set<String> stopWords) throws IOException {
        prepareCorpusForAnnotation(MED_BOOK);

        try (BufferedReader reader = new BufferedReader(new FileReader(RESOURCE_DIR + MED_BOOK_BY_SENTENCES))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULTED_ANNOTATED_CORPUS_PATH))) {
                String sentence;
                while ((sentence= reader.readLine()) != null) {
                    writer.write(SentenceAnnotator.annotate(sentence, entities, stopWords) + "\n");
                }
                System.out.println("DONE! CORPUS WAS ANNOTATED!");
            }
        }
    }

    private static void prepareCorpusForAnnotation(String book) throws IOException  {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(RESOURCE_DIR+book)))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(RESOURCE_DIR+MED_BOOK_BY_SENTENCES)))) {
                String sentence;
                while ((sentence = reader.readLine()) != null) {
                    writer.write(sentence.replace(".", ".\n"));
                }
            }
        }
    }
}
