package com.primax.model;

import lombok.Data;

/**
 * 文本卡片消息
 */

@Data
public class TextcardMessage extends  BaseMessage{

    //文本
    private Textcard textcard;

    //btntxt    否    按钮文字。 默认为“详情”， 不超过4个文字，超过自动截断。
}
