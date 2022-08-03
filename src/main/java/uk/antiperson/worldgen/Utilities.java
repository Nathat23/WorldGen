package uk.antiperson.worldgen;

public class Utilities {

    public static int[] toChunkCoords(int x, int y) {
        return new int[]{x % 16, y % 16};
    }

}
