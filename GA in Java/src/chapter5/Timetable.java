package chapter5;

import java.util.HashMap;

/**
 * Timetable is the main evaluation class for the class scheduler GA.
 * 
 * A timetable represents a potential solution in human-readable form, unlike an
 * Individual or a chromosome. This timetable class, then, can read a chromosome
 * and develop a timetable from it, and ultimately can evaluate the timetable
 * for its fitness and number of scheduling clashes.
 * 
 * The most important methods in this class are createClasses and calcClashes.
 * 
 * The createClasses method accepts an Individual (really, a chromosome),
 * unpacks its chromosome, and creates Class objects from the genetic
 * information. Class objects are lightweight; they're just containers for
 * information with getters and setters, but it's more convenient to work with
 * them than with the chromosome directly.
 * 
 * The calcClashes method is used by GeneticAlgorithm.calcFitness, and requires
 * that createClasses has been run first. calcClashes looks at the Class objects
 * created by createClasses, and figures out how many hard constraints have been
 * violated.
 * 
 */
public class Timetable {
	private final HashMap<Integer, Room> rooms;
	private final HashMap<Integer, Professor> professors;
	private final HashMap<Integer, Module> modules;
	private final HashMap<Integer, Group> groups;
	private final HashMap<Integer, Timeslot> timeslots;
	private Class classes[];

	private int numClasses = 0;

	/**
	 * Initialize new Timetable
	 */
	public Timetable() {
		this.rooms = new HashMap<Integer, Room>();
		this.professors = new HashMap<Integer, Professor>();
		this.modules = new HashMap<Integer, Module>();
		this.groups = new HashMap<Integer, Group>();
		this.timeslots = new HashMap<Integer, Timeslot>();
	}

	/**
	 * "Clone" a timetable. We use this before evaluating a timetable so we have
	 * a unique container for each set of classes created by "createClasses".
	 * Truthfully, that's not entirely necessary (no big deal if we wipe out and
	 * reuse the .classes property here), but Chapter 6 discusses
	 * multi-threading for fitness calculations, and in order to do that we need
	 * separate objects so that one thread doesn't step on another thread's
	 * toes. So this constructor isn't _entirely_ necessary for Chapter 5, but
	 * you'll see it in action in Chapter 6.
	 * 
	 * @param cloneable
	 */
	public Timetable(Timetable cloneable) {
		this.rooms = cloneable.getRooms();
		this.professors = cloneable.getProfessors();
		this.modules = cloneable.getModules();
		this.groups = cloneable.getGroups();
		this.timeslots = cloneable.getTimeslots();
	}

	private HashMap<Integer, Group> getGroups() {
		return this.groups;
	}

	private HashMap<Integer, Timeslot> getTimeslots() {
		return this.timeslots;
	}

	private HashMap<Integer, Module> getModules() {
		return this.modules;
	}

	private HashMap<Integer, Professor> getProfessors() {
		return this.professors;
	}

	/**
	 * Add new room
	 * 
	 * @param roomId
	 * @param roomName
	 * @param capacity
	 */
	public void addRoom(int roomId, String roomName, int capacity) {
		this.rooms.put(roomId, new Room(roomId, roomName, capacity));
	}

	/**
	 * Add new professor
	 * 
	 * @param professorId
	 * @param professorName
	 */
	public void addProfessor(int professorId, String professorName) {
		this.professors.put(professorId, new Professor(professorId, professorName));
	}

	/**
	 * Add new module
	 * 
	 * @param moduleId
	 * @param moduleCode
	 * @param module
	 * @param professorIds
	 */
	public void addModule(int moduleId, String moduleCode, String module, int professorIds[]) {
		this.modules.put(moduleId, new Module(moduleId, moduleCode, module, professorIds));
	}

	/**
	 * Add new group
	 * 
	 * @param groupId
	 * @param groupSize
	 * @param moduleIds
	 */
	public void addGroup(int groupId, int groupSize, int moduleIds[]) {
		this.groups.put(groupId, new Group(groupId, groupSize, moduleIds));
		this.numClasses = 0;
	}

	/**
	 * Add new timeslot
	 * 
	 * @param timeslotId
	 * @param timeslot
	 */
	public void addTimeslot(int timeslotId, String timeslot) {
		this.timeslots.put(timeslotId, new Timeslot(timeslotId, timeslot));
	}

