package assignment;

import java.util.UUID;
/**
 * Task group.
 *
 * @param groupUUID Unique group identifier.
 */
public record TaskGroup(UUID groupUUID) {
    public TaskGroup {
        if (groupUUID == null) {
            throw new IllegalArgumentException("Group UUID must not be null");
        }
    }



}

