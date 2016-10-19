package chapter4;

/**
 * Main, executive class for the Traveling Salesman Problem.
 * 
 * We don't have a real list of cities, so we randomly generate a number of them
 * on a 100x100 map.
 * 
 * The TSP requires that each city is visited once and only once, so we have to
 * be careful when initializing a random Individual and also when applying
 * crossover and mutation. Check out the GeneticAlgorithm class for
 * implementations of crossover and mutation for this problem.
 * 
 * @author bkanber
 *
 */
public class TSP {
	public static int maxGenerations = 10000;
	public static void main(String[] args) {
		
		// Create cities
		int numCities = 100;
		City cities[] = new City[numCities];
		
		// Loop to create random cities
		for (int cityIndex = 0; cityIndex < numCities; cityIndex++) {
			// Generate x,y position
			int xPos = (int) (100 * Math.random());
			int yPos = (int) (100 * Math.random());
			
			// Add city
			cities[cityIndex] = new City(xPos, yPos);
		}

		// Initial GA
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.9, 2, 5);

		// Initialize population
		Population population = ga.initPopulation(cities.length);

		// Evaluate population
		ga.evalPopulation(population, cities);

		Route startRoute = new Route(population.getFittest(0), cities);
		System.out.println("Start Distance: " + startRoute.getDistance());

		// Keep track of current generation
		int generation = 1;
		// Start evolution loop
		while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
			// Print fittest individual from population
			Route route = new Route(population.getFittest(0), cities);
			System.out.println("G"+generation+" Best distance: " + route.getDistance());

			// Apply crossover
			population = ga.crossoverPopulation(population);

			// Apply mutation
			population = ga.mutatePopulation(population);

			// Evaluate population
			ga.evalPopulation(population, cities);

			// Increment the current generation
			generation++;
		}
		
		System.out.println("Stopped after " + maxGenerations + " generations.");
		Route route = new Route(population.getFittest(0), cities);
		System.out.println("Best distance: " + route.getDistance());

	}
}
