package chapter3;

/**
 * The main executive class for the Robot Controller problem in chapter 3.
 * 
 * We'll create a maze by hand, and feed it to the GeneticAlgorithm's
 * `evalPopulation` method, which is then responsible for scoring an abstract
 * robot with sensors against the maze.
 * 
 * @author bkanber
 *
 */
public class RobotController {

	/**
	 * Upper bound for the number of generations to run for. 200 generations is
	 * sufficient to find the path about 50% of the time, but for demonstration
	 * purposes we've set this high by default.
	 */
	public static int maxGenerations = 1000;

	public static void main(String[] args) {

		/**
		 * Initialize a maze. We'll write this by hand, because, y'know, this
		 * book isn't called "maze generation algorithms".
		 * 
		 * The 3s represent the correct route through the maze. 1s are walls
		 * that can't be navigated through, and 0s are valid positions, but not
		 * the correct route. You can follow the 3s visually to find the correct
		 * path through the maze.
		 * 
		 * If you've read the docblock for
		 * GeneticAlgorithm::isTerminationConditionMet, I mention that we don't
		 * know what a perfect solution looks like, so the only constraint we
		 * can give the algorithm is the number of generations. That's both true
		 * and untrue. In this case, because we made the maze by hand, we
		 * actually DO know the winning fitness: it's 29, or the number of 3s
		 * below! However, we can't use that as a termination condition; if this
		 * maze were procedurally generated we would not necessarily know that
		 * the magic number is 29.
		 * 
		 * As a reminder: 
		 * 0 = Empty 
		 * 1 = Wall 
		 * 2 = Starting position 
		 * 3 = Route 
		 * 4 = Goal position
		 */

		Maze maze = new Maze(new int[][] { 
			{ 0, 0, 0, 0, 1, 0, 1, 3, 2 }, 
			{ 1, 0, 1, 1, 1, 0, 1, 3, 1 },
			{ 1, 0, 0, 1, 3, 3, 3, 3, 1 }, 
			{ 3, 3, 3, 1, 3, 1, 1, 0, 1 }, 
			{ 3, 1, 3, 3, 3, 1, 1, 0, 0 },
			{ 3, 3, 1, 1, 1, 1, 0, 1, 1 }, 
			{ 1, 3, 0, 1, 3, 3, 3, 3, 3 }, 
			{ 0, 3, 1, 1, 3, 1, 0, 1, 3 },
			{ 1, 3, 3, 3, 3, 1, 1, 1, 4 } 
		});

		// Create genetic algorithm
		GeneticAlgorithm ga = new GeneticAlgorithm(200, 0.05, 0.9, 2, 10);
		Population population = ga.initPopulation(128);
		ga.evalPopulation(population, maze);
		// Keep track of current generation
		int generation = 1;
		// Start evolution loop
		while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
			// Print fittest individual from population
			Individual fittest = population.getFittest(0);
			System.out.println(
					"G" + generation + " Best solution (" + fittest.getFitness() + "): " + fittest.toString());

			// Apply crossover
			population = ga.crossoverPopulation(population);

			// Apply mutation
			population = ga.mutatePopulation(population);

			// Evaluate population
			ga.evalPopulation(population, maze);

			// Increment the current generation
			generation++;
		}

		System.out.println("Stopped after " + maxGenerations + " generations.");
		Individual fittest = population.getFittest(0);
		System.out.println("Best solution (" + fittest.getFitness() + "): " + fittest.toString());
		
	}
}
