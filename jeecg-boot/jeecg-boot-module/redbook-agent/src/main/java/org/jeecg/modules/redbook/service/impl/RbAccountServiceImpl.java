package org.jeecg.modules.redbook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.redbook.entity.RbAccount;
import org.jeecg.modules.redbook.mapper.RbAccountMapper;
import org.jeecg.modules.redbook.service.IRbAccountService;
import org.springframework.stereotype.Service;

@Service
public class RbAccountServiceImpl extends ServiceImpl<RbAccountMapper, RbAccount> implements IRbAccountService {
}
