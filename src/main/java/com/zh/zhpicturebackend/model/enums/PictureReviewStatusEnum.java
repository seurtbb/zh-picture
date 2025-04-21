package com.zh.zhpicturebackend.model.enums;


import com.zh.zhpicturebackend.exception.BusinessException;
import com.zh.zhpicturebackend.exception.ErrorCode;
import com.zh.zhpicturebackend.exception.ThrowUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: zzh
 */
@Getter
public enum PictureReviewStatusEnum {  
    REVIEWING("待审核", 0),  
    PASS("通过", 1),  
    REJECT("拒绝", 2);
  
    private final String text;

    private final int value;

    private static final Map<Integer, PictureReviewStatusEnum> PICTURE_REVIEW_STATUS_ENUM_MAP =
            Arrays.stream(PictureReviewStatusEnum.values())
                  .collect(Collectors.toMap( e ->  e.value, e -> e));


    PictureReviewStatusEnum(String text, int value) {
        this.text = text;  
        this.value = value;  
    }  
  
    /**  
     * 根据 value 获取枚举  
     */  
    public static PictureReviewStatusEnum getEnumByValue(Integer value) {
        PictureReviewStatusEnum pictureReviewStatusEnum = value == null ? null : PICTURE_REVIEW_STATUS_ENUM_MAP.getOrDefault(value, null);
        ThrowUtils.throwIf(Objects.isNull(pictureReviewStatusEnum), new BusinessException(ErrorCode.PARAMS_ERROR));
        return pictureReviewStatusEnum;
    }  
}
