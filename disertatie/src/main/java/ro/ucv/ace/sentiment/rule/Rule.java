package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;

public interface Rule {

    void execute(Dependency dependency, Sentence sentence);

    boolean applies(Dependency dependency);
}
