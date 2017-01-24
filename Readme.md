# Cybersecurity Project work 1 - Tero Hakuli

https://cybersecuritybase.github.io/project/ 

## Course project - Poor man's Facebook
### Application Description
Application is simple Facebook clone. Users can add statuses and they can read their
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


### Known vulnerabilities
#### A1 SQL injection
All the database requests are vulnerable for the SQL Injection attacks, because
they generate the SQL staments by concatinating the input values directly to the
sql string without validating the input.
For example, 
class DBAPI {
  private static List<String> getFriendsByUid(int uid) {
    String sql = "SELECT b_id from FRIENDS where a_id = '" + uid + "'";
}


**How to produce:**

**Solution:**
Quick solution is to use PreparedStatements, but a better solution would be to 
use JPA and let the framework to handle SQL connections and SQL request executions.

#### A2 Broken authentication and session management
All the passwords are plain text encoded in the databased.

**How to produce:**

**Solution: **
Encrypt the passwords in the identity provider by using using strong encryption libraries.

#### A3 Cross-Site Scripting (XSS)
The application does to validate the user input at all and it passes some input values 
directly to the Thymeleaf framework to render html page. Luckily, the Thymeleaf
does renders the injected html input as texts, so this is not severe vulnerability
at this point. If the user values would later be rendered without validation, then
the injected html would be rendered as html code and it would enable xss attack.

**How to produce:**

#### A4 Insecure Direct Object References
An authenticated user can add friends to another users by generating a POST request
with victims username and a new friend.

**How to produce:**

**Solution:**
Do not add user's name to the POST request. 

#### A6 Sensitive Data Exposure
Application is vulnerable for the SQL injection attacks and the passwords are plain text.
An attacker might be able to get the passwords using a well crafted SQL statement.

**How to produce:**


#### A8 Cross-Site Request Forgery (CSRF)
The application developer has stupidly disabled the CSRF protection.

**How to produce:**

**Solution:**
Enable the CSRF protection.


