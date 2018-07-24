create table if not exists adjective(
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	ENG TXT NOT NULL,
	PL TXT NOT NULL,
	USED INTEGER DEFAULT 0,
	DATE TXT DEFAULT '2018-08-08',
        HOUR TXT DEFAULT '12-12-12',
	FAVORITE INTEGER DEFAULT 0
);
