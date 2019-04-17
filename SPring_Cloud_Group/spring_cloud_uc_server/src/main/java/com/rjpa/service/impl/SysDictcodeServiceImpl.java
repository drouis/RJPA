package com.rjpa.service.impl;

import com.rjpa.repository.SysDictCodeRepository;
import com.rjpa.service.ISysDictcodeService;
import com.rjpa.vo.SysDictV;
import model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDictcodeServiceImpl implements ISysDictcodeService {
    @Override
    public Result getSysDictcodes() {
        return null;
    }

    @Override
    public SysDictV getSysDictcodeByDId(int did) {
        return null;
    }

    @Override
    public SysDictV checkSysDictcodeExistByDId(int mid) {
        return null;
    }

    @Override
    public Result addSysDictcode(SysDictV sysDictV) {
        return null;
    }

    @Override
    public Result editSysDictcode(SysDictV sysDictV) {
        return null;
    }

    @Override
    public Result delSysDictcode(int did) {
        return null;
    }

    @Override
    public Result getSubSysDictcodesByDpresent(String dpresent) {
        return null;
    }

    @Override
    public Result getSysDictcodeGroupByDpresent(String dpresent) {
        return Result.ok(sysDictCodeRepository.selectGroupByDpresent(dpresent));
    }

    @Autowired
    SysDictCodeRepository sysDictCodeRepository;
}
