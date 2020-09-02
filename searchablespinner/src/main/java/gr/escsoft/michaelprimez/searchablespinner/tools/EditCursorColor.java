package gr.escsoft.michaelprimez.searchablespinner.tools;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;

/**
 * Created by michael on 12/31/16.
 */

public class EditCursorColor {

    private EditCursorColor() { }

    public static void setCursorColor(EditText editText, int color) {
        try {
            final Field drawableResField = TextView.class.getDeclaredField("mCursorDrawableRes");
            drawableResField.setAccessible(true);
            final Drawable drawable = getDrawable(editText.getContext(), drawableResField.getInt(editText));
            if (drawable == null) {
                return;
            }
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            final Object drawableFieldOwner;
            final Class<?> drawableFieldClass;
                final Field editorField = TextView.class.getDeclaredField("mEditor");
                editorField.setAccessible(true);
                drawableFieldOwner = editorField.get(editText);
                drawableFieldClass = drawableFieldOwner.getClass();
            final Field drawableField = drawableFieldClass.getDeclaredField("mCursorDrawable");
            drawableField.setAccessible(true);
            drawableField.set(drawableFieldOwner, new Drawable[] {drawable, drawable});
        } catch (Exception ignored) {
        }
    }

    private static Drawable getDrawable(Context context, int id) {
            return ContextCompat.getDrawable(context, id);
    }
}
