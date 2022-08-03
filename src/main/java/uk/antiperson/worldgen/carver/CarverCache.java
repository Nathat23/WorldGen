package uk.antiperson.worldgen.carver;

import uk.antiperson.worldgen.CoordinatePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CarverCache {

    private final Map<CoordinatePair, List<CoordinatePair>> points;
    private final Carver carver;

    public CarverCache(Carver carver) {
        this.points = new HashMap();
        this.carver = carver;
        /*for (int x = -10; x < 10; x++) {
            for (int z = -10; z < 10; z++) {
                generatePoints(x, z);
            }
        }*/
    }

    public void generatePoints(int x, int z) {
        if (ThreadLocalRandom.current().nextDouble() > 0.01) {
            return;
        }
        Worm worm = new Worm();
        worm.start(x, z);
        List<CoordinatePair> pairs = new ArrayList<>();
        for (int i = 0; i < worm.getLength(); i++) {
            CoordinatePair coordinatePair = worm.getCurrentPos();
            pairs.add(coordinatePair);
            worm.step();
        }
        for (CoordinatePair pair : pairs) {
            CoordinatePair chunk = new CoordinatePair(pair.getX() / 16, pair.getZ() / 16);
            List<CoordinatePair> set = points.containsKey(chunk) ? points.get(chunk) : new ArrayList<>();
            set.add(pair);
            points.put(chunk, set);
        }
    }

    public List<CoordinatePair> getPoints(int chunkX, int chunkZ) {
        if (!points.containsKey(new CoordinatePair(chunkX, chunkZ))) {
            generatePoints(chunkX, chunkZ);
        }
        return points.get(new CoordinatePair(chunkX, chunkZ));
    }


}
