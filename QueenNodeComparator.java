import java.util.Comparator;

public class QueenNodeComparator implements Comparator<QueenNode> {

    @Override
    public int compare(QueenNode current, QueenNode next) {
        return (current.getAttackingPairs() - next.getAttackingPairs());
    }
}
