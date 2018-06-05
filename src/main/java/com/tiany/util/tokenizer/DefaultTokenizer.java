package com.tiany.util.tokenizer;

import com.tiany.inf.Tokenizer;
import com.tiany.inf.Word;

import java.util.ArrayList;
import java.util.List;

public class DefaultTokenizer implements Tokenizer {
    @Override
    public List<String> split(String str) {
        List<String> list = new ArrayList<>();
        String word = "";
        while (true){
            Word w = WordUtil.getWord(str);
            if (w == null){
                break;
            }
            word = w.getValue();
            list.add(word);
            str = str.substring(w.endIndex());
        }
        return list;
    }



}
