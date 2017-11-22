CREATE TABLE Message (
  username   TEXT    NOT NULL,
  conv_id    TEXT    NOT NULL,
  msg_id     TEXT    NOT NULL,
  time_stamp INTEGER    NOT NULL,
  isReq      INTEGER NOT NULL,
  PRIMARY KEY (username, conv_id, msg_id),
  FOREIGN KEY (username, conv_id) REFERENCES Conversation (username, conv_id)
);
