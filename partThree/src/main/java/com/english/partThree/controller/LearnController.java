package com.english.partThree.controller;

import com.english.partThree.dao.SentenceDao;

public class LearnController {

    private SentenceDao dao;

    public LearnController(SentenceDao dao) {
        this.dao = SentenceDao.getDao();
    }


}
