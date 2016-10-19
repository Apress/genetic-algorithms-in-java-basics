package chapter5;

/**
 * A simple "group-of-students" abstraction. Defines the modules that the group is enrolled in.
 *
 */
public class Group {
    private final int groupId;
    private final int groupSize;
    private final int moduleIds[];

    /**
     * Initialize Group
     * 
     * @param groupId
     * @param groupSize
     * @param moduleIds
     */
    public Group(int groupId, int groupSize, int moduleIds[]){
        this.groupId = groupId;
        this.groupSize = groupSize;
        this.moduleIds = moduleIds;
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
     * Get groupSize
     * 
     * @return groupSize
     */
    public int getGroupSize(){
        return this.groupSize;
    }
        
    /**
     * Get array of group's moduleIds
     * 
     * @return moduleIds
     */
    public int[] getModuleIds(){
        return this.moduleIds;
    }
}
