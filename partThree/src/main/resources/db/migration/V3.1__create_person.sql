create table if not exists person(
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	ENG TXT NOT NULL UNIQUE,
	PL TXT NOT NULL UNIQUE,
	USED INTEGER NOT NULL DEFAULT 0
);
