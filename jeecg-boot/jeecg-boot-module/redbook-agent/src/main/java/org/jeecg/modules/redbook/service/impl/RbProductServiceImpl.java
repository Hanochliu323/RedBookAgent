package org.jeecg.modules.redbook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.redbook.entity.RbProduct;
import org.jeecg.modules.redbook.mapper.RbProductMapper;
import org.jeecg.modules.redbook.service.IRbProductService;
import org.springframework.stereotype.Service;

@Service
public class RbProductServiceImpl extends ServiceImpl<RbProductMapper, RbProduct> implements IRbProductService {
}
