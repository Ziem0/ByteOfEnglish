create table if not exists tense(
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	NAME TXT NOT NULL UNIQUE,
	USED INTEGER DEFAULT 0
);
