package com.zh.zhpicturebackend.model.enums;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Author:zzh
 * @Date:2025/4/19 18:39
 * @Version:1.0
 * @Description:
 */
@SpringBootTest
class PictureReviewStatusEnumTest {
    private static final Map<Integer, PictureReviewStatusEnum> PICTURE_REVIEW_STATUS_ENUM_MAP =
            Arrays.stream(PictureReviewStatusEnum.values())
                    .collect(Collectors.toMap(PictureReviewStatusEnum::getValue, e -> e));
    @Test
    void test1(){

    }
}