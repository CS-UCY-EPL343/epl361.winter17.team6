CREATE TABLE CUser (
    username TEXT PRIMARY KEY NOT NULL 
);

CREATE TABLE Token (
    token_code TEXT PRIMARY KEY NOT NULL,
    username TEXT NOT NULL,
    FOREIGN KEY (username) REFERENCES CUser(username)
);

CREATE TABLE Conversation (
    username TEXT NOT NULL,
    conv_id INTEGER NOT NULL
    PRIMARY KEY (username, conv_id),
    FOREIGN KEY (username) REFERENCES CUser(username),
    duration REAL,
    status INTEGER NOT NULL
);

CREATE TABLE Message (
    user_id INTEGER NOT NULL,
    conv_id INTEGER NOT NULL,
    msg_id INTEGER NOT NULL,
    PRIMARY KEY(user_id, conv_id, msg_id),
    FOREIGN KEY(user_id, conv_id) REFERENCES Conversation(user_id, conv_id), 
    time_stamp REAL NOT NULL,
    isReq INTEGER NOT NULL
);