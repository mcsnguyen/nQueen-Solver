import java.util.Scanner;

public class Driver {
    public static void main(String[] args){
        title(100);

        Scanner read = new Scanner(System.in);

        displaySolution(21, 5000, 100, 550);
    }

    public static void title(int lines){
        for(int i = 0; i < lines; i++){
            System.out.print("-");
        }
        System.out.println();
        for(int i = 0; i < lines/10; i++){
            System.out.print("\t");
        }

        System.out.print("N Queen Solver \n");

        for(int i = 0; i < lines; i++){
            System.out.print("-");
        }

        String col1 = "Queen Solution Sequence";
        String col2 = "Number of Attacking Pairs";
        String col3 = "Iterations";
        String col4 = "Execution Time(s)";

        System.out.println();
        System.out.printf("This program will solve the N Queen Problem using 2 algorithms. Solution will be formatted as follows: \n\n" +
                "Simulated Annealing (SA) \n" +
                "%20s" + "%60s" + "%15s" + "%20s" +
                "\nGenetic Algorithm (GE) \n" +
                "%20s" + "%60s" + "%15s" + "%20s \n", col1, col2, col3, col4, col1, col2, col3, col4);
    }

    public static void displaySolution(int size, int maxGeneticIterations, double temperature, int testQuantity){
        int[] displaySA, displayGE, costArr;
        double startTime, endTime;
        double durationSA = 0, durationGE = 0, percentSA = 0, percentGE = 0;
        int costSA = 0, costGE = 0;
        Search nQueenSolver = new Search(size);

        for(int i = 0; i < testQuantity; i++) {
            startTime = System.nanoTime();
            displaySA = nQueenSolver.simulatedAnnealing(temperature);
            endTime = System.nanoTime();

            durationSA += (endTime - startTime) / Math.pow(10, 9);
            costArr = displayFormat(displaySA, size, (endTime - startTime) / Math.pow(10, 9), "SA");
            costSA += costArr[1];

            if(costArr[0] == 0){
                percentSA++;
            }
        }

        for(int i = 0; i < testQuantity; i++) {
            startTime = System.nanoTime();
            displayGE = nQueenSolver.genetics(maxGeneticIterations);
            endTime = System.nanoTime();

            durationGE += (endTime - startTime) / Math.pow(10, 9);
            costArr = displayFormat(displayGE, size, (endTime - startTime) / Math.pow(10, 9), "GE");
            costGE += costArr[1];

            if(costArr[0] == 0){
                percentGE++;
            }
        }

        System.out.printf("Summary: \n" +
                "Algorithm 1: Simulated Annealing \n" +
                "Solution Found %%: %.2f" + "\n" +
                "Average Iterations: " + costSA/testQuantity + "\n" +
                "Average Execution Time (s): %.2f" + "\n" +
                "Algorithm 2: Genetic \n" +
                "Solutions Found %%: %.2f" + "\n" +
                "Average Execution Time (s): %.2f" + "\n" +
                "Average Generations Depth: " + costGE/testQuantity + "\n", percentSA/testQuantity*100, durationSA/testQuantity, percentGE/testQuantity*100, durationGE/testQuantity);

    }

    public static int[] displayFormat(int[] solution, int size, double duration, String algorithm){
        int[] temp = new int[2];
        System.out.print(algorithm + ": ");
        int length = size + 2;

        for(int i = 0; i < length; i++){
            if(i == length - 2){
                System.out.print("\t\t\t\t");
                temp[0] = solution[i];
            }
            else if(i == length - 1){
                System.out.print("\t\t\t\t\t");
                temp[1] = solution[i];
            }
            System.out.print(solution[i] + " ");
        }
        System.out.printf("\t\t\t%.2f", duration);
        System.out.println();

        return temp;
    }

}
