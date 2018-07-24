package com.english.partThree.model;

import com.english.partThree.dao.SentenceDao;
import com.english.partTwo.enums.Colors;

public class Sentence {

    private Grama grama;
    private Tense tense;
    private Type type;

    private Person person;
    private Verb verb;
    private Adjective adjective;
    private Noun noun;

    private Phrasal phrasal;
    private Idiom idiom;

    public Sentence(Grama grama, Tense tense, Type type, Person person, Verb verb, Adjective adjective, Noun noun, Phrasal phrasal, Idiom idiom) {
        this.grama = grama;
        this.tense = tense;
        this.type = type;
        this.person = person;
        this.verb = verb;
        this.adjective = adjective;
        this.noun = noun;
        this.phrasal = phrasal;
        this.idiom = idiom;
    }

    public void upgradeAll(SentenceDao dao) {
        grama.upgrade(dao);
        tense.upgrade(dao);
        type.upgrade(dao);
//        person.setUsed();                   //--->
        verb.upgrade(dao);
        adjective.upgrade(dao);
        noun.upgrade(dao);
        phrasal.upgrade(dao);
        idiom.upgrade(dao);
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

    public Phrasal getPhrasal() {
        return phrasal;
    }

    public Idiom getIdiom() {
        return idiom;
    }

    public String printPL() {
        return
        String.format("grammar--> %s %s %s\nsentence--> %s %s %s %s\nphrasalVerbs--> %s %s \nidiom--> %s %s"
                , Colors.GREEN.getBg(type.getName())
                , Colors.GREEN.getBg(tense.getName())
                , Colors.GREEN.getBg(grama.getName())
                , Colors.GREEN.getBg(person.getPl())
                , Colors.GREEN.getBg(verb.getPl())
                , Colors.GREEN.getBg(adjective.getPl())
                , Colors.GREEN.getBg(noun.getPl())
                , Colors.GREEN.getBg(phrasal.getPl())
                , Colors.GREEN.getBg(phrasal.getExample())
                , Colors.GREEN.getBg(idiom.getPl())
                , Colors.GREEN.getBg(idiom.getExample()));
    }

    public String printENG() {
        return String.format("sentence--> %s %s %s %s\nphrasalVerb--> %s %s\nidiom--> %s %s\n"
                ,Colors.GREEN.getBg(person.toString())
                ,Colors.GREEN.getBg(verb.toString())
                ,Colors.GREEN.getBg(adjective.toString())
                ,Colors.GREEN.getBg(noun.toString())
                ,Colors.GREEN.getBg(phrasal.toString())
                ,Colors.GREEN.getBg(phrasal.getExplain())
                ,Colors.GREEN.getBg(idiom.toString())
                ,Colors.GREEN.getBg(idiom.getExplain())
        );
    }
}
