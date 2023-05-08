import java.util.*;

public class LotteryPicker {

    //TODO: Declare the fields in a universal scope.

    /**
     * Takes the input of lottery teams and their respective odds and populates a hashmap.
     * @param teams An array of all lottery teams for the current year's NBA draft.
     * @param odds The current probability of each team's chances of landing the #1 overall pick.  Note:  this needs to have the same index as the teams String array.
     * @return A hashmap of teams and their lottery chances to get the #1 overall pick.
     */
    public static HashMap<String, Double> populateMap (String[] teams, Double[] odds) {
        HashMap<String, Double> lotteryTeamsAndOdds = new HashMap<>();
        for (int i = 0; i < teams.length; i++) {
            lotteryTeamsAndOdds.put(teams[i], odds[i] * 10);
        }
        return lotteryTeamsAndOdds;
    }

    /**
     * Populates the pool of 1000 "lottery balls" based on each team's probabilities.
     * @param teams An array of all lottery teams for the current year's NBA draft.
     * @param draftMap A hashmap of teams and their lottery chances to get the #1 overall pick.
     * @return A lottery pool of 1000 entries populated with each team's "lottery balls" according to their probabilities.
     */

    public static String[] populateLotteryBalls(String[] teams, HashMap<String, Double> draftMap) {
        int lotteryBalls = 1000;
        String[] lotteryPool = new String[1000];
        int i = 0;
        int j = 0;
        while (lotteryBalls > 0) {
            Double currentTeamsBalls = draftMap.get(teams[j]);
                while (currentTeamsBalls > 0) {
                    lotteryPool[i] = teams[j];
                    currentTeamsBalls--;
                    lotteryBalls--;
                    i++;
                }
                j++;
        }
        return lotteryPool;
    }

    /**
     * Generates the NBA lottery winners and draft order.
     * @param lotteryBalls The lottery pool of 1000 entries based on lottery teams and their probabilities.
     * @param lotteryTeams The NBA lottery teams.
     * @return The NBA draft order results.
     */

    public static String[] generateDraftOrder(String[] lotteryBalls, String[] lotteryTeams) {

        String[] order = new String[14];
        List<String> alreadyDrafted = new ArrayList<>();
        int topFour = 4;
        int upperBound = 1000;
        int index = 0;
        Random random = new Random();

        while (topFour > 0) {
            int randomLotteryBall = random.nextInt(upperBound);
            String selectedTeam = lotteryBalls[randomLotteryBall];
            if (!alreadyDrafted.contains(selectedTeam)) {
                order[index] = selectedTeam;
                alreadyDrafted.add(selectedTeam);
                topFour--;
                index++;
            }
        }
        LinkedList<String> topFourRemoved = new LinkedList<>();
        for (String s : lotteryTeams) {
            if (!alreadyDrafted.contains(s)) {
                topFourRemoved.add(s);
            }
        }
        int j = 4;
        for (String s : topFourRemoved) {
            order[j] = s;
            j++;
        }
        return order;
    }

    /**
     * Displays the generated NBA draft order results.
     * @param draftOrder The generated NBA draft order.
     */

    public static void displayDraftOrder(String[] draftOrder) {
        for (int i = 0; i < draftOrder.length; i++) {
            int pick = i + 1;
            System.out.println("Pick " + pick + ": " + draftOrder[i]);
        }
    }

    /**
     * Counts the total number of times the given NBA team wins the 1st overall pick based on the supplied simulation number.
     * @param simulations The amount of simulations to run.
     * @param initialSimulations The initial amount of simulations to run.
     * @param totalFirstPicks The total count of 1st picks the given team won out of the simulations.
     * @param team The NBA lottery team to run the simulations on.
     * @return The number of times the given NBA team won the 1st pick.
     */

    public static int countFirstPicks(int simulations, int initialSimulations, int totalFirstPicks, String team) {
        String[] lotteryTeams = {"DETROIT", "HOUSTON", "SAN ANTONIO", "CHARLOTTE", "PORTLAND", "ORLANDO", "INDIANA", "WASHINGTON", "UTAH", "DALLAS", "CHICAGO", "OKLAHOMA CITY", "TORONTO", "NEW ORLEANS"};
        Double[] teamProbabilities = {14.0, 14.0, 14.0, 12.5, 10.5, 9.0, 6.8, 6.7, 4.5, 3.0, 1.8, 1.7, 1.0, 0.5};
        HashMap<String, Double> map = populateMap(lotteryTeams, teamProbabilities);
        String[] result = generateDraftOrder(populateLotteryBalls(lotteryTeams, map), lotteryTeams);
        if (simulations > 0) {
            simulations--;
            if (Objects.equals(result[0], team)) {
                totalFirstPicks++;
            }
            return countFirstPicks(simulations, initialSimulations, totalFirstPicks, team);
        }
        System.out.println(team + " won the first pick " + totalFirstPicks + " times out of " + initialSimulations + " simulations.");
        return totalFirstPicks;
    }



    public static void main(String[] args) {

        String[] lotteryTeams = {"DETROIT", "HOUSTON", "SAN ANTONIO", "CHARLOTTE", "PORTLAND", "ORLANDO", "INDIANA", "WASHINGTON", "UTAH", "DALLAS", "CHICAGO", "OKLAHOMA CITY", "TORONTO", "NEW ORLEANS"};
        Double[] teamProbabilities = {14.0, 14.0, 14.0, 12.5, 10.5, 9.0, 6.8, 6.7, 4.5, 3.0, 1.8, 1.7, 1.0, 0.5};
        populateMap(lotteryTeams, teamProbabilities);
        displayDraftOrder(generateDraftOrder(populateLotteryBalls(lotteryTeams, populateMap(lotteryTeams, teamProbabilities)), lotteryTeams));
        countFirstPicks(1000, 1000, 0,  "UTAH");
    }
}
