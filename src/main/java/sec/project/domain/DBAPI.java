/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * @author th567
 */
public class DBAPI {

    final static private String databaseAddress = "jdbc:h2:file:./database";
    private static Connection conn = null;

    private static Friend createFriend(String user, List<String> statuses) {
        Friend f = new Friend();
        f.setUsername(user);
        f.setStatuses(statuses);

        return f;
    }

    private static Connection getConnection() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(databaseAddress, "sa", "");
        } 
        return conn; 
    }
    
    private static ResultSet executeQuery(String sql) throws SQLException {
        try (Connection con = DriverManager.getConnection(databaseAddress, "sa", "")) {
            return con.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw e;
        }
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

    private static List<String> getStatusesByUsername(String user) {
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
//        List<Friend> friends = new ArrayList<>();
//        friends.add(createFriend("kelly", Arrays.asList("hace frio", "fines es dificil")));
//        friends.add(createFriend("tuomas", Arrays.asList("Oulu on kaupunki", "espanja on vaikeaa")));
//        return friends;

        return getFriendsHelper(user);
    }

    public static List<String> getStatusesByUser(String user) {
        List<String> statuses = new ArrayList<>();
        statuses.add("today is a good day");
        statuses.add("cycling is good for your body");

        return statuses;
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
}
