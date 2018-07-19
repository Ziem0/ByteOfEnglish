package com.english.partThree.enums;

public enum CsvPath {

    VERBS_PATH("partThree/src/main/resources/verbs.csv", "insert into verb(eng,simple,perfect,pl) values(?,?,?,?);"),
    NOUNS_PATH("partThree/src/main/resources/nouns.csv","insert into noun(eng,pl) values(?,?);"),
    ADJECTIVES_PATH("partThree/src/main/resources/adjectives.csv","insert into adjective(eng,pl) values(?,?);"),
    IDIOMS_PATH("partThree/src/main/resources/idioms.csv","insert into idiom(eng,explain,example,pl) values(?,?,?,?);"),
    PHRASAL_VERBS_PATH("partThree/src/main/resources/phrasalVerbs.csv","insert into phrasal(eng,explain,example,pl) values(?,?,?,?);");

    private String path;
    private String sqlStatement;

    CsvPath(String path, String sqlStatement) {
        this.path = path;
        this.sqlStatement = sqlStatement;
    }

    public String getPath() {
        return path;
    }

    public String getSqlStatement() {
        return sqlStatement;
    }
}
