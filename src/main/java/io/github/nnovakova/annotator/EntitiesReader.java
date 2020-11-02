package io.github.nnovakova.annotator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static io.github.nnovakova.annotator.Utils.*;

class EntitiesReader {

    static Entities getEntities() throws IOException {
        Entities entities = new Entities(new HashMap<>());
        BufferedReader br = new BufferedReader(new FileReader(fromResourceDir("entities.csv").toFile()));
        String entity;

        while ((entity = br.readLine()) != null) {
            entity = removeDoubleQuotes(entity);

            if (entity.contains(",")) {
                parseComplexEntity(entities, entity);
            } else {
                entities.add(entity);
            }
        }
        //println("Entities size: " + entities.size());
        return entities;
    }

    private static void parseComplexEntity(Entities entities, String entity) {
        String[] complexEntity = entity.split(",");

        String firstEntity = complexEntity[0];
        entities.add(firstEntity);
        //println("Complex Term First Part: " + firstEntity);

        if (firstEntity.contains(" ")) {
            LinkedList<String> parsedEntities = parseEntity(firstEntity);
            entities.add(parsedEntities.getLast());
        }

        String secondEntity =
                complexEntity[1].replaceFirst(" ", "")
                        + (complexEntity[1].endsWith("-") ? "" : " ")
                        + firstEntity;

        entities.add(secondEntity);
        //println("Complex Term with Coma: " + secondEntity);
    }

    private static String removeDoubleQuotes(String s) {
        return s.replaceAll("\"", "");
    }

    private static LinkedList<String> parseEntity(String entity) {
        String[] complexEntity = entity.split(" ");
        LinkedList<String> l = Arrays.stream(complexEntity)
                .filter(s -> !s.matches("[0-9]"))
                .filter(s -> !s.matches("^(?=[MDCLXVI])M*D?C{0,4}L?X{0,4}V?I{0,4}$"))
                .filter(s -> (s.length() > 1))
                .collect(Collectors.toCollection(LinkedList::new));
        //println("Entity: " + l.toString());

        return l;
    }
}
