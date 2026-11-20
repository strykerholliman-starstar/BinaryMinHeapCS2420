package assign10;

import java.util.ArrayList;
import java.util.Random;

import timing.TimingExperiment;

/**
 * Base class for the Dijkstra timing experiments. Each experiment builds its own
 * sparse, connected graph with V vertices and about 4V edges (the same graph for
 * the same V, because the random seed is V). Running main times both versions
 * of the algorithm over the same problem sizes.
 *
 * @author ?
 * @version ?
 */
public abstract class DijkstraTiming extends TimingExperiment {
	protected ArrayList<Integer> sources, destinations;
	protected ArrayList<Double> weights;
	protected int last;

	/**
	 * Creates the experiment.
	 * 
	 * @param min - smallest number of vertices
	 * @param count - number of problem sizes
	 * @param step - increase in vertices between sizes
	 * @param iterations - number of times to repeat each size (the median is reported)
	 */
	public DijkstraTiming(int min, int count, int step, int iterations) {
		super("number of vertices (V)", min, count, step, iterations);
	}

	@Override
	protected void setupExperiment(int problemSize) {
		Random rng = new Random(problemSize);
		sources = new ArrayList<Integer>();
		destinations = new ArrayList<Integer>();
		weights = new ArrayList<Double>();
		last = problemSize - 1;
		// a chain guarantees a path from vertex 0 to the last vertex
		for(int i = 0; i < last; i++)
			addEdge(i, i + 1, 1 + rng.nextInt(100));
		// about 3 extra random edges per vertex keeps the graph sparse
		for(int i = 0; i < 3 * problemSize; i++)
			addEdge(rng.nextInt(problemSize), rng.nextInt(problemSize), 1 + rng.nextInt(100));
	}

	private void addEdge(int src, int dst, double weight) {
		sources.add(src);
		destinations.add(dst);
		weights.add(weight);
	}

	/**
	 * Runs both Dijkstra experiments, one after the other, over the same sizes.
	 * 
	 * @param args - unused
	 */
	public static void main(String[] args) {
		int min = 1000, count = 20, step = 1000, iterations = 10;
		System.out.println("Dijkstra with priority queue (binary heap)");
		new DijkstraPQTiming(min, count, step, iterations).printResults();
		System.out.println();
		System.out.println("Dijkstra with sorting");
		new DijkstraSortingTiming(min, count, step, iterations).printResults();
	}
}