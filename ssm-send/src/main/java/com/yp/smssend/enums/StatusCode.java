package com.yp.smssend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {

    VALIDATE_FAILURE(1000, "验证失败！"),
    LOGIN_FAIL_VALIDATE(1001, "登录失败！"),
    INITIALIZED_PASSWORD(1002, "初始化密码！"),
    LOGIN_FAIL_CODE_CAPTCHA(1003, "验证码错误！"),

    SUCCESS(2000, "操作成功！"),
    SAVE_SUCCESS(2000, "保存成功！"),
    DELETE_SUCCESS(2000, "删除成功！"),
    UPDATE_SUCCESS(2000, "修改成功！"),
    QUERY_SUCCESS(2000, "查询成功！"),

    FRIENDSHIP(3000, "友情提示！"),
    NULL_DATA_FRIENDSHIP(3001, "暂无数据！"),

    WARN(4000, "警告提示！"),
    BAD_REQUEST(4001, "错误请求"),
    CHECK_NULL_FRIENDSHIP(4002, "参数不能为空！"),
    NOT_LOGIN(4010, "未登录"),
    FORBIDDEN(4030, "没有访问权限"),
    NOT_FOUND(4040, "资源不存在"),
    METHOD_NOT_ALLOWED(4050, "请求方法不允许"),
    FRIENDSHIP_ERROR(4060, "参数错误！"),
    SESSION_EXPIRE(4070, "会话超时！"),
    TOKEN_EXPIRE(4080, "TOKEN超时！"),
    TOKEN_ERROR(4081, "TOKEN出错！"),

    FAILURE(5000, "操作失败！"),
    SAVE_FAILURE(5001, "保存失败！"),
    DELETE_FAILURE(5002, "删除失败！"),
    UPDATE_FAILURE(5003, "修改失败！"),
    QUERY_FAILURE(5004, "查询失败！"),
    DB_FAILURE(5005, "数据库异常！"),
    CREATE_ROOM_FAILURE(5006, "创建直播间失败！"),
    OPEN_ROOM_FAILURE(5007, "直播间开启失败！"),
    GET_LIVE_ROOM_URL_FAILURE(5008, "获取直播间地址失败！"),
    OSS_FILE_DELETE_FAILURE(5009, "OSS文件删除失败！"),
    FAILED_TO_READ_JSON_FILE(5010, "读取Json文件失败");


    private final int code;
    private final String msg;

    @Override
    public String toString() {
        return msg;
    }
}