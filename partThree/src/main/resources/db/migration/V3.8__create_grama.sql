create table if not exists grama(
        ID INTEGER PRIMARY KEY AUTOINCREMENT,
        NAME TXT,
        USED INTEGER DEFAULT 0,
	DATE TXT DEFAULT '2018-08-08',
        HOUR TXT DEFAULT '12-12-12'
);
