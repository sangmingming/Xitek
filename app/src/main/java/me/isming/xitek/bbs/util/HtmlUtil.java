package me.isming.xitek.bbs.util;

import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.text.style.QuoteSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.widget.TextView;

import org.xml.sax.XMLReader;

import me.isming.xitek.bbs.R;
import me.isming.xitek.bbs.glide.GlideImageGetter;

/**
 * Created by sam on 17/3/6.
 */
public class HtmlUtil {

    public static void setHtml(TextView textView, String string) {
        if (textView != null) {
            Spanned spanned = htmlToSpan(string, textView);
            if (spanned != null) {
                textView.setText(spanned);
                if (textView.getMovementMethod() == null
                        || !(textView.getMovementMethod() instanceof LinkMovementMethod)) {
                    Object[] spans = spanned.getSpans(0, spanned.length(), URLSpan.class);
                    if (spans != null && spans.length > 0) {
                        textView.setMovementMethod(new LinkMovementMethod());
                    } else {
                        textView.setMovementMethod(null);
                    }

                }

            } else {
                textView.setText("");
            }
        }
    }

    public static Spanned htmlToSpan(String html, TextView textView) {
        //暂时只处理默认的标签.如果有需要处理图片,则增加imgHandler,如果需要处理其他的html标签,则增加tagHandler;
        //mTagHandler.handleTag(false, tag, mSpannableStringBuilder, mReader);
        Spanned result = Html.fromHtml(html, new GlideImageGetter(textView.getContext(), textView), null);
        if (result != null) {

            SpannableStringBuilder builder = result instanceof  SpannableStringBuilder ?
                    (SpannableStringBuilder) result : new SpannableStringBuilder(result);
            Object[] objects = result.getSpans(0, result.length(), ImageSpan.class);
            if (objects != null && objects.length > 0) {   //改用没有改变字体设置的span
                for (Object obj : objects) {
                    ImageSpan span = (ImageSpan) obj;
                    String src = span.getSource();
                    if (!src.startsWith("http")) {
                        src = "http://cloud.xitek.com/" + src;
                    }
                    builder.setSpan(new ImageClickSpan(src), builder.getSpanStart(obj),
                            builder.getSpanEnd(obj), builder.getSpanFlags(obj));
                }
            }
            Object[] quotes = result.getSpans(0, result.length(), QuoteSpan.class);
            if (objects != null && quotes.length > 0) {
                for (int i = 0; i < quotes.length; i++) {
                    Log.i("htmTOString", builder.toString());
                    PostQuoteSpan quoteSpan = new PostQuoteSpan(textView.getContext().getResources().getColor(R.color.base_gray90));
                    builder.setSpan(quoteSpan, builder.getSpanStart(quotes[i]),
                            builder.getSpanEnd(quotes[i]), builder.getSpanFlags(quotes[i]));
                    builder.removeSpan(quotes[i]);
                }

            }

            return builder;
        }
        return null;
    }

    public static class QutoS {

    }
}
