package assign10;

/**
 * Times the sorting version of Dijkstra's algorithm (Assignment 7).
 *
 * @author ?
 * @version ?
 */
public class DijkstraSortingTiming extends DijkstraTiming {
	/**
	 * Creates the experiment.
	 * 
	 * @param min - smallest number of vertices
	 * @param count - number of problem sizes
	 * @param step - increase in vertices between sizes
	 * @param iterations - number of times to repeat each size
	 */
	public DijkstraSortingTiming(int min, int count, int step, int iterations) {
		super(min, count, step, iterations);
	}

	@Override
	protected void runComputation() {
		GraphUtility.shortestWeightedPathWithSorting(sources, destinations, weights, 0, last);
	}
}
