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
    
//    private static ResultSet executeQuery(String sql) throws SQLException {
//        try (Connection con = DriverManager.getConnection(databaseAddress, "sa", "")) {
//            return con.createStatement().executeQuery(sql);
//        } catch (SQLException e) {
//            throw e;
//        }
//    }

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
        statuses.forEach(System.out::println);
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
        friendList.forEach((f) -> { System.out.println(f.getUsername()) ;});
        return friendList;
    }

    public static List<Friend> getFriendsByUser(String user) {
        return getFriendsHelper(user);
    }

    public static void addUserStatus(String user, String status) {
        try {
            // INSERT INTO Status (id, message, userid) VALUES (1, 'Good day to ride a bike', 'tero');
            String sql = "INSERT INTO STATUS (MESSAGE, USERID) VALUES ('"+status+"' , '"+user+"')";
            System.out.println("*****sql={}".format(sql));
            getConnection().createStatement().executeUpdate(sql);
            getConnection().commit();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
//    public static List<String> getStatusesByUser(String user) {
//        List<String> statuses = new ArrayList<>();
//        statuses.add("today is a good day");
//        statuses.add("cycling is good for your body");
//
//        return statuses;
//    }

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

    public static List<Friend> searchFriends(String user, String friend) {
        return getFriendsHelper(user).stream().filter(f -> f.getUsername().contains(friend)).collect(Collectors.toList());
    }
}
