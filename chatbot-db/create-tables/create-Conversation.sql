CREATE TABLE IF NOT EXISTS Conversation (
  username TEXT    NOT NULL,
  conv_id  TEXT    NOT NULL,
  init_timestamp INTEGER NOT NULL,
  duration REAL,
  status   INTEGER NOT NULL,
  PRIMARY KEY (username, conv_id),
  FOREIGN KEY (username) REFERENCES CUser (username)
);
