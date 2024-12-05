import java.io.*;
import java.util.*;

public class TransportationProblem {

    private static class TransportData {
        int[] offer;
        int[] demmand;
        int[][] cost;
        boolean ghostDest;
        boolean ghostOrig;

        private static int solve(int[] offer, int[] demmand, int[][] cost, int[][] transport) {
            int m = offer.length;
            int n = demmand.length;
            int totalCost = 0;

            int[] remainingOff = Arrays.copyOf(offer, m);
            int[] remainingDemm = Arrays.copyOf(demmand, n);

            List<Cell> cells = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    cells.add(new Cell(i, j, cost[i][j]));
                }
            }
            cells.sort(Comparator.comparingInt(c -> c.cost));

            for (Cell cell : cells) {
                int i = cell.i;
                int j = cell.j;

                if (remainingOff[i] > 0 && remainingDemm[j] > 0) {
                    int quant = Math.min(remainingOff[i], remainingDemm[j]);
                    transport[i][j] = quant;
                    remainingOff[i] -= quant;
                    remainingDemm[j] -= quant;
                    totalCost += quant * cost[i][j];
                }

                boolean offersMet = true;
                for (int o : remainingOff) {
                    if (o > 0) {
                        offersMet = false;
                        break;
                    }
                }
                boolean demmandsMet = true;
                for (int d : remainingDemm) {
                    if (d > 0) {
                        demmandsMet = false;
                        break;
                    }
                }
                if (offersMet || demmandsMet) {
                    break;
                }
            }

            return totalCost;
        }

        private static TransportData rebalance(int[] offer, int[] demmand, int[][] cost, int ogOffers,
                int ogDemmands) {
            int sumoffer = Arrays.stream(offer).sum();
            int sumdemm = Arrays.stream(demmand).sum();

            int[] balancedOffer = Arrays.copyOf(offer, offer.length);
            int[] balancedDemm = Arrays.copyOf(demmand, demmand.length);
            int[][] balancedCost = new int[cost.length][cost[0].length];

            for (int i = 0; i < cost.length; i++) {
                balancedCost[i] = Arrays.copyOf(cost[i], cost[i].length);
            }

            boolean ghostDest = false;
            boolean ghostOrig = false;

            if (sumoffer > sumdemm) {
                int diferenca = sumoffer - sumdemm;
                balancedDemm = Arrays.copyOf(balancedDemm, balancedDemm.length + 1);
                balancedDemm[balancedDemm.length - 1] = diferenca; // demmand fictícia

                for (int i = 0; i < balancedCost.length; i++) {
                    balancedCost[i] = Arrays.copyOf(balancedCost[i], balancedCost[i].length + 1);
                    balancedCost[i][balancedCost[i].length - 1] = 0;
                }

                ghostDest = true;
            } else if (sumdemm > sumoffer) {
                int diferenca = sumdemm - sumoffer;
                balancedOffer = Arrays.copyOf(balancedOffer, balancedOffer.length + 1);
                balancedOffer[balancedOffer.length - 1] = diferenca; // offer fictícia

                int[][] novocost = new int[balancedCost.length + 1][balancedCost[0].length];
                for (int i = 0; i < balancedCost.length; i++) {
                    System.arraycopy(balancedCost[i], 0, novocost[i], 0, balancedCost[i].length);
                }
                for (int j = 0; j < balancedCost[0].length; j++) {
                    novocost[novocost.length - 1][j] = 0;
                }
                balancedCost = novocost;

                ghostOrig = true;
            }

            return new TransportData(balancedOffer, balancedDemm, balancedCost, ghostDest,
                    ghostOrig);
        }

        TransportData(int[] offer, int[] demmand, int[][] cost, boolean ghostDest, boolean ghostOrig) {
            this.offer = offer;
            this.demmand = demmand;
            this.cost = cost;
            this.ghostDest = ghostDest;
            this.ghostOrig = ghostOrig;
        }

        private static class Cell {
            int i;
            int j;
            int cost;

            Cell(int i, int j, int cost) {
                this.i = i;
                this.j = j;
                this.cost = cost;
            }
        }

        public static void main(String[] args) throws IOException {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please type the name of the archive containing the data: ");
            String fileName = scanner.nextLine();

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String[] firstLine = reader.readLine().trim().split("\\s+");
            int ogOffers = Integer.parseInt(firstLine[0]);
            int ogDemmands = Integer.parseInt(firstLine[1]);

            int[] offer = new int[ogOffers];
            for (int i = 0; i < ogOffers; i++) {
                offer[i] = Integer.parseInt(reader.readLine().trim());
            }

            int[] demmand = new int[ogDemmands];
            for (int i = 0; i < ogDemmands; i++) {
                demmand[i] = Integer.parseInt(reader.readLine().trim());
            }

            int[][] cost = new int[ogOffers][ogDemmands];
            for (int i = 0; i < ogOffers; i++) {
                String[] line = reader.readLine().trim().split("\\s+");
                for (int j = 0; j < ogDemmands; j++) {
                    cost[i][j] = Integer.parseInt(line[j]);
                }
            }
            reader.close();

            TransportData data = rebalance(offer, demmand, cost, ogOffers, ogDemmands);

            int[][] transport = new int[data.offer.length][data.demmand.length];
            int totalCost = solve(data.offer, data.demmand, data.cost,
                    transport);

            System.out.println("Total cost: " + totalCost);

            scanner.close();
        }

    }
}