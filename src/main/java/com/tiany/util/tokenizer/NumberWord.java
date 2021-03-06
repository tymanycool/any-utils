package com.tiany.util.tokenizer;

import com.tiany.inf.Condition;
import com.tiany.inf.Word;
import com.tiany.util.StringUtil;

public class NumberWord implements Word {

    private String firstWord;

    private String strData;

    public NumberWord(String strData) {
        this.strData = strData;
    }

    @Override
    public int length() {
        return firstWord.length();
    }

    @Override
    public String getValue() {
        return getFirstWord();
    }

    @Override
    public int beginIndex() {
        return getFistWordIndex();
    }

    @Override
    public int endIndex() {
        return getFistWordIndex()+length();
    }

    private String getFirstWord() {
        if (strData == null) {
            return null;
        }
        int fistWordIndex = getFistWordIndex();
        if (isNumber(strData.charAt(fistWordIndex))) {
            firstWord = StringUtil.substringUntil(strData, fistWordIndex, new Condition() {
                @Override
                public boolean matches(Object obj, Object nextObj) {
                    return !isNumber((Character)obj);
                }
            });
        }
        return firstWord;
    }

    /**
     * ch是数字类型
     * @param ch
     * @return
     */
    public static boolean isNumber(char ch) {
        return Character.isDigit(ch)||ch=='.';
    }

    private int getFistWordIndex(){
        for (int i = 0;i < strData.length();i++){
            if(isNumber(strData.charAt(i))){
                return i;
            }
        }
        return -1;
    }
}
