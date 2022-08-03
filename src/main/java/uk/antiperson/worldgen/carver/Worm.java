package uk.antiperson.worldgen.carver;

import uk.antiperson.worldgen.CoordinatePair;

import java.util.concurrent.ThreadLocalRandom;

public class Worm {

    private CoordinatePair start;
    private CoordinatePair keyLastPos;
    private CoordinatePair currentPos;
    private CoordinatePair keyPos;
    private CoordinatePair end;
    private double reciprocalGradient;
    private int length;

    public void start(int x, int z) {
        start = new CoordinatePair((x * 16) + ThreadLocalRandom.current().nextInt(0, 15), (z * 16) + ThreadLocalRandom.current().nextInt(0,15));
        int x2 = ThreadLocalRandom.current().nextInt(100, 200);
        int z2 = ThreadLocalRandom.current().nextInt(400, 600);
        end = new CoordinatePair(x2, z2);
        currentPos = start;
        keyLastPos = start;
        keyPos = start;
        System.out.println(start.getX() + "," + start.getX() + "-" + end.getX() + "," + end.getZ());
        reciprocalGradient =  -1 / ((end.getZ() - start.getZ()) / (double) (end.getX() - start.getX()));
        length = (int) Math.sqrt((start.getX() - end.getX()) ^ 2 + (start.getZ() - end.getZ()) ^ 2);
    }

    public void step() {
        System.out.println("step:" + currentPos.getX() + "," + currentPos.getZ());
        if (keyPos.equals(currentPos)) {
            keyLastPos = keyPos;
        }
        double gradient = (double) (keyPos.getZ() - keyLastPos.getZ()) / (keyPos.getX() - keyLastPos.getX());
        System.out.println("gradient " + gradient);
        double zIntercept = -keyPos.getZ() + (keyPos.getX() * gradient);
        System.out.println("zintercept " + zIntercept);
        int x = currentPos.getX() + 1;
        int z = (int) (gradient * x + zIntercept);
        currentPos = new CoordinatePair(x, z);
    }

    public int getLength() {
        return length;
    }

    public CoordinatePair getCurrentPos() {
        return currentPos;
    }
}
