package com.rjpa.service.impl;

import com.rjpa.repository.Entity.SysDictcodeEntity;
import com.rjpa.repository.SysDictCodeRepository;
import com.rjpa.service.ISysDictcodeService;
import com.rjpa.vo.SysDictV;
import model.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysDictcodeServiceImpl implements ISysDictcodeService {
    @Override
    public Result getSysDictcodes() {
        return null;
    }

    @Override
    public SysDictV getSysDictcodeByDId(int did) {
        SysDictV v = new SysDictV();
        SysDictcodeEntity dbData = sysDictCodeRepository.findByDid(did);
        if (null != dbData) {
            BeanUtils.copyProperties(dbData, v);
        }
        return v;
    }

    @Override
    public SysDictV checkSysDictcodeExistByDId(int did) {
        SysDictV v = new SysDictV();
        SysDictcodeEntity dbData = sysDictCodeRepository.findByDid(did);
        if (null != dbData) {
            BeanUtils.copyProperties(dbData, v);
        }
        return v;
    }

    @Override
    public boolean checkSysDictcodeExist(String dcolumn) {
        List<SysDictcodeEntity> dbData = sysDictCodeRepository.findByDcolumn(dcolumn);
        if (null != dbData) {
            return true;
        }
        return false;
    }

    @Override
    public Result addSysDictcode(SysDictV sysDictV) {
        SysDictcodeEntity sysDictcode = new SysDictcodeEntity();
        BeanUtils.copyProperties(sysDictV, sysDictcode);
        sysDictCodeRepository.save(sysDictcode);
        Result r = Result.ok(sysDictcode);
        return r;
    }

    @Override
    public Result editSysDictcode(SysDictV sysDictV) {
        SysDictcodeEntity dbData = (SysDictcodeEntity) sysDictCodeRepository.findByDid(sysDictV.getDid());
        SysDictcodeEntity sysDictcode = new SysDictcodeEntity();
        BeanUtils.copyProperties(sysDictV, sysDictcode);
        sysDictcode.setDid(dbData.getDid());
        sysDictCodeRepository.save(sysDictcode);
        Result r = Result.ok(sysDictcode);
        return r;
    }

    @Override
    public Result delSysDictcode(int did) {
        SysDictcodeEntity dbData = (SysDictcodeEntity) sysDictCodeRepository.findByDid(did);
        sysDictCodeRepository.deleteById(did);
        return Result.ok(dbData);
    }

    @Override
    public Result getSubSysDictcodesByDpresent(String dpresent) {
        List<SysDictV> vl = new ArrayList<SysDictV>();
        List<SysDictcodeEntity> sysDictcodes = sysDictCodeRepository.findByDpresent(dpresent);
        for (SysDictcodeEntity sysDictcode : sysDictcodes) {
            SysDictV v = new SysDictV();
            BeanUtils.copyProperties(sysDictcode, v);
            vl.add(v);
        }
        return Result.ok(vl);
    }

    @Override
    public Result getSysDictcodeGroupByDpresent(String dpresent) {
        return Result.ok(sysDictCodeRepository.selectGroupByDpresent(dpresent));
    }

    @Autowired
    SysDictCodeRepository sysDictCodeRepository;
}
