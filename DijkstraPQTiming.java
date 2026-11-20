package assign10;

/**
 * Times the priority-queue version of Dijkstra's algorithm.
 *
 * @author ?
 * @version ?
 */
public class DijkstraPQTiming extends DijkstraTiming {
	/**
	 * Creates the experiment.
	 * 
	 * @param min - smallest number of vertices
	 * @param count - number of problem sizes
	 * @param step - increase in vertices between sizes
	 * @param iterations - number of times to repeat each size
	 */
	public DijkstraPQTiming(int min, int count, int step, int iterations) {
		super(min, count, step, iterations);
	}

	@Override
	protected void runComputation() {
		GraphUtility.shortestWeightedPathWithPriorityQueue(sources, destinations, weights, 0, last);
	}
}
