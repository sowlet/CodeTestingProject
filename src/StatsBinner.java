/**
 * Non-UI stats binning logic.
 * Preserves the existing behavior, including any off-by-one quirks.
 */
public class StatsBinner {

    private StatsBinner() {
        // utility class
    }

    public static int[] calculateBinCounts(GameStats stats, int[] binEdges) {
        int[] binCounts = new int[binEdges.length];

        for(int binIndex=0; binIndex<binEdges.length; binIndex++){
            final int lowerBound = binEdges[binIndex];
            int numGames = 0;

            if(binIndex == binEdges.length-1){
                // last bin: sum from lowerBound on up
                // Note: preserves original "< maxNumGuesses()" behavior.
                for(int numGuesses=lowerBound; numGuesses<stats.maxNumGuesses(); numGuesses++){
                    numGames += stats.numGames(numGuesses);
                }
            }
            else{
                // Note: preserves original use of next edge as upper bound.
                int upperBound = binEdges[binIndex+1];
                for(int numGuesses=lowerBound; numGuesses <= upperBound; numGuesses++) {
                    numGames += stats.numGames(numGuesses);
                }
            }

            binCounts[binIndex] = numGames;
        }

        return binCounts;
    }
}
