/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  th567
 * Created: 14-Jan-2017
 */

CREATE TABLE IF NOT EXISTS UserAccount (
    id varchar(9) PRIMARY KEY,
    username varchar(200),
    password varchar(200)
);

CREATE TABLE IF NOT EXISTS Status (
    id varchar(9) PRIMARY KEY,
    message varchar(200),
    creationTime TIMESTAMP,
    username varchar(200)
    -- FOREIGN KEY(username) REFERENCES UserAccount(username)
);