package com.service.impl;

import com.dao.UserVoMapper;
import com.domain.vo.UserVo;
import com.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceimpl implements IndexService {
    @Autowired
    private UserVoMapper userVoMapper;
    @Override
    public UserVo getinde(int id) {
        return userVoMapper.selectByPrimaryKey(id);
    }
}
