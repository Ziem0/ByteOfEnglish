create table if not exists words(id INTEGER PRIMARY KEY AUTOINCREMENT, eng TXT, pl TXT, statusID INTEGER DEFAULT 1, date TXT, hour TXT, repeated INTEGER DEFAULT 0, FOREIGN KEY(statusID) references status(id) on DELETE CASCADE);