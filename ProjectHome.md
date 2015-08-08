**Important announcement: I'm looking for a new maintainer!**

Due to lack of time, I've decided to dismiss this project. If you're interested, you may become the new maintainer. Please contact me to c(dot)cerbo(at)gmail(dot)com and I'll give you the commit right for the svn repository.

---

WordnetAPI is a Java interface to the famous [WordNet](http://wordnet.princeton.edu/) database of lexical relationships.

It converts the Wordnet Dictionary in an embedded [H2 Database](http://www.h2database.com) and access the data through the Java Persistence API (currently Toplink essential implementation), providing a **stictly object-oriented interface**, instead of the procedural one in the original Wordnet C library.

This is the domain model:

![http://wordnetapi.googlecode.com/svn/wordnetapi/trunk/WordnetAPI/doc/UML/Class_Diagramm.png](http://wordnetapi.googlecode.com/svn/wordnetapi/trunk/WordnetAPI/doc/UML/Class_Diagramm.png)

The access to entities happens with the DAO Pattern (see package [eu.kostia.dao](http://wordnetapi.googlecode.com/svn/wordnetapi/trunk/WordnetAPI/src/eu/kostia/dao)).

## Examples of code usage ##
To look for a lemma in the dictionary:
```
WordnetDictionary dictionary = new WordnetDictionary();
String lemma = "conceptualization";
List<Word> result = dictionary.lookup(lemma);
```

In [WordnetCLI](http://wordnetapi.googlecode.com/svn/wordnetapi/trunk/WordnetAPI/src/eu/kostia/main/WordnetCLI.java) the result is also formatted:
```
LEMMA: conceptualization (NOUN noun.act): inventing or contriving an idea or explanation and formulating it mentally
SEMANTIC SET:
 SAME:
   formulation (NOUN noun.act): inventing or contriving an idea or explanation and formulating it mentally
   conceptualisation (NOUN noun.act): inventing or contriving an idea or explanation and formulating it mentally
 DERIVATIONALLY_RELATED_FORM:
   conceptualize (VERB verb.creation): have the idea for; "He conceived of a robot that would help paralyzed patients"; "This library was well conceived"
 HYPERNYM:
   creating_by_mental_acts (NOUN noun.act): the act of creating something by thinking
 HYPONYM:
   plan_of_attack (NOUN noun.act): ideas or actions intended to deal with a problem or situation; "his approach to every problem is to draw up a list of pros and cons"; "an attack on inflation"; "his plan of attack was misguided"
   framing (NOUN noun.act): formulation of the plans and important details; "the framing of judicial decrees"
   attack (NOUN noun.act): ideas or actions intended to deal with a problem or situation; "his approach to every problem is to draw up a list of pros and cons"; "an attack on inflation"; "his plan of attack was misguided"
   approach (NOUN noun.act): ideas or actions intended to deal with a problem or situation; "his approach to every problem is to draw up a list of pros and cons"; "an attack on inflation"; "his plan of attack was misguided"

LEMMA: conceptualization (NOUN noun.cognition): an elaborated concept
SEMANTIC SET:
 SAME:
   conceptualisation (NOUN noun.cognition): an elaborated concept
   conceptuality (NOUN noun.cognition): an elaborated concept
 DERIVATIONALLY_RELATED_FORM:
   conceptualize (VERB verb.creation): have the idea for; "He conceived of a robot that would help paralyzed patients"; "This library was well conceived"
 HYPERNYM:
   concept (NOUN noun.cognition): an abstract or general idea inferred or derived from specific instances
   construct (NOUN noun.cognition): an abstract or general idea inferred or derived from specific instances
   conception (NOUN noun.cognition): an abstract or general idea inferred or derived from specific instances
 HYPONYM:
   perception (NOUN noun.cognition): a way of conceiving something; "Luther had a new perception of the Bible"
```

Given an inflected form, you can retrieve the base forms using the [WordNet's morphological processing](http://wordnet.princeton.edu/man/morphy.7WN). For example:
```
Morphy morphy = new Morphy();
String[] baseforms = morphy.stem("axes");
```
will be return "axis", "ax", "axe"

More code usage examples in the [unit-tests source folder](http://wordnetapi.googlecode.com/svn/wordnetapi/trunk/WordnetAPI/test).


This project is related to [Text Analysis](http://code.google.com/p/text-analysis/) a more general framework for Natural Language Processing and Text Mining.


My name is Costantino and feel free to contact me at:
![http://wordnetapi.googlecode.com/svn/wordnetapi/trunk/WordnetAPI/doc/email.png](http://wordnetapi.googlecode.com/svn/wordnetapi/trunk/WordnetAPI/doc/email.png)