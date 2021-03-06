package com.tiany.util.tokenizer;

import com.tiany.inf.Word;

public abstract class WordUtil {
    public static Word getWord(String str) {
        if (str == null||"".equals(str)) {
            return null;
        }
        if (StringWord.isString(str.charAt(0))) {
            return new StringWord(str);
        }
        if (SingleWord.isSingle(str.charAt(0))) {
            return new SingleWord(str);
        }
        if (KeyWord.isKey(str.charAt(0))) {
            return new KeyWord(str);
        }
        if (BlankWord.isBlank(str.charAt(0))) {
            return new BlankWord(str);
        }
        if (NumberWord.isNumber(str.charAt(0))) {
            return new NumberWord(str);
        }

        if (OtherWord.isOther(str.charAt(0))) {
            return new OtherWord(str);
        }
        return null;
    }
}
