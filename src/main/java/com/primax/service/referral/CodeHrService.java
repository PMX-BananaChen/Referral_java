package com.primax.service.referral;

import com.primax.mapper.db1.CodeHrMapper;
import com.primax.model.CodeHr;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CodeHrService {

    @Resource
    private CodeHrMapper codeHrMapper;

    public CodeHr getCodeHr(String empNo) {

        CodeHr hr = codeHrMapper.getCodeHr(empNo);

        return hr;
    }

    public List<CodeHr> getCodeHrs(String empNo) {

        List<CodeHr> hrs = codeHrMapper.getCodeHrs(empNo);

        return hrs;
    }

    public List<CodeHr>  getCodeHrByBuId(Integer buId, String empType) {

        List<CodeHr> hrs = codeHrMapper.getCodeHrByBuId(buId,empType);

        return hrs;
    }
}
