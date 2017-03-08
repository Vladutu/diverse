package ro.ucv.ace;

import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * Created by Geo on 05.03.2017.
 */
public class ClausesExtractor {

    private static final List<String> CLAUSE_NAMES = Arrays.asList("S", "SBAR", "SBARQ", "SINV", "SQ");

    public List<String> extractClauses(String sent) {
        Queue<Tree> clauses = new ArrayDeque<>();
        List<Tree> noClauses = new ArrayList<>();

        Sentence sentence = new Sentence(sent);
        Tree tree = sentence.parse();

        Pair<Tree, Tree> clausePair = findFirstClause(tree);
        Tree clause = clausePair.getRight();
        clauses.offer(clause);

        while (!clauses.isEmpty()) {
            Tree head = clauses.poll();
            boolean finished = false;

            do {
                clausePair = findFirstClause(head);
                if (clausePair != null) {
                    clause = clausePair.getRight();
                    removeChild(clausePair.getLeft(), clausePair.getRight());
                    clauses.add(clause);
                } else {
                    finished = true;
                }
            } while (!finished);
            noClauses.add(head);
        }

        List<String> result = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (Tree noClause : noClauses) {
            List<Label> yield = noClause.yield();
            if (yield.isEmpty()) {
                continue;
            }

            for (Label label : yield) {
                stringBuilder.append(label.value());
                stringBuilder.append(" ");
            }
            result.add(stringBuilder.toString());
            stringBuilder = new StringBuilder();
        }

        return result;
    }

    private void removeChild(Tree parent, Tree child) {
        Tree[] children = parent.children();

        int pos = -1;
        for (int i = 0; i < children.length; i++) {
            if (children[i] == child) {
                pos = i;
                break;
            }
        }

        parent.removeChild(pos);
    }

    private Pair<Tree, Tree> findFirstClause(Tree tree) {
        Queue<Pair<Tree, Tree>> queue = new ArrayDeque<>();
        for (Tree child : tree.getChildrenAsList()) {
            queue.offer(new ImmutablePair<>(tree, child));
        }

        while (!queue.isEmpty()) {
            Pair<Tree, Tree> head = queue.poll();
            if (CLAUSE_NAMES.contains(head.getRight().value())) {
                return head;
            }

            for (Tree child : head.getRight().getChildrenAsList()) {
                queue.offer(new ImmutablePair<>(head.getRight(), child));
            }
        }

        return null;
    }
}
