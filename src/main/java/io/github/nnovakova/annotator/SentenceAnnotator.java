package io.github.nnovakova.annotator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Function;

import static io.github.nnovakova.annotator.Utils.transformToken;

class SentenceAnnotator {

    static String annotate(final String sentence, Entities entities, Set<String> stopWords) {
        String[] words = padCommas(sentence).split(" ");
        Set<String> found = new HashSet<>();
        LinkedList<String> annotatedSentence = new LinkedList<>();

        Function<String, Boolean> check = (w) -> entities.has(w) && notIn(stopWords, w) && notIn(found, w);

        for (int i = 0; i < words.length; i++) {
            String currentWord = words[i];
            String annotatedWord = currentWord;

            Function<Integer, String> twoWords = (j) -> (words[j - 1] + " " + currentWord).trim();

            if (i != 0 && check.apply(twoWords.apply(i))) {
                annotatedWord = annotateWord(entities, found, twoWords.apply(i));
                annotatedSentence.removeLast(); //remove previously annotated word, since we just found bigger word which is also an entity
            } else if (check.apply(currentWord))
                annotatedWord = annotateWord(entities, found, currentWord);

            annotatedSentence.add(annotatedWord);
        }

        return unpadCommas(String.join(" ", annotatedSentence)).trim();
    }

    private static String padCommas(String s) {
        return s.replaceAll(",", " , ");
    }

    private static String unpadCommas(String s) {
        return s.replaceAll(" , ", ",");
    }

    private static String annotateWord(Entities entities, Set<String> found, String word) {
        found.add(transformToken(word));
        return String.format(wrapInSpaces("<START:%s> %s <END>"), entities.get(word), word);
    }

    private static String wrapInSpaces(String s) {
        return " " + s + " ";
    }

    private static String removeCommas(String t) {
        return t.replace(",", "");
    }

    private static boolean notIn(Set<String> s, String key) {
        return !s.contains(transformToken(key));
    }
}
