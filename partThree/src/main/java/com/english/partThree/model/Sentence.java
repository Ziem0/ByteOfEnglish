package com.english.partThree.model;

import com.english.partTwo.enums.Colors;

public class Sentence {

    private Grama grama;
    private Tense tense;
    private Type type;

    private Person person;
    private Verb verb;
    private Adjective adjective;
    private Noun noun;

    private PhrasalVerbs phrasalVerbs;
    private Idiom idiom;

    public Sentence(Grama grama, Tense tense, Type type, Person person, Verb verb, Adjective adjective, Noun noun, PhrasalVerbs phrasalVerbs, Idiom idiom) {
        this.grama = grama;
        this.tense = tense;
        this.type = type;
        this.person = person;
        this.verb = verb;
        this.adjective = adjective;
        this.noun = noun;
        this.phrasalVerbs = phrasalVerbs;
        this.idiom = idiom;
    }

    public Grama getGrama() {
        return grama;
    }

    public Tense getTense() {
        return tense;
    }

    public Type getType() {
        return type;
    }

    public Person getPerson() {
        return person;
    }

    public Verb getVerb() {
        return verb;
    }

    public Adjective getAdjective() {
        return adjective;
    }

    public Noun getNoun() {
        return noun;
    }

    public PhrasalVerbs getPhrasalVerbs() {
        return phrasalVerbs;
    }

    public Idiom getIdiom() {
        return idiom;
    }

    public String printPL() {
        return String.format("%15s%s%s\n%s%s%s%s\n%15s%s"
                 ,Colors.CYAN.getBg(grama.getName())
                 ,Colors.MAGENTA.getBg(tense.getName())
                 ,Colors.CYAN.getBg(type.getName())
                 ,Colors.GREEN.getBg(person.getPl())
                 ,Colors.YELLOW.getBg(verb.getPl())
                 ,Colors.GREEN.getBg(adjective.getPl())
                 ,Colors.YELLOW.getBg(noun.getPl())
                 ,Colors.CYAN.getBg(phrasalVerbs.getPl())
                 ,Colors.MAGENTA.getBg(idiom.getPl()));
    }

    public String printENG() {
        return String.format("%15s%s%s\n%s%s%s%s\n%15s%s"
                ,Colors.CYAN.getBg(grama.toString())
                ,Colors.MAGENTA.getBg(tense.toString())
                ,Colors.CYAN.getBg(type.toString())
                ,Colors.GREEN.getBg(person.toString())
                ,Colors.YELLOW.getBg(verb.toString())
                ,Colors.GREEN.getBg(adjective.toString())
                ,Colors.YELLOW.getBg(noun.toString())
                ,Colors.CYAN.getBg(phrasalVerbs.toString())
                ,Colors.MAGENTA.getBg(idiom.toString()));
    }
}
