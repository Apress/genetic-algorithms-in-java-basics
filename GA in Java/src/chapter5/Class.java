package chapter5;

/**
 * A simple class abstraction -- basically a container for class, group, module, professor, timeslot, and room IDs
 */
public class Class {
    private final int classId;
    private final int groupId;
    private final int moduleId;
    private int professorId;
    private int timeslotId;
    private int roomId;
    
    /**
     * Initialize new Class
     * 
     * @param classId
     * @param groupId
     * @param moduleId
     */
    public Class(int classId, int groupId, int moduleId){
        this.classId = classId;
        this.moduleId = moduleId;
        this.groupId = groupId;
    }
    
    /**
     * Add professor to class
     * 
     * @param professorId
     */
    public void addProfessor(int professorId){
        this.professorId = professorId;
    }
    
    /**
     * Add timeslot to class
     * 
     * @param timeslotId
     */
    public void addTimeslot(int timeslotId){
        this.timeslotId = timeslotId;
    }    
    
    /**
     * Add room to class
     * 
     * @param roomId
     */
    public void setRoomId(int roomId){
        this.roomId = roomId;
    }
    
    /**
     * Get classId
     * 
     * @return classId
     */
    public int getClassId(){
        return this.classId;
    }
    
    /**
     * Get groupId
     * 
     * @return groupId
     */
    public int getGroupId(){
        return this.groupId;
    }
    
    /**
     * Get moduleId
     * 
     * @return moduleId
     */
    public int getModuleId(){
        return this.moduleId;
    }
    
    /**
     * Get professorId
     * 
     * @return professorId
     */
    public int getProfessorId(){
        return this.professorId;
    }
    
    /**
     * Get timeslotId
     * 
     * @return timeslotId
     */
    public int getTimeslotId(){
        return this.timeslotId;
    }
    
    /**
     * Get roomId
     * 
     * @return roomId
     */
    public int getRoomId(){
        return this.roomId;
    }
}

