package com.andy.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Random;

public class StringUtil {

    private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
    private static final char[] AMP_ENCODE = "&amp;".toCharArray();
    private static final char[] LT_ENCODE = "&lt;".toCharArray();
    private static final char[] GT_ENCODE = "&gt;".toCharArray();
    private static MessageDigest digest = null;
    private static final int fillchar = 61;
    private static final String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static Random randGen = new Random();
    private static char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] zeroArray = "0000000000000000".toCharArray();
    public static final String EMPTY_STRING = "";
    public static final char DOT = '.';
    public static final char UNDERSCORE = '_';
    public static final String COMMA_SPACE = ", ";
    public static final String COMMA = ",";
    public static final String OPEN_PAREN = "(";
    public static final String CLOSE_PAREN = ")";
    public static final String EMPTY = "";

    /**
     * 浠tr鍙傚姞缁撳熬杩涜姣旇緝,鐪嬫槸鍚︾浉绛�,蹇界暐澶у皬鍐�
     * @param str
     * @param suffix
     * @return createBy lyy 2017 10 09
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        return endsWith(str, suffix, true);
    }
    private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
        if (str == null || suffix == null) {
            return (str == null && suffix == null);
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        int strOffset = str.length() - suffix.length();
        return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
    }

    private static String formatDep(String str, String[] args)
    {
        if (args == null) {
            return str;
        }
        for (int i = 0; i < args.length; i++) {
            if ((args[i] != null) &&
                    (str.indexOf("%s") != -1)) {
                str = str.replaceFirst("\\%s", args[i]);
            }
        }
        return str;
    }

    public static boolean isEmpty(String str)
    {
        return (str == null) || (str.length() == 0);
    }

    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str)
    {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String blankStr(String str, String defalutStr)
    {
        if (defalutStr == null) {
            defalutStr = "";
        }
        if (str == null) {
            return defalutStr;
        }
        return str.trim();
    }

    public static String blankStr(String str)
    {
        return blankStr(str, null);
    }

    public static boolean isNotBlank(String str)
    {
        return !isBlank(str);
    }

    public static String convertCastChar(String src)
    {
        if ((src == null) || (src.equals(""))) {
            return src;
        }
        int length = src.length();
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            switch (src.charAt(i))
            {
                case '%':
                case '\\':
                case '_':
                    tmp.append("\\");
            }
            tmp.append(src.charAt(i));
        }
        return tmp.toString();
    }

    public static String convertForXml(String src)
    {
        if ((src == null) || (src.equals(""))) {
            return src;
        }
        int length = src.length();
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < length; i++) {
            switch (src.charAt(i))
            {
                case '<':
                    tmp.append("&lt;");
                    break;
                case '>':
                    tmp.append("&gt;");
                    break;
                case '"':
                    tmp.append("&quot;");
                    break;
                case ' ':
                    int spaceCount;
                    for (spaceCount = 0; src.charAt(i) == ' '; spaceCount++) {
                        i++;
                    }
                    for (int j = 0; j < spaceCount / 2; j++) {
                        tmp.append("  ");
                    }
                    if (spaceCount % 2 != 0) {
                        tmp.append("&#160;");
                    }
                    i--;
                    break;
                case '&':
                    tmp.append("&amp;");
                    break;
                case '\r':
                    break;
                default:
                    tmp.append(src.charAt(i));
            }
        }
        return tmp.toString();
    }

    public static String convertSingleQuot(String src)
    {
        if ((src == null) || (src.equals(""))) {
            return src;
        }
        int length = src.length();
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (src.charAt(i) == '\'') {
                tmp.append("''");
            } else {
                tmp.append(src.charAt(i));
            }
        }
        return tmp.toString();
    }

    public static String replace(String src, String mod, String str)
    {
        if ((src == null) || (src.length() == 0)) {
            return src;
        }
        if ((mod == null) || (mod.length() == 0)) {
            return src;
        }
        if (src == null) {
            src = "";
        }
        StringBuffer buffer = new StringBuffer();
        int idx1 = 0;
        int idx2 = 0;
        while ((idx2 = src.indexOf(mod, idx1)) != -1)
        {
            buffer.append(src.substring(idx1, idx2)).append(str);
            idx1 = idx2 + mod.length();
        }
        buffer.append(src.substring(idx1));
        return buffer.toString();
    }

    public static final String replace(String line, String oldString, String newString, int[] count)
    {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0)
        {
            int counter = 0;
            counter++;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for ( j = i; (i = line.indexOf(oldString, i)) > 0; j = i)
            {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

    public static final String replaceIgnoreCase(String line, String oldString, String newString)
    {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0)
        {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i)
            {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    public static final String replaceIgnoreCase(String line, String oldString, String newString, int[] count)
    {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0)
        {
            int counter = 0;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i)
            {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = (counter + 1);
            return buf.toString();
        }
        return line;
    }

    public static String replaceOnce(String template, String placeholder, String replacement)
    {
        int loc = template.indexOf(placeholder);
        if (loc < 0) {
            return template;
        }
        return template.substring(0, loc) + replacement + template.substring(loc + placeholder.length());
    }

    /**
     * @deprecated
     */
    public static String replaceAll(String src, String regex, String replacement)
    {
        if (src != null) {
            return src.replaceAll(regex, replacement);
        }
        return src;
    }

    /**
     * @deprecated
     */
    public static String replaceAll(String src, String regex, String replacement, int model)
    {
        if (src != null) {
            return src.replaceAll(regex, replacement);
        }
        return src;
    }

    public static final String escapeHTMLTags(String in)
    {
        if (in == null) {
            return null;
        }
        int i = 0;
        int last = 0;
        char[] input = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int)(len * 1.3D));
        for (; i < len; i++)
        {
            char ch = input[i];
            if (ch <= '>') {
                if (ch == '<')
                {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(LT_ENCODE);
                }
                else if (ch == '>')
                {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(GT_ENCODE);
                }
            }
        }
        if (last == 0) {
            return in;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }

    public static final String escapeNull(String str)
    {
        return str == null ? "" : str;
    }

    public static final String escapeForXML(String string)
    {
        if (string == null) {
            return null;
        }
        int i = 0;
        int last = 0;
        char[] input = string.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int)(len * 1.3D));
        for (; i < len; i++)
        {
            char ch = input[i];
            if (ch <= '>') {
                if (ch == '<')
                {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(LT_ENCODE);
                }
                else if (ch == '&')
                {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(AMP_ENCODE);
                }
                else if (ch == '"')
                {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(QUOTE_ENCODE);
                }
            }
        }
        if (last == 0) {
            return string;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }

    public static final String unescapeFromXML(String string)
    {
        string = replace(string, "&lt;", "<");
        string = replace(string, "&gt;", ">");
        string = replace(string, "&quot;", "\"");
        return replace(string, "&amp;", "&");
    }



    public static final String encodeHex(byte[] bytes)
    {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++)
        {
            if ((bytes[i] & 0xFF) < 16) {
                buf.append("0");
            }
            buf.append(Long.toString(bytes[i] & 0xFF, 16));
        }
        return buf.toString();
    }

    public static final byte[] decodeHex(String hex)
    {
        char[] chars = hex.toCharArray();
        byte[] bytes = new byte[chars.length / 2];
        int byteCount = 0;
        for (int i = 0; i < chars.length; i += 2)
        {
            byte newByte = 0;
            newByte = (byte)(newByte | hexCharToByte(chars[i]));
            newByte = (byte)(newByte << 4);
            newByte = (byte)(newByte | hexCharToByte(chars[(i + 1)]));
            bytes[byteCount] = newByte;
            byteCount++;
        }
        return bytes;
    }

    public static String encodeBase64(String data)
    {
        return encodeBase64(data.getBytes());
    }

    public static String encodeBase64(byte[] data)
    {
        int len = data.length;
        StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);
        for (int i = 0; i < len; i++)
        {
            int c = data[i] >> 2 & 0x3F;
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            c = data[i] << 4 & 0x3F;
            i++;
            if (i < len) {
                c |= data[i] >> 4 & 0xF;
            }
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            if (i < len)
            {
                c = data[i] << 2 & 0x3F;
                i++;
                if (i < len) {
                    c |= data[i] >> 6 & 0x3;
                }
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            }
            else
            {
                i++;
                ret.append('=');
            }
            if (i < len)
            {
                c = data[i] & 0x3F;
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            }
            else
            {
                ret.append('=');
            }
        }
        return ret.toString();
    }

    public static String decodeBase64(String data)
    {
        return decodeBase64(data.getBytes());
    }

    public static String decodeBase64(byte[] data)
    {
        int len = data.length;
        StringBuffer ret = new StringBuffer(len * 3 / 4);
        for (int i = 0; i < len; i++)
        {
            int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            i++;
            int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            c = c << 2 | c1 >> 4 & 0x3;
            ret.append((char)c);
            i++;
            if (i < len)
            {
                c = data[i];
                if (61 == c) {
                    break;
                }
                c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char)c);
                c1 = c1 << 4 & 0xF0 | c >> 2 & 0xF;
                ret.append((char)c1);
            }
            i++;
            if (i < len)
            {
                c1 = data[i];
                if (61 == c1) {
                    break;
                }
                c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char)c1);
                c = c << 6 & 0xC0 | c1;
                ret.append((char)c);
            }
        }
        return ret.toString();
    }

    public static final String[] toLowerCaseWordArray(String text)
    {
        if ((text == null) || (text.length() == 0)) {
            return new String[0];
        }
        ArrayList wordList = new ArrayList();
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(text);
        int start = 0;
        for (int end = boundary.next(); end != -1; end = boundary.next())
        {
            String tmp = text.substring(start, end).trim();
            tmp = replace(tmp, "+", "");
            tmp = replace(tmp, "/", "");
            tmp = replace(tmp, "\\", "");
            tmp = replace(tmp, "#", "");
            tmp = replace(tmp, "*", "");
            tmp = replace(tmp, ")", "");
            tmp = replace(tmp, "(", "");
            tmp = replace(tmp, "&", "");
            if (tmp.length() > 0) {
                wordList.add(tmp);
            }
            start = end;
        }
        return (String[])wordList.toArray(new String[wordList.size()]);
    }

    public static final String randomString(int length)
    {
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    public static final String chopAtWord(String string, int length)
    {
        if (string == null) {
            return string;
        }
        char[] charArray = string.toCharArray();
        int sLength = string.length();
        if (length < sLength) {
            sLength = length;
        }
        for (int i = 0; i < sLength - 1; i++)
        {
            if ((charArray[i] == '\r') && (charArray[(i + 1)] == '\n')) {
                return string.substring(0, i + 1);
            }
            if (charArray[i] == '\n') {
                return string.substring(0, i);
            }
        }
        if (charArray[(sLength - 1)] == '\n') {
            return string.substring(0, sLength - 1);
        }
        if (string.length() < length) {
            return string;
        }
        for (int i = length - 1; i > 0; i--) {
            if (charArray[i] == ' ') {
                return string.substring(0, i).trim();
            }
        }
        return string.substring(0, length);
    }

    public static final String zeroPadString(String string, int length)
    {
        if ((string == null) || (string.length() > length)) {
            return string;
        }
        StringBuffer buf = new StringBuffer(length);
        buf.append(zeroArray, 0, length - string.length()).append(string);
        return buf.toString();
    }

    public static final String upperFirst(String name)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1));
        return sb.toString();
    }

    public static final String lowerFirst(String name)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(Character.toLowerCase(name.charAt(0))).append(name.substring(1));
        return sb.toString();
    }

    public static String join(String seperator, String[] strings)
    {
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        StringBuffer buf = new StringBuffer(length * strings[0].length()).append(strings[0]);
        for (int i = 1; i < length; i++) {
            buf.append(seperator).append(strings[i]);
        }
        return buf.toString();
    }

    public static String Array2String(String[] values)
    {
        return join(",", values);
    }

    private static final byte hexCharToByte(char ch)
    {
        switch (ch)
        {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'a':
                return 10;
            case 'b':
                return 11;
            case 'c':
                return 12;
            case 'd':
                return 13;
            case 'e':
                return 14;
            case 'f':
                return 15;
        }
        return 0;
    }

    public static String toLowerCase(String value)
    {
        if (isBlank(value)) {
            return value;
        }
        return value.trim().toLowerCase();
    }


    public static boolean isNumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static String getStringVal(Object obj) {
        if (obj instanceof Integer || obj instanceof Double || obj instanceof Float || obj instanceof BigDecimal)
            return String.valueOf(obj);
        if (StringUtil.isEmpty((String) obj))
            return "";
        else
            return ((String) obj).replace("\r", "").replace("\n", "").replace("\t", "");
    }
}