	/**
	 * Create classes using individual's chromosome
	 * 
	 * One of the two important methods in this class; given a chromosome,
	 * unpack it and turn it into an array of Class (with a capital C) objects.
	 * These Class objects will later be evaluated by the calcClashes method,
	 * which will loop through the Classes and calculate the number of
	 * conflicting timeslots, rooms, professors, etc.
	 * 
	 * While this method is important, it's not really difficult or confusing.
	 * Just loop through the chromosome and create Class objects and store them.
	 * 
	 * @param individual
	 */
	public void createClasses(Individual individual) {
		// Init classes
		Class classes[] = new Class[this.getNumClasses()];

		// Get individual's chromosome
		int chromosome[] = individual.getChromosome();
		int chromosomePos = 0;
		int classIndex = 0;

		for (Group group : this.getGroupsAsArray()) {
			int moduleIds[] = group.getModuleIds();
			for (int moduleId : moduleIds) {
				classes[classIndex] = new Class(classIndex, group.getGroupId(), moduleId);

				// Add timeslot
				classes[classIndex].addTimeslot(chromosome[chromosomePos]);
				chromosomePos++;

				// Add room
				classes[classIndex].setRoomId(chromosome[chromosomePos]);
				chromosomePos++;

				// Add professor
				classes[classIndex].addProfessor(chromosome[chromosomePos]);
				chromosomePos++;

				classIndex++;
			}
		}

		this.classes = classes;
	}

	/**
	 * Get room from roomId
	 * 
	 * @param roomId
	 * @return room
	 */
	public Room getRoom(int roomId) {
		if (!this.rooms.containsKey(roomId)) {
			System.out.println("Rooms doesn't contain key " + roomId);
		}
		return (Room) this.rooms.get(roomId);
	}

	public HashMap<Integer, Room> getRooms() {
		return this.rooms;
	}

	/**
	 * Get random room
	 * 
	 * @return room
	 */
	public Room getRandomRoom() {
		Object[] roomsArray = this.rooms.values().toArray();
		Room room = (Room) roomsArray[(int) (roomsArray.length * Math.random())];
		return room;
	}

	/**
	 * Get professor from professorId
	 * 
	 * @param professorId
	 * @return professor
	 */
	public Professor getProfessor(int professorId) {
		return (Professor) this.professors.get(professorId);
	}

	/**
	 * Get module from moduleId
	 * 
	 * @param moduleId
	 * @return module
	 */
	public Module getModule(int moduleId) {
		return (Module) this.modules.get(moduleId);
	}

	/**
	 * Get moduleIds of student group
	 * 
	 * @param groupId
	 * @return moduleId array
	 */
	public int[] getGroupModules(int groupId) {
		Group group = (Group) this.groups.get(groupId);
		return group.getModuleIds();
	}

	/**
	 * Get group from groupId
	 * 
	 * @param groupId
	 * @return group
	 */
	public Group getGroup(int groupId) {
		return (Group) this.groups.get(groupId);
	}

	/**
	 * Get all student groups
	 * 
	 * @return array of groups
	 */
	public Group[] getGroupsAsArray() {
		return (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
	}

	/**
	 * Get timeslot by timeslotId
	 * 
	 * @param timeslotId
	 * @return timeslot
	 */
	public Timeslot getTimeslot(int timeslotId) {
		return (Timeslot) this.timeslots.get(timeslotId);
	}

	/**
	 * Get random timeslotId
	 * 
	 * @return timeslot
	 */
	public Timeslot getRandomTimeslot() {
		Object[] timeslotArray = this.timeslots.values().toArray();
		Timeslot timeslot = (Timeslot) timeslotArray[(int) (timeslotArray.length * Math.random())];
		return timeslot;
	}

	/**
	 * Get classes
	 * 
	 * @return classes
	 */
	public Class[] getClasses() {
		return this.classes;
	}

	/**
	 * Get number of classes that need scheduling
	 * 
	 * @return numClasses
	 */
	public int getNumClasses() {
		if (this.numClasses > 0) {
			return this.numClasses;
		}

		int numClasses = 0;
		Group groups[] = (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
		for (Group group : groups) {
			numClasses += group.getModuleIds().length;
		}
		this.numClasses = numClasses;

		return this.numClasses;
	}

	/**
	 * Calculate the number of clashes between Classes generated by a
	 * chromosome.
	 * 
	 * The most important method in this class; look at a candidate timetable
	 * and figure out how many constraints are violated.
	 * 
	 * Running this method requires that createClasses has been run first (in
	 * order to populate this.classes). The return value of this method is
	 * simply the number of constraint violations (conflicting professors,
	 * timeslots, or rooms), and that return value is used by the
	 * GeneticAlgorithm.calcFitness method.
	 * 
	 * There's nothing too difficult here either -- loop through this.classes,
	 * and check constraints against the rest of the this.classes.
	 * 
	 * The two inner `for` loops can be combined here as an optimization, but
	 * kept separate for clarity. For small values of this.classes.length it
	 * doesn't make a difference, but for larger values it certainly does.
	 * 
	 * @return numClashes
	 */
	public int calcClashes() {
		int clashes = 0;

		for (Class classA : this.classes) {
			// Check room capacity
			int roomCapacity = this.getRoom(classA.getRoomId()).getRoomCapacity();
			int groupSize = this.getGroup(classA.getGroupId()).getGroupSize();
			
			if (roomCapacity < groupSize) {
				clashes++;
			}

			// Check if room is taken
			for (Class classB : this.classes) {
				if (classA.getRoomId() == classB.getRoomId() && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}

			// Check if professor is available
			for (Class classB : this.classes) {
				if (classA.getProfessorId() == classB.getProfessorId() && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}
		}

		return clashes;
	}
}