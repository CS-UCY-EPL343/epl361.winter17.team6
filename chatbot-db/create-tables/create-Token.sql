CREATE TABLE IF NOT EXISTS Token (
  token_code TEXT PRIMARY KEY NOT NULL,
  username   TEXT             NOT NULL,
  FOREIGN KEY (username) REFERENCES CUser (username)
);
