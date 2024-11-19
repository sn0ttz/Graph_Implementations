package Implementacao_04;

import java.util.Arrays;

public class TransportationProblem {

    static int INF = 1000;

    public static void main(String[] args) {
        // Input data: grid, supply, and demand
        int[][] grid = {{3, 1, 7, 4}, {2, 6, 5, 9}, {8, 3, 3, 2}};
        int[] supply = {300, 400, 500};
        int[] demand = {250, 350, 400, 200};

        // Solve the transportation problem and print the result
        int ans = solveTransportationProblem(grid, supply, demand);
        System.out.println("The basic feasible solution is " + ans);
    }

    // Function to solve the transportation problem
    public static int solveTransportationProblem(int[][] grid, int[] supply, int[] demand) {
        int n = grid.length;  // Number of rows
        int m = grid[0].length;  // Number of columns
        int ans = 0;  // Initialize the answer

        // Loop until supply and demand are exhausted
        while (!isEmpty(supply) && !isEmpty(demand)) {
            int[] rowDiff = new int[n];  // Array to store row differences
            int[] colDiff = new int[m];  // Array to store column differences

            // Calculate row and column differences
            findDiff(grid, rowDiff, colDiff);

            // Find maximum differences in rows and columns
            int maxi1 = Arrays.stream(rowDiff).max().getAsInt();
            int maxi2 = Arrays.stream(colDiff).max().getAsInt();

            // Check whether the maximum row difference is greater than or equal to the maximum column difference
            if (maxi1 >= maxi2) {
                for (int i = 0; i < n; i++) {
                    if (rowDiff[i] == maxi1) {
                        int mini1 = Arrays.stream(grid[i]).min().getAsInt();  // Find minimum element in the row
                        for (int j = 0; j < m; j++) {
                            if (grid[i][j] == mini1) {
                                int mini2 = Math.min(supply[i], demand[j]);  // Calculate the minimum of supply and demand
                                ans += mini2 * mini1;  // Update the answer
                                supply[i] -= mini2;  // Adjust supply and demand
                                demand[j] -= mini2;
                                if (demand[j] == 0) {
                                    for (int r = 0; r < n; r++) {
                                        grid[r][j] = INF;  // Mark the entire column as exhausted
                                    }
                                } else {
                                    Arrays.fill(grid[i], INF);  // Mark the entire row as exhausted
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            } else {
                // If the maximum column difference is greater
                for (int j = 0; j < m; j++) {
                    if (colDiff[j] == maxi2) {
                        int mini1 = INF;
                        for (int[] ints : grid) {
                            mini1 = Math.min(mini1, ints[j]);  // Find the minimum element in the column
                        }

                        for (int i = 0; i < n; i++) {
                            int val2 = grid[i][j];
                            if (val2 == mini1) {
                                int mini2 = Math.min(supply[i], demand[j]);  // Calculate the minimum of supply and demand
                                ans += mini2 * mini1;  // Update the answer
                                supply[i] -= mini2;  // Adjust supply and demand
                                demand[j] -= mini2;
                                if (demand[j] == 0) {
                                    for (int r = 0; r < n; r++) {
                                        grid[r][j] = INF;  // Mark the entire column as exhausted
                                    }
                                } else {
                                    Arrays.fill(grid[i], INF);  // Mark the entire row as exhausted
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return ans;  // Return the final answer
    }

    // Function to check if an array is empty
    public static boolean isEmpty(int[] array) {
        for (int value : array) {
            if (value != 0) {
                return false;
            }
        }
        return true;
    }

    // Function to calculate row and column differences in the grid
    public static void findDiff(int[][] grid, int[] rowDiff, int[] colDiff) {
        for (int i = 0; i < grid.length; i++) {
            int[] arr = Arrays.copyOf(grid[i], grid[i].length);  // Copy the row
            Arrays.sort(arr);  // Sort the row
            rowDiff[i] = arr[1] - arr[0];  // Calculate row difference
        }

        for (int col = 0; col < grid[0].length; col++) {
            int[] arr = new int[grid.length];
            for (int i = 0; i < grid.length; i++) {
                arr[i] = grid[i][col];  // Collect elements from the column
            }
            Arrays.sort(arr);  // Sort the column elements
            colDiff[col] = arr[1] - arr[0];  // Calculate column difference
        }
    }
}