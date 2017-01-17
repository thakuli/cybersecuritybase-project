/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  th567
 * Created: 14-Jan-2017
 */

-- DROP TABLE UserAccount;
-- DROP TABLE Status;
-- DROP TABLE Friends;

CREATE TABLE IF NOT EXISTS UserAccount (
    id varchar(9) PRIMARY KEY auto_increment,
    username varchar(50),
    password varchar(50)
);

CREATE TABLE IF NOT EXISTS Status (
    id varchar(9) PRIMARY KEY auto_increment,
    message varchar(200),
    userid varchar(50)
    -- FOREIGN KEY(username) REFERENCES UserAccount(username)
);

CREATE TABLE IF NOT EXISTS Friends (
    a_id varchar(9),
    b_id varchar(9)
    -- FOREIGN KEY(username) REFERENCES UserAccount(username)
);
