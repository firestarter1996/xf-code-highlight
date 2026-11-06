package com.firestarter.codehighlight;

import android.text.Spannable;
import android.text.TextPaint;
import java.lang.reflect.Field;
import org.telegram.ui.Components.TextStyleSpan;
import org.telegram.ui.Components.URLSpanMono;

/** Inline code keeps stock tap to copy and gets a soft highlight behind it. Spans are swapped once,
 *  when a message's text is built; drawing only sets one paint field. */
public final class CodeCore {
    static volatile int alpha = 0x30;
    static Field fMsg, fStart, fEnd, fType, fStyle;

    public static void setAlpha(int a) { alpha = Math.max(0, Math.min(255, a)); }

    static Field f(String n) throws Exception { Field x = URLSpanMono.class.getDeclaredField(n); x.setAccessible(true); return x; }

    public static int wrap(CharSequence text) {
        if (!(text instanceof Spannable)) return 0;
        Spannable sp = (Spannable) text;
        URLSpanMono[] spans = sp.getSpans(0, sp.length(), URLSpanMono.class);
        if (spans == null || spans.length == 0) return 0;
        int n = 0;
        try {
            if (fMsg == null) { fMsg = f("currentMessage"); fStart = f("currentStart"); fEnd = f("currentEnd"); fType = f("currentType"); fStyle = f("style"); }
            for (URLSpanMono s : spans) {
                if (s.getClass() != URLSpanMono.class) continue;
                int st = sp.getSpanStart(s), en = sp.getSpanEnd(s), fl = sp.getSpanFlags(s);
                CodeSpan c = new CodeSpan((CharSequence) fMsg.get(s), fStart.getInt(s), fEnd.getInt(s), fType.getByte(s), (TextStyleSpan.TextStyleRun) fStyle.get(s));
                sp.removeSpan(s);
                sp.setSpan(c, st, en, fl);
                n++;
            }
        } catch (Throwable ignore) { }
        return n;
    }

    public static final class CodeSpan extends URLSpanMono {
        public CodeSpan(CharSequence m, int s, int e, byte t, TextStyleSpan.TextStyleRun r) { super(m, s, e, t, r); }
        @Override public void updateDrawState(TextPaint p) {
            super.updateDrawState(p);
            p.bgColor = (p.getColor() & 0x00FFFFFF) | (alpha << 24);
        }
    }
}
