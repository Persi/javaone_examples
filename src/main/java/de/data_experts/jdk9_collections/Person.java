package de.data_experts.jdk9_collections;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private final String name;

    private final Geschlecht geschlecht;

    // in cm
    private final int groesse;

    private final LocalDate geburtstag;

    private Person(String name, Geschlecht geschlecht, LocalDate geburtstag, int groesse) {
        this.name = name;
        this.geschlecht = geschlecht;
        this.groesse = groesse;
        this.geburtstag = geburtstag;
    }

    public String getName() {
        System.out.println("getName called on " + name);
        return name;
    }

    public Geschlecht getGeschlecht() {
        System.out.println("getGeschlecht called on " + name);
        return geschlecht;
    }

    public int getAlter() {
        System.out.println("getAlter called on " + name);
        return calculateAlter();
    }

    public int getGroesse() {
        System.out.println("getGroesse called on " + name);
        return groesse;
    }

    public LocalDate getGeburtstag() {
        System.out.println("getGeburtstag called on " + name);
        return geburtstag;
    }

    private int calculateAlter() {
        return Period.between(geburtstag, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", geschlecht=" + geschlecht +
                ", groesse=" + groesse +
                ", geburtstag=" + geburtstag +
                ", alter=" + calculateAlter() +
                '}';
    }

    public static class PersonBuilder {
        private String name;
        private Geschlecht geschlecht;
        private LocalDate geburtstag;
        private int groesse;

        private PersonBuilder() {
        }

        public static PersonBuilder builder() {
            return new PersonBuilder();
        }

        public PersonBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder withGeschlecht(Geschlecht geschlecht) {
            this.geschlecht = geschlecht;
            return this;
        }

        public PersonBuilder withGeburtstag(LocalDate geburtstag) {
            this.geburtstag = geburtstag;
            return this;
        }

        public PersonBuilder withGroesse(int groesse) {
            this.groesse = groesse;
            return this;
        }

        public Person get() {
            assert geburtstag != null;
            assert groesse != 0;
            assert name != null;
            assert geschlecht != null;
            return new Person(name, geschlecht, geburtstag, groesse);
        }
    }

}
