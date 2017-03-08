package me.isming.xitek.bbs.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * Created by sam on 17/3/7.
 */
public class PostQuoteSpan extends QuoteSpan {
    private static final int STRIPE_WIDTH = 15;
    private static final int GAP_WIDTH = 40;

    public PostQuoteSpan() {
        super();
    }

    public PostQuoteSpan(int color) {
        super(color);
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        int color = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(getColor());

        c.drawRect(x, top, x + dir * STRIPE_WIDTH, bottom, p);

        p.setStyle(style);
        p.setColor(color);
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return STRIPE_WIDTH + GAP_WIDTH;
    }
}
