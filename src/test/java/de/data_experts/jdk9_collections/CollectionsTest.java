package de.data_experts.jdk9_collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class CollectionsTest {

    @Test
    void testOldUnmodifiableLists() {
        System.out.println("testOldUnmodifiableLists");
        List<String> oldList = new ArrayList<>();
        oldList.add("1");
        oldList.add("2");
        oldList.add("3");
        List<String> unmodOldList = Collections.unmodifiableList(oldList);
        System.out.println(unmodOldList.toString());
        oldList.add("4");
        System.out.println(unmodOldList.toString());
    }

    @Test
    void testNewLists() {
        System.out.println("testNewLists");
        List<String> newList = List.of("1", "2", "3", "3");
        System.out.println(newList.toString());
        Assertions.assertThrows(UnsupportedOperationException.class, () -> newList.add("4"));
        System.out.println(newList.toString());
    }

    @Test
    void testOldUnmodifiableMaps() {
        System.out.println("testOldUnmodifiableMaps");
        Map<String, Integer> oldMap = new HashMap<>();
        oldMap.put("1", 42);
        oldMap.put("2", 43);
        oldMap.put("3", 44);
        Map<String, Integer> unmodOldMap = Collections.unmodifiableMap(oldMap);
        System.out.println(unmodOldMap.toString());
        oldMap.put("4", 45);
        System.out.println(unmodOldMap.toString());
    }

    @Test
    void testOldUnmodifiableMaps2() {
        System.out.println("testOldUnmodifiableMaps2");
        // der einzige Weg bis Java 8 um eine Map vor zu initialisieren!
        Map<String, Integer> oldMap = new HashMap<>() {{
            put("1", 42);
            put("2", 43);
            put("3", 44);
        }};
        Map<String, Integer> unmodOldMap = Collections.unmodifiableMap(oldMap);
        System.out.println(unmodOldMap.toString());
        oldMap.put("4", 45);
        System.out.println(unmodOldMap.toString());
    }

    @Test
    void testNewMaps() {
        System.out.println("testNewMaps");
        Map<String, Integer> newMap = Map.of("1", 42, "2", 43, "3", 44);
        System.out.println(newMap.toString());
        Assertions.assertThrows(UnsupportedOperationException.class, () -> newMap.put("4", 45));
        System.out.println(newMap.toString());
    }

    @Test
    void testNewMapsDuplicatesDisallowed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Map.of("1", 42, "1", 43));
    }

    @Test
    void testNewSets() {
        System.out.println("testNewSets");
        Set<String> newSet = Set.of("1", "2", "3");
        System.out.println(newSet.toString());
        Assertions.assertThrows(UnsupportedOperationException.class, () -> newSet.add("4"));
        System.out.println(newSet.toString());
    }

    @Test
    void testNewSetsDuplicatesDisallowed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Set.of("1", "1"));
    }
}
