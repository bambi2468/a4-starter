public class Midway {

    public static long stepsRemaining(int[] diskPos) {
        long steps = 0;
        int targetPeg = 1; // The final goal for all disks is Peg 1

        // Iterate from the largest disk down to the smallest
        for (int i = diskPos.length - 1; i >= 0; i--) {
            int currentPeg = diskPos[i];

            if (currentPeg != targetPeg) {
                // The largest disk needs to be moved to the target.
                // It will take 2^i steps to finish moving it and place the 
                // smaller disks on top of it later.
                steps += (1L << i); 
                
                // The smaller disks currently need to be moved to the auxiliary 
                // peg to make way for this disk.
                targetPeg = 3 - currentPeg - targetPeg;
            }
            // If currentPeg == targetPeg, we add 0 steps and the targetPeg 
            // for the next smallest disks remains the same.
        }

        return steps;
    }
}