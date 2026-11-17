package com.firestarter.codehighlight;

import android.text.Spannable;
import android.text.TextPaint;
import java.lang.reflect.Field;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.TextStyleSpan;
import org.telegram.ui.Components.URLSpanMono;

/** Inline code keeps stock tap to copy, is drawn in the theme's link color with an underline, and gets a
 *  soft highlight behind it. Spans are swapped once, when a message's text is built; drawing reads one
 *  theme color and sets three paint fields. Any failure falls back to the stock look. */
public final class CodeCore {
    static volatile int alpha = 0x30;
    static volatile boolean linkOk = true;
    static Field fMsg, fStart, fEnd, fType, fStyle;

    public static void setAlpha(int a) { alpha = Math.max(0, Math.min(255, a)); }

    /** Called once at load: proves the theme link colors resolve on this build. */
    public static String probe() {
        try {
            return "in=" + Integer.toHexString(Theme.getColor(Theme.key_chat_messageLinkIn))
                 + " out=" + Integer.toHexString(Theme.getColor(Theme.key_chat_messageLinkOut));
        } catch (Throwable t) {
            linkOk = false;
            return "link colors unavailable, stock text color kept: " + t;
        }
    }

    static Field f(String n) throws Exception { Field x = URLSpanMono.class.getDeclaredField(n); x.setAccessible(true); return x; }

    public static int wrap(CharSequence text) {
        if (!(text instanceof Spannable)) return 0;
        int n = 0;
        try {
            Spannable sp = (Spannable) text;
            URLSpanMono[] spans = sp.getSpans(0, sp.length(), URLSpanMono.class);
            if (spans == null || spans.length == 0) return 0;
            if (fMsg == null) { fMsg = f("currentMessage"); fStart = f("currentStart"); fEnd = f("currentEnd"); fType = f("currentType"); fStyle = f("style"); }
            for (URLSpanMono s : spans) {
                if (s.getClass() != URLSpanMono.class) continue;
                int st = sp.getSpanStart(s), en = sp.getSpanEnd(s), fl = sp.getSpanFlags(s);
                if (st < 0 || en <= st) continue;
                CodeSpan c = new CodeSpan((CharSequence) fMsg.get(s), fStart.getInt(s), fEnd.getInt(s), fType.getByte(s), (TextStyleSpan.TextStyleRun) fStyle.get(s));
                sp.removeSpan(s);
                sp.setSpan(c, st, en, fl);
                n++;
            }
        } catch (Throwable ignore) { }
        return n;
    }

    public static final class CodeSpan extends URLSpanMono {
        final byte type;
        public CodeSpan(CharSequence m, int s, int e, byte t, TextStyleSpan.TextStyleRun r) { super(m, s, e, t, r); type = t; }
        @Override public void updateDrawState(TextPaint p) {
            super.updateDrawState(p);
            try {
                p.bgColor = (p.getColor() & 0x00FFFFFF) | (alpha << 24);
                if (linkOk) {
                    // type 1 = my bubble, 0 = their bubble, 2 = white text over media (color kept)
                    if (type == 1) p.setColor(Theme.getColor(Theme.key_chat_messageLinkOut));
                    else if (type == 0) p.setColor(Theme.getColor(Theme.key_chat_messageLinkIn));
                    p.setUnderlineText(true);
                }
            } catch (Throwable t) { linkOk = false; }
        }
    }
}
