CREATE TABLE Message (
    user_id INTEGER NOT NULL,
    conv_id INTEGER NOT NULL,
    msg_id INTEGER NOT NULL, 
    time_stamp REAL NOT NULL,
    isReq INTEGER NOT NULL,
    PRIMARY KEY(user_id, conv_id, msg_id),
    FOREIGN KEY(user_id, conv_id) REFERENCES Conversation(user_id, conv_id)
);
