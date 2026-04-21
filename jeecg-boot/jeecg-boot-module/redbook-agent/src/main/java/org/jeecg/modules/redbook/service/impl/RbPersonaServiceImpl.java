package org.jeecg.modules.redbook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.redbook.entity.RbPersona;
import org.jeecg.modules.redbook.mapper.RbPersonaMapper;
import org.jeecg.modules.redbook.service.IRbPersonaService;
import org.springframework.stereotype.Service;

@Service
public class RbPersonaServiceImpl extends ServiceImpl<RbPersonaMapper, RbPersona> implements IRbPersonaService {
}
