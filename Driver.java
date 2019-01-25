import java.util.Scanner;

public class Driver {
    public static void main(String[] args){
        int size, iterations;
        double temperature;

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
        System.out.println();
        System.out.println("This program will solve the N Queen Problem using 2 algorithms. Solution will be formatted as follows: \n\n" +
                "Simulated Annealing (SA) \n" +
                "Queen Solution Sequence --- Number of Attacking Pairs --- Iterations --- Execution time \n" +
                "Genetic Algorithm (GE) \n" +
                "Queen Solution Sequence --- Number of Attacking Pairs --- Total Generations --- Execution time \n" +
                "Summary \n");
    }

    public static void displaySolution(int size, int iterations, double temperature, int instances){
        int[] displaySA, displayGE, costArr;
        double startTimeSA, endTimeSA, startTimeGE, endTimeGE;
        double durationSA = 0, durationGE = 0, percentSA = 0, percentGE = 0;
        int costSA = 0, costGE = 0;
        Search nQueenSolver = new Search(size);

        for(int i = 0; i < instances; i++) {
            startTimeGE = System.nanoTime();
            displayGE = nQueenSolver.genetics(iterations);
            endTimeGE = System.nanoTime();

            durationGE += (endTimeGE - startTimeGE) / Math.pow(10, 9);
            costArr = displayFormat(displayGE, size, (endTimeGE - startTimeGE) / Math.pow(10, 9), "GE");
            costGE += costArr[1];

            if(costArr[0] == 0){
                percentGE++;
            }
        }

        System.out.printf("Summary: \n" +
                "Algorithm 1: Simulated Annealing \n" +
                "Solution Found %%: %.2f" + "\n" +
                "Average Iterations: " + costSA/instances + "\n" +
                "Average Execution Time (s): %.2f" + "\n" +
                "Algorithm 2: Genetic \n" +
                "Solutions Found %%: %.2f" + "\n" +
                "Average Execution Time (s): %.2f" + "\n" +
                "Average Generations Depth: " + costGE/instances + "\n", percentSA/instances*100, durationSA/instances, percentGE/instances*100, durationGE/instances);

    }

    public static int[] displayFormat(int[] solution, int size, double duration, String algorithm){
        int[] temp = new int[2];
        System.out.print(algorithm + ": ");
        int length = size + 2;

        for(int i = 0; i < length; i++){
            if(i == length - 2){
                System.out.print("\t\t");
                temp[0] = solution[i];
            }
            else if(i == length - 1){
                System.out.print("\t\t");
                temp[1] = solution[i];
            }
            System.out.print(solution[i] + " ");
        }
        System.out.printf("\t %.2f", duration);
        System.out.println();

        return temp;
    }

}
