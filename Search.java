import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;
import java.lang.Math;

public class Search {
    private int size, populationCounter;
    private double temperature, mutation, fitRange;

    //Population Parameter
    public Search(int size){
        this.size = size;
        this.populationCounter = 100;
        this.mutation = 0.3;
        this.fitRange = 0.3;
    }

    //Common Methods for Annealing and Genetics
    public int[] randomize(){
        Random generate = new Random();
        int[] positions = new int[size];
        for(int i = 0; i < positions.length; i++){
            positions[i] = generate.nextInt(positions.length);
        }
        return positions;
    }

    public int[] randomizeSuccessor(int[] currentPositions){
        Random generate = new Random();
        int[] nextPositions = new int[currentPositions.length];
        System.arraycopy(currentPositions, 0, nextPositions, 0, currentPositions.length);
        int index = generate.nextInt(currentPositions.length);
        int value;

        while(currentPositions[index] == nextPositions[index]){
            value = generate.nextInt(currentPositions.length);
            nextPositions[index] = value;
        }

        return nextPositions;
    }

    public int[] formatResult(QueenNode result, int cost){
        int[] resultant = new int[size + 2];
        System.arraycopy(result.getQueenPositions(), 0, resultant, 0, size);
        resultant[size] = result.getAttackingPairs();
        resultant[size + 1] = cost;
        return resultant;
    }

    //Simulated Annealing
    public int[] simulatedAnnealing(double temperature) {
        this.temperature = temperature;

        QueenNode currentNode = new QueenNode(randomize());
        QueenNode nextNode;
        int fitness;
        int iterations = 1;

        while (currentNode.getAttackingPairs() != 0 && this.temperature > Math.pow(10,-9)) {
            nextNode = new QueenNode(randomizeSuccessor(currentNode.getQueenPositions()));
            fitness = currentNode.getAttackingPairs() - nextNode.getAttackingPairs();

            if (fitness > 0) {
                currentNode = new QueenNode(nextNode.getQueenPositions());
            } else {
                if (probability(fitness, this.temperature)) {
                    currentNode = new QueenNode(nextNode.getQueenPositions());
                }
            }
            temperatureMapping(iterations);
            iterations++;
        }
        return formatResult(currentNode, iterations);
    }

    public void temperatureMapping(int iterations){
        this.temperature *= 0.9985;
    }

    public boolean probability(int fitness, double temperature){
        return Math.random() < (Math.exp((fitness)/temperature));
    }

    //Genetics
    public int[] genetics(int iterations){
        QueenNode parent1, parent2, child;
        PriorityQueue<QueenNode> currentPopulation = generatePopulation();

        int generationCount = 0;
        while(true) {
            PriorityQueue<QueenNode> newPopulation = new PriorityQueue<>(populationCounter, new QueenNodeComparator());
            for (int i = 0; i < currentPopulation.size(); i++) {
                parent1 = new QueenNode(selectFrom(currentPopulation, null, fitRange));
                parent2 = new QueenNode(selectFrom(currentPopulation, parent1, fitRange));

                if(parent1.getAttackingPairs() == 0){
                    return formatResult(parent1, generationCount);
                }
                else if(parent2.getAttackingPairs() == 0){
                    return formatResult(parent2, generationCount);
                }

                child = new QueenNode(reproduce(parent1, parent2));

                if (Math.random() < mutation) {
                    child = new QueenNode(randomizeSuccessor(child.getQueenPositions()));
                }

                newPopulation.add(child);

                if(child.getAttackingPairs() == 0 || iterations == 0) {
                    return formatResult(child, generationCount);
                }
            }
            iterations--;
            generationCount++;
            currentPopulation = newPopulation;
        }
    }

    public PriorityQueue<QueenNode> generatePopulation(){
        PriorityQueue<QueenNode> temp = new PriorityQueue<>(populationCounter, new QueenNodeComparator());
        for(int i = 0; i < populationCounter; i++){
            temp.add(new QueenNode(randomize()));
        }
        return temp;
    }

    public int[] selectFrom(PriorityQueue<QueenNode> population, QueenNode checkParent, double fitRange){
        Random generate = new Random();
        int lowerBound = (int)(fitRange * population.size());
        int index = generate.nextInt(lowerBound);

        QueenNode[] populationArray = population.toArray(new QueenNode[population.size()]);
        Arrays.sort(populationArray, new QueenNodeComparator());

        while(checkParent != null && checkParent.equals(populationArray[index])){
            index = generate.nextInt(lowerBound);
        }

        return populationArray[index].getQueenPositions();
    }

    public int[] reproduce(QueenNode parent1, QueenNode parent2){
        Random generate = new Random();
        int locus = generate.nextInt(parent1.size() - 3) + 1;
        int[] childPositions = new int[parent1.size()];

        System.arraycopy(parent1.getQueenPositions(), 0, childPositions, 0, locus);
        System.arraycopy(parent2.getQueenPositions(), locus, childPositions, locus, parent1.size() - locus);

        return childPositions;
    }


}
