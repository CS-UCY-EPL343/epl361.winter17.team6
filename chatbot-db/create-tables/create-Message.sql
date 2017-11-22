CREATE TABLE IF NOT EXISTS Message (
  username   TEXT    NOT NULL,
  conv_id    TEXT    NOT NULL,
  msg_id     TEXT    NOT NULL,
  time_stamp INTEGER    NOT NULL,
  is_user_msg INTEGER NOT NULL,
  content TEXT NOT NULL,
  PRIMARY KEY (username, conv_id, msg_id),
  FOREIGN KEY (username, conv_id) REFERENCES Conversation (username, conv_id)
);
