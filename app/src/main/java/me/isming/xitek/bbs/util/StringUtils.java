package me.isming.xitek.bbs.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by sam on 17/3/3.
 */
public class StringUtils {

    public static LinkedHashMap<String, String> localLinkedHashMap = new LinkedHashMap<>();

    static {
        localLinkedHashMap.put("\\[url.+?\\[img\\]static/image/common/back.gif\\[/img\\]\\[/url\\]", "");
        localLinkedHashMap.put("\\[b\\](.+?)\\[/b\\]", "<b>$1</b>");
        localLinkedHashMap.put("\\[i\\](.+?)\\[/i\\]", "<i>$1</i>");
        localLinkedHashMap.put("\\[i=s\\](.+?)\\[/i\\]", "\n<i><font color='grey'>$1</font></i>");
        localLinkedHashMap.put("\\[u\\](.+?)\\[/u\\]", "<u>$1</u>");
        localLinkedHashMap.put("\\[h1\\](.+?)\\[/h1\\]", "<h1>$1</h1>");
        localLinkedHashMap.put("\\[h2\\](.+?)\\[/h2\\]", "<h2>$1</h2>");
        localLinkedHashMap.put("\\[h3\\](.+?)\\[/h3\\]", "<h3>$1</h3>");
        localLinkedHashMap.put("\\[h4\\](.+?)\\[/h4\\]", "<h4>$1</h4>");
        localLinkedHashMap.put("\\[h5\\](.+?)\\[/h5\\]", "<h5>$1</h5>");
        localLinkedHashMap.put("\\[h6\\](.+?)\\[/h6\\]", "<h6>$1</h6>");
        localLinkedHashMap.put("\\[p\\](.+?)\\[/p\\]", "<p>$1</p>");
        localLinkedHashMap.put("\\[p=(.+?),(.+?)\\](.+?)\\[/p\\]", "<p style='text-indent:$1px;line-height:$2%;'>$3</p>");
        localLinkedHashMap.put("\\[center\\](.+?)\\[/center\\]", "<div align='center'>$1");
        localLinkedHashMap.put("\\[align=(.+?)\\](.+?)\\[/align\\]", "<div align='$1'>$2");
        localLinkedHashMap.put("\\[color=(.+?)\\](.+?)\\[/color\\]", "<font color='$1'>$2</font>");
        localLinkedHashMap.put("\\[size=(.+?)\\](.+?)\\[/size\\]", "$2");
        localLinkedHashMap.put("\\[img[\\d=,\"]*?\\]([^\\[\\]]+?)/thumb_(\\d+?_\\d+?.jpg)\\[/img\\]", "<img src='$1/thumb_$2' /> ");
        localLinkedHashMap.put("\\[img[\\d=,\"]*?\\]([^\\[\\]]+?)/(\\d+?_\\d+?.jpg)\\[/img\\]", "<img src='$1/thumb_$2' /> ");
        localLinkedHashMap.put("\\[img[\\d=,\"]*?\\]([^\\[\\]]+?)\\[/img\\]", "<img src='$1' /> ");
        localLinkedHashMap.put("\\[email\\](.+?)\\[/email\\]", "<a href='mailto:$1'>$1</a>");
        localLinkedHashMap.put("\\[flash\\](.+?)\\[/flash\\]", "<img src='$1' alt='flash'></img>");
        localLinkedHashMap.put("\\[email=(.+?)\\](.+?)\\[/email\\]", "<a href=mailto:$1>$2</a>");
        localLinkedHashMap.put("\\[url\\](.+?)\\[/url\\]", "<a href=$1>$1</a>");
        localLinkedHashMap.put("\\[url=(.+?)\\](.+?)\\[/url\\]", "<a href=$1>$2</a>");
        //localLinkedHashMap.put("\\[quote\\](.+?)\\[/quote\\]", "<blockquote>$1</blockquote>\n");
        localLinkedHashMap.put(":\\)", "<img src=\"/static/image/smiley/xitek/smile.gif\"/>");
        localLinkedHashMap.put(":\\(", "<img src=\"/static/image/smiley/xitek/frown.gif\"/>");
        localLinkedHashMap.put(":o", "<img src=\"/static/image/smiley/xitek/redface.gif\"/>");
        localLinkedHashMap.put(":D", "<img src=\"/static/image/smiley/xitek/biggrin.gif\"/>");
        localLinkedHashMap.put(";\\)", "<img src=\"/static/image/smiley/xitek/wink.gif\"/>");
        localLinkedHashMap.put(":p", "<img src=\"/static/image/smiley/xitek/tongue.gif\"/>");
        localLinkedHashMap.put(":cool:", "<img src=\"/static/image/smiley/xitek/cool.gif\"/>");
        localLinkedHashMap.put(":rolleyes:", "<img src=\"/static/image/smiley/xitek/rolleyes.gif\"/>");
        localLinkedHashMap.put(":mad:", "<img src=\"/static/image/smiley/xitek/mad.gif\"/>");
        localLinkedHashMap.put(":eek:", "<img src=\"/static/image/smiley/xitek/eek.gif\"/>");
        localLinkedHashMap.put(":confused:", "<img src=\"/static/image/smiley/xitek/confused.gif\"/>");
        localLinkedHashMap.put(":cry:", "<img src=\"/static/image/smiley/xitek/cry.gif\"/>");
        localLinkedHashMap.put(":smile:", "<img src=\"/static/image/smiley/xitek/expr001.gif\"/>");
        localLinkedHashMap.put(":haha1:", "<img src=\"/static/image/smiley/xitek/expr002.gif\"/>");
        localLinkedHashMap.put(":\\!:", "<img src=\"/static/image/smiley/xitek/expr003.gif\"/>");
        localLinkedHashMap.put(":g:", "<img src=\"/static/image/smiley/xitek/expr004.gif\"/>");
        localLinkedHashMap.put(":angry:", "<img src=\"/static/image/smiley/xitek/expr005.gif\"/>");
        localLinkedHashMap.put(":expr006:", "<img src=\"/static/image/smiley/xitek/expr006.gif\"/>");
        localLinkedHashMap.put(":shy:", "<img src=\"/static/image/smiley/xitek/expr007.gif\"/>");
        localLinkedHashMap.put(":expr008:", "<img src=\"/static/image/smiley/xitek/expr008.gif\"/>");
        localLinkedHashMap.put(":cool1:", "<img src=\"/static/image/smiley/xitek/expr009.gif\"/>");
        localLinkedHashMap.put(":ha:", "<img src=\"/static/image/smiley/xitek/expr010.gif\"/>");
        localLinkedHashMap.put(":expr011:", "<img src=\"/static/image/smiley/xitek/expr011.gif\"/>");
        localLinkedHashMap.put(":expr012:", "<img src=\"/static/image/smiley/xitek/expr012.gif\"/>");
        localLinkedHashMap.put(":shutup1:", "<img src=\"/static/image/smiley/xitek/expr013.gif\"/>");
        localLinkedHashMap.put(":expr014:", "<img src=\"/static/image/smiley/xitek/expr014.gif\"/>");
        localLinkedHashMap.put(":expr015:", "<img src=\"/static/image/smiley/xitek/expr015.gif\"/>");
        localLinkedHashMap.put(":expr016:", "<img src=\"/static/image/smiley/xitek/expr016.gif\"/>");
        localLinkedHashMap.put(":expr017:", "<img src=\"/static/image/smiley/xitek/expr017.gif\"/>");
        localLinkedHashMap.put(":cry1:", "<img src=\"/static/image/smiley/xitek/expr018.gif\"/>");
        localLinkedHashMap.put(":expr019:", "<img src=\"/static/image/smiley/xitek/expr019.gif\"/>");
        localLinkedHashMap.put(":expr020:", "<img src=\"/static/image/smiley/xitek/expr020.gif\"/>");
        localLinkedHashMap.put(":expr021:", "<img src=\"/static/image/smiley/xitek/expr021.gif\"/>");
        localLinkedHashMap.put(":expr022:", "<img src=\"/static/image/smiley/xitek/expr022.gif\"/>");
        localLinkedHashMap.put(":envy:", "<img src=\"/static/image/smiley/xitek/expr023.gif\"/>");
        localLinkedHashMap.put(":expr024:", "<img src=\"/static/image/smiley/xitek/expr024.gif\"/>");
        localLinkedHashMap.put(":shutup2:", "<img src=\"/static/image/smiley/xitek/expr025.gif\"/>");
        localLinkedHashMap.put(":good:", "<img src=\"/static/image/smiley/xitek/tongyi.gif\"/>");
        localLinkedHashMap.put(":nogood:", "<img src=\"/static/image/smiley/xitek/fandui.gif\"/>");
        localLinkedHashMap.put(":ax:", "<img src=\"/static/image/smiley/xitek/zhuoji.gif\"/>");
        localLinkedHashMap.put(":crying:", "<img src=\"/static/image/smiley/xitek/daku.gif\"/>");
        localLinkedHashMap.put(":haha:", "<img src=\"/static/image/smiley/xitek/hahaha.gif\"/>");
        localLinkedHashMap.put(":shutup:", "<img src=\"/static/image/smiley/xitek/shutup.gif\"/>");
        localLinkedHashMap.put(":gxep4:", "<img src=\"/static/image/smiley/xitek/happy.gif\"/>");
        localLinkedHashMap.put(":gxep8:", "<img src=\"/static/image/smiley/xitek/shy.gif\"/>");
        localLinkedHashMap.put(":gxep1:", "<img src=\"/static/image/smiley/xitek/shutup1.gif\"/>");
        localLinkedHashMap.put(":gxep6:", "<img src=\"/static/image/smiley/xitek/sleep.gif\"/>");
        localLinkedHashMap.put(":gxep5:", "<img src=\"/static/image/smiley/xitek/angry.gif\"/>");
        localLinkedHashMap.put(":gxep3:", "<img src=\"/static/image/smiley/xitek/cry_b.gif\"/>");
        localLinkedHashMap.put(":gxep7:", "<img src=\"/static/image/smiley/xitek/envy.gif\"/>");
        localLinkedHashMap.put(":gxep9:", "<img src=\"/static/image/smiley/xitek/question.gif\"/>");
        localLinkedHashMap.put(":gexp2:", "<img src=\"/static/image/smiley/xitek/shy_b.gif\"/>");
        localLinkedHashMap.put(":roll:", "<img src=\"/static/image/smiley/xitek/roll.gif\"/>");
        localLinkedHashMap.put(":brickwall:", "<img src=\"/static/image/smiley/xitek/brickwall.gif\"/>");
        localLinkedHashMap.put("\r", "");
        localLinkedHashMap.put("\n{2,}", "<br/><br/>");
        localLinkedHashMap.put("\n", "<br/>");
        localLinkedHashMap.put("\\[quote\\](.+?)\\[/quote\\]", "<blockquote>$1</blockquote>\n");
    }

    public static String bbcode(String content) {
        String result = content;
        for(Map.Entry<String, String> s : localLinkedHashMap.entrySet()) {
            result = Pattern.compile(s.getKey()).matcher(result).replaceAll(s.getValue());
        }
        return result.replace("cnc.xitek.com", "forum.xitek.com");
    }
}
