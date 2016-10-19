package chapter5;

public class Individual {
	
	/**
	 * In this case, the chromosome is an array of integers rather than a string. 
	 */
	private int[] chromosome;
	private double fitness = -1;

	/**
	 * Initializes random individual based on a timetable
	 * 
	 * The Timetable class is a bit overloaded. It knows both fixed information
	 * (the courses that MUST be scheduled, the professors that MUST be given
	 * jobs, the classrooms that DO exist) -- but it also understands how to
	 * parse and unpack chromosomes which contain variable information (which
	 * professor teaches which class and when?)
	 * 
	 * In this case, we use the Timetable for the fixed information only, and
	 * generate a random chromosome, making guesses at the variable information.
	 * 
	 * Given the fixed information in a Timetable, we create a chromosome that
	 * randomly assigns timeslots, rooms, and professors to the chromosome for
	 * each student group and module.
	 * 
	 * @param timetable
	 *            The timetable information
	 */
	public Individual(Timetable timetable) {
		int numClasses = timetable.getNumClasses();

		// 1 gene for room, 1 for time, 1 for professor
		int chromosomeLength = numClasses * 3;
		// Create random individual
		int newChromosome[] = new int[chromosomeLength];
		int chromosomeIndex = 0;
		// Loop through groups
		for (Group group : timetable.getGroupsAsArray()) {
			// Loop through modules
			for (int moduleId : group.getModuleIds()) {
				// Add random time
				int timeslotId = timetable.getRandomTimeslot().getTimeslotId();
				newChromosome[chromosomeIndex] = timeslotId;
				chromosomeIndex++;

				// Add random room
				int roomId = timetable.getRandomRoom().getRoomId();
				newChromosome[chromosomeIndex] = roomId;
				chromosomeIndex++;

				// Add random professor
				Module module = timetable.getModule(moduleId);
				newChromosome[chromosomeIndex] = module.getRandomProfessorId();
				chromosomeIndex++;
			}
		}

		this.chromosome = newChromosome;
	}

	/**
	 * Initializes random individual
	 * 
	 * The book instructs you to copy this constructor over from Chapter 4. This
	 * case is a little tricky -- used in Chapter 4, this constructor will
	 * create a valid chromosome for a list of cities for the TSP, by using each
	 * city once and only once.
	 * 
	 * If used in Chapter 5, however, this will create an utterly INVALID
	 * chromosome for the class scheduler. So you should not use this
	 * constructor if you hope to create a valid random individual. For that
	 * purpose, use the Individual(Timetable) constructor, which will create a
	 * valid Individual from the fixed information in the Timetable object.
	 * 
	 * However, Chapter 5 still needs an Individual(int) constructor that
	 * creates an Individual with a chromosome of a given size. It's used in the
	 * crossoverPopulation method in order to initialize the offspring. The fact
	 * that this creates an invalid Individual doesn't matter in this case,
	 * because the crossover algorithm immediately rewrites the whole
	 * chromosome.
	 * 
	 * 
	 * @param chromosomeLength
	 *            The length of the individuals chromosome
	 */
	public Individual(int chromosomeLength) {
		// Create random individual
		int[] individual;
		individual = new int[chromosomeLength];
		
		/**
		 * This comment and the for loop doesn't make sense for this chapter.
		 * But I'm leaving it in here because you were instructed to copy this
		 * class from Chapter 4 -- and NOT having this comment here might be
		 * more confusing than keeping it in.
		 * 
		 * Comment from Chapter 4:
		 * 
		 * "In this case, we can no longer simply pick 0s and 1s -- we need to
		 * use every city index available. We also don't need to randomize or
		 * shuffle this chromosome, as crossover and mutation will ultimately
		 * take care of that for us."
		 */
		for (int gene = 0; gene < chromosomeLength; gene++) {
			individual[gene] = gene;
		}
		
		this.chromosome = individual;
	}
    
	/**
	 * Initializes individual with specific chromosome
	 * 
	 * @param chromosome
	 *            The chromosome to give individual
	 */
	public Individual(int[] chromosome) {
		// Create individual chromosome
		this.chromosome = chromosome;
	}

	/**
	 * Gets individual's chromosome
	 * 
	 * @return The individual's chromosome
	 */
	public int[] getChromosome() {
		return this.chromosome;
	}

	/**
	 * Gets individual's chromosome length
	 * 
	 * @return The individual's chromosome length
	 */
	public int getChromosomeLength() {
		return this.chromosome.length;
	}

	/**
	 * Set gene at offset
	 * 
	 * @param gene
	 * @param offset
	 */
	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}

	/**
	 * Get gene at offset
	 * 
	 * @param offset
	 * @return gene
	 */
	public int getGene(int offset) {
		return this.chromosome[offset];
	}

	/**
	 * Store individual's fitness
	 * 
	 * @param fitness
	 *            The individuals fitness
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * Gets individual's fitness
	 * 
	 * @return The individual's fitness
	 */
	public double getFitness() {
		return this.fitness;
	}
	
	public String toString() {
		String output = "";
		for (int gene = 0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene] + ",";
		}
		return output;
	}

	/**
	 * Search for a specific integer gene in this individual.
	 * 
	 * For instance, in a Traveling Salesman Problem where cities are encoded as
	 * integers with the range, say, 0-99, this method will check to see if the
	 * city "42" exists.
	 * 
	 * @param gene
	 * @return
	 */
	public boolean containsGene(int gene) {
		for (int i = 0; i < this.chromosome.length; i++) {
			if (this.chromosome[i] == gene) {
				return true;
			}
		}
		return false;
	}


	
}
