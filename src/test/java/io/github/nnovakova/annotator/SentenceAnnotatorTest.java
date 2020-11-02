package io.github.nnovakova.annotator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class SentenceAnnotatorTest {
    Entities entities;
    Set<String> stopWords ;

    @Before
    public void setUp() throws Exception {
        entities = EntitiesReader.getEntities();
        stopWords = StopWordsReader.getStopWords(Config.STOP_WORDS_PATH);
    }

    @Test
    public void doubleWordEntity() {
        String expected = "<START:med-entity> Geschlossene Reposition <END> , <START:med-entity> Osteosynthese <END>  mittels zweier kanülierter Zugschrauben (38 + 40 mm) Vorgehen:";
        String input = "Geschlossene Reposition, Osteosynthese mittels zweier kanülierter Zugschrauben (38 + 40 mm) Vorgehen:";

        String actual = SentenceAnnotator.annotate(input, entities, stopWords);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void wordInWordCase() {
        String expected = "Aufgrund der zerstörten sternalen  <START:med-entity> Gelenkfläche <END>  luxiert das  <START:med-entity> Gelenk <END>  mit Wegnahme des Kugelspießes erneut.";
        String input = "Aufgrund der zerstörten sternalen Gelenkfläche luxiert das Gelenk mit Wegnahme des Kugelspießes erneut.";
        String actual = SentenceAnnotator.annotate(input, entities, stopWords);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void withoutNumber() {
        String expected = "Zudem sind sie in den frakturierten BWK XI eingezogen.";
        String input = "Zudem sind sie in den frakturierten BWK XI eingezogen.";

        String actual = SentenceAnnotator.annotate(input, entities, stopWords);

        Assert.assertEquals(expected, actual);
    }
}