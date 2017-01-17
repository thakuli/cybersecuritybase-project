/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.domain;

import java.util.List;

/**
 *
 * @author th567
 */
public class Friend {
    private String username;
    private List<String> statuses;

    public Friend() {
        
    }
    
    public Friend(String fuser, List<String> statuses) {
        this.username = fuser;
        this.statuses = statuses;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the statuses
     */
    public List<String> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses the statuses to set
     */
    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }
    
}
