package de.data_experts.jdk9_collections;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class PersonProvider {

    public static List<Person> getPersons() {
        List<Person> result = new ArrayList<>();
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(1995, Month.APRIL, 4)).withGeschlecht(Geschlecht.MAENNLICH).withGroesse(186).withName("Fritz").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(1930, Month.JULY, 15)).withGeschlecht(Geschlecht.WEIBLICH).withGroesse(162).withName("Daniela").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(1952, Month.JANUARY, 8)).withGeschlecht(Geschlecht.MAENNLICH).withGroesse(174).withName("Horst").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(1972, Month.APRIL, 26)).withGeschlecht(Geschlecht.WEIBLICH).withGroesse(165).withName("Luisa").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(1980, Month.SEPTEMBER, 1)).withGeschlecht(Geschlecht.MAENNLICH).withGroesse(190).withName("Günther").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(1999, Month.FEBRUARY, 28)).withGeschlecht(Geschlecht.I_DONT_KNOW).withGroesse(176).withName("Jaqueline").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(1968, Month.MARCH, 30)).withGeschlecht(Geschlecht.WEIBLICH).withGroesse(170).withName("Sylvia").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(1961, Month.MAY, 4)).withGeschlecht(Geschlecht.MAENNLICH).withGroesse(181).withName("Sigfried").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(2005, Month.JUNE, 15)).withGeschlecht(Geschlecht.WEIBLICH).withGroesse(153).withName("Tamara").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(1939, Month.APRIL, 12)).withGeschlecht(Geschlecht.MAENNLICH).withGroesse(164).withName("Fridolin").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(1955, Month.MARCH, 8)).withGeschlecht(Geschlecht.WEIBLICH).withGroesse(165).withName("Marie").get());
        result.add(Person.PersonBuilder.builder().withGeburtstag(LocalDate.of(2001, Month.JULY, 12)).withGeschlecht(Geschlecht.MAENNLICH).withGroesse(178).withName("Malte Torben Björn").get());
        return result;
    }
}
