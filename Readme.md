# Cybersecurity Project work 1 - Tero Hakuli

https://cybersecuritybase.github.io/project/ 

## Course project - Poor man's Facebook
### Application Description
Application is simple and very limited Facebook clone. Users can add statuses and they can read their
friend's statuses. They can also search other users and they can add them to their
friend list.

The following users are created by default (user / password):
tero / tampere
tuomas / oulu
kelly / caracas
lemmy / losangeles
henkka / seattle
robert / london
jimmy / london
john_paul / countryside


### How to start the application
The application is written in Java and it has been developed with NetBeans. Using this IDE
it can be easily started by just selecting "Run" from the menu. Then the application is started
to http://localhost:8080. The IntelliJ and Eclipse work in the same way.


### Known vulnerabilities
#### (1) A1 SQL injection
All the database requests are vulnerable for the SQL Injection attacks, because
they generate the SQL staments by concatinating the input values directly to the
sql string without validating the input.
For example, 
class DBAPI {
  private static List<String> getFriendsByUid(int uid) {
    String sql = "SELECT b_id from FRIENDS where a_id = '" + uid + "'";
}

**How to produce:**
1. Open the Login screen: http://localhost:8080
2. Type as username: myaccount' and foobar like '%
3. Verify from the application console that a sql syntax error exception is thrown

**Solution:**
Quick solution is to use PreparedStatements, but a better solution would be to 
use JPA and let the framework to handle SQL connections and SQL request executions.


#### (2) A2 Broken authentication and session management
All the passwords are plain text encoded in the database. There is no password policy, so any 
string is accepted as a password.

**How to produce:**
An attacker might be to fetch the passwords from the database by using the sql injection vulnerability.
1. The passwords are defined in the file cybersec-db-default-data.sql

**Solution: **
Encrypt the passwords in the identity provider by using using strong encryption libraries.


#### (3) A3 Cross-Site Scripting (XSS)
The application does to validate the user input at all and it passes some input values 
directly to the Thymeleaf framework to render html page. Luckily, the Thymeleaf
does renders the injected html input as texts, so this is not severe vulnerability
at this point. If the user values would later be rendered without validation, then
the injected html would be rendered as html code and it would enable xss attack.

**How to produce:**
1. Login to the application
2. Type <h1>headline</h1> to Status
3. The html is displayed to the screen. Thymeleaf does not render this as html, so this is not actually a vulnerability


#### (4) A7 Missing Function Level Access Control
An authenticated user can add friends to another users by generating a POST request
with victims username and a new friend.

**How to produce:**
1. Login to application as tero / tampere with browser 1
2. Login to application as kelly / caracas with browser 2
3. With browser 2 execute the following request: http://localhost:8080/addNewFriend?user=tero&new_friend=lemmy
4. Tero is now friends with lemmy. Open the main page with browser 1

**Solution:**
Do not add user's name to the POST request. 


#### (5) A6 Sensitive Data Exposure
Application is vulnerable for the SQL injection attacks and the passwords are plain text.
An attacker might be able to get the passwords using a well crafted SQL statement.

**How to produce:**
1. The passwords are defined in the file cybersec-db-default-data.sql


#### (6) A8 Cross-Site Request Forgery (CSRF)
The application developer has stupidly disabled the CSRF protection.

**How to produce:**
1. Login to the application as kelly / caracas
2. Go away from the site ( e.g. http://www.mooc.fi)
3. Open the url http://localhost:8080/addNewFriend?user=tero&new_friend=lemmy
4. The mooc.fi could have had the above malicious link and you may have accidentally clicked that one => CSRF attack 

**Solution:**
Enable the CSRF protection. The this line from SecurityConfiguration.java: http.csrf().disable();

