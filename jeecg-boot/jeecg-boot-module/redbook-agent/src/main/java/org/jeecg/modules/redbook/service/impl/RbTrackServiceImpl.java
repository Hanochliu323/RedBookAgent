package org.jeecg.modules.redbook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.redbook.entity.RbTrack;
import org.jeecg.modules.redbook.mapper.RbTrackMapper;
import org.jeecg.modules.redbook.service.IRbTrackService;
import org.springframework.stereotype.Service;

@Service
public class RbTrackServiceImpl extends ServiceImpl<RbTrackMapper, RbTrack> implements IRbTrackService {
}
