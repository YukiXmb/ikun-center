package com.yuki.usercenter.utils;

import com.yuki.usercenter.common.BaseResponse;
import com.yuki.usercenter.common.ErrorCode;

public class ResponseUtils {
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<T>(0,data,"ok");
    }

    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse(errorCode);
    }

    public static BaseResponse error(ErrorCode errorCode, String message, String description){
        return new BaseResponse(errorCode.getCode(),null,message,description);
    }

    public static BaseResponse error(int code, String message, String description){
        return new BaseResponse(code,null,message,description);
    }


    public static BaseResponse error(ErrorCode errorCode, String description){
        return new BaseResponse(errorCode.getCode(),null,errorCode.getMessage(),description);
    }


}
