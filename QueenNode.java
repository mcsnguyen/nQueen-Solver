public class QueenNode{
    private int[] queenPositions;
    private int attackingPairs;

    public QueenNode(int[] queenPositions){
        this.queenPositions = new int[queenPositions.length];
        setQueenPositions(queenPositions);
        countPairs(queenPositions);
    }

    public void setQueenPositions(int[] queenPositions){
        System.arraycopy(queenPositions, 0, this.queenPositions, 0, queenPositions.length);
    }

    public int[] getQueenPositions(){
        return queenPositions;
    }

    public void countPairs(int[] queensPositions){
        int indicesDifference, upperDiagonal, lowerDiagonal;
        int pairs = 0;

        for(int i = 0; i < queensPositions.length - 1; i++){
            for(int j = i + 1; j < queensPositions.length; j++){
                indicesDifference = j - i;
                upperDiagonal = queensPositions[j] - indicesDifference;
                lowerDiagonal = queensPositions[j] + indicesDifference;
                if(queensPositions[i] == queensPositions[j] ||  queensPositions[i] == upperDiagonal || queensPositions[i] == lowerDiagonal)
                    pairs++;
            }
        }
        this.attackingPairs = pairs;
    }

    public int getAttackingPairs(){
        return attackingPairs;
    }

    public int size(){
        return queenPositions.length;
    }
}
