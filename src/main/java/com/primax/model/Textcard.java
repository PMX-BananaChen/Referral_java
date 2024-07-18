package com.primax.model;


import lombok.Data;

/**
 * 文本卡片 消息类型
 */
@Data
public class Textcard {

    //是 标题，不超过128个字节，超过会自动截断
    private String title;
    //是    描述，不超过512个字节，超过会自动截断
    private String description;
    //是    点击后跳转的链接。
    private String url;
}
