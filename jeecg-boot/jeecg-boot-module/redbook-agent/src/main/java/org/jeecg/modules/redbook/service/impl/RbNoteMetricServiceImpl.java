package org.jeecg.modules.redbook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.redbook.entity.RbNoteMetric;
import org.jeecg.modules.redbook.mapper.RbNoteMetricMapper;
import org.jeecg.modules.redbook.service.IRbNoteMetricService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
public class RbNoteMetricServiceImpl extends ServiceImpl<RbNoteMetricMapper, RbNoteMetric> implements IRbNoteMetricService {
    @Override
    public RbNoteMetric normalizeMetric(RbNoteMetric entity) {
        long views = defaultLong(entity.getViews());
        long likes = defaultLong(entity.getLikes());
        long collects = defaultLong(entity.getCollects());
        long comments = defaultLong(entity.getComments());
        long shares = defaultLong(entity.getShares());

        if (entity.getCollectTime() == null) {
            entity.setCollectTime(new Date());
        }

        if (views <= 0) {
            entity.setInteractionRate(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP));
            entity.setCollectRate(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP));
            entity.setCommentRate(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP));
            return entity;
        }

        BigDecimal base = BigDecimal.valueOf(views);
        entity.setInteractionRate(BigDecimal.valueOf(likes + collects + comments + shares).divide(base, 4, RoundingMode.HALF_UP));
        entity.setCollectRate(BigDecimal.valueOf(collects).divide(base, 4, RoundingMode.HALF_UP));
        entity.setCommentRate(BigDecimal.valueOf(comments).divide(base, 4, RoundingMode.HALF_UP));
        return entity;
    }

    private long defaultLong(Long value) {
        return value == null ? 0L : value;
    }
}
