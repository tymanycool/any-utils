package com.tiany.inf;

import java.util.List;

/**
 * 分词器
 */
public interface Tokenizer {
    List<String> split(String str);
}
