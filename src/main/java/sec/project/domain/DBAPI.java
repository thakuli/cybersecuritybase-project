/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.domain;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author th567
 */
public class DBAPI {

    final static private String databaseAddress = "jdbc:h2:file:./database";
    private static Connection conn = null;


    private static Connection getConnection() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(databaseAddress, "sa", "");
        } 
        return conn; 
    }
    
    private static List<String> getFriendsByUid(int uid) {
        String sql = "SELECT b_id from FRIENDS where a_id = '" + uid + "'";

        List<String> uids = new ArrayList<>();
        try {
            ResultSet rs = getConnection().createStatement().executeQuery(sql);

            while (rs.next()) {
                uids.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<String> friendsList = new ArrayList<>();
        uids.forEach((fuid) -> { 
            friendsList.add(getNameByUserId(fuid)); } );
        return friendsList;
    }

    public static List<String> getStatusesByUsername(String user) {
        String sql = "SELECT message from STATUS where userid = '" + user + "'";

        List<String> statuses = new ArrayList<>();
        try {
            ResultSet rs = getConnection().createStatement().executeQuery(sql);

            while (rs.next()) {
                statuses.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statuses;
    }

    private static int getUidByUsername(String user) {
        String sql = "SELECT id from UserAccount where username = '" + user + "'";

        int userId = 0;
        try {
            ResultSet rs = getConnection().createStatement().executeQuery(sql);

            if (rs.next()) {
                userId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;

    }

    private static List<Friend> getFriendsHelper(String username) {
        List<Friend> friendList = new ArrayList<>();
        try {
            int myId = getUidByUsername(username);
            List<String> friendsIds = getFriendsByUid(myId);

            friendsIds.forEach((fuser) -> {
                friendList.add(new Friend(fuser, getStatusesByUsername(fuser)));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendList;
    }

    public static List<Friend> getFriendsByUser(String user) {
        return getFriendsHelper(user);
    }

    public static void addUserStatus(String user, String status) {
        try {
            String sql = "INSERT INTO STATUS (MESSAGE, USERID) VALUES ('"+status+"' , '"+user+"')";
            getConnection().createStatement().executeUpdate(sql);
            getConnection().commit();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private static String getNameByUserId(String uid) {
        String sql = "SELECT username from UserAccount where id = '" + uid + "'";

        String username = "";
        try {
            ResultSet rs = getConnection().createStatement().executeQuery(sql);

            if (rs.next()) {
                username = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;

    }        
    
    public static List<String> getUsersByName(String username) {
        String sql = "SELECT username FROM UserAccount WHERE username LIKE  '%" + username + "%'";
        List<String> users = new ArrayList<>();
        
        ResultSet rs = null;
        try (Statement stmt = getConnection().createStatement()) {
            if (stmt.execute(sql)) {
                rs = stmt.getResultSet();   
                while (rs.next()) {
                    users.add(rs.getString(1));
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return users;        
    }
    
    public static List<Friend> searchFriends(String user, String friend) {
        return getFriendsHelper(user).stream().filter(f -> f.getUsername().contains(friend)).collect(Collectors.toList());
    }

    public static void addNewFriend(String user, String new_friend) {
        try {
            int a_id = DBAPI.getUidByUsername(user);
            int b_id = DBAPI.getUidByUsername(new_friend);

            String sql = "INSERT INTO FRIENDS (a_id, b_id) VALUES ('"+a_id+"' , '"+b_id+"')";
            getConnection().createStatement().executeUpdate(sql);
            getConnection().commit();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
