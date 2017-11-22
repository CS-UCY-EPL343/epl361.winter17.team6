CREATE TABLE Conversation (
    username TEXT NOT NULL,
    conv_id INTEGER NOT NULL,
    duration REAL,
    status INTEGER NOT NULL,
    PRIMARY KEY (username, conv_id),
    FOREIGN KEY (username) REFERENCES CUser(username)
);
