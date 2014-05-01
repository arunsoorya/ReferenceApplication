/*@(#)CustomButton.java}
 */
package com.barbourbooks.biblecrosswordpuzzles.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 *
 */
public class CustomButton extends Button {

  public CustomButton(Context context) {
    super(context);
  }

  public CustomButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public void setBackgroundDrawable(Drawable d) {
    CustomButtonBackgroundDrawable layer = new CustomButtonBackgroundDrawable(d);
    super.setBackgroundDrawable(layer);
  }

  protected class CustomButtonBackgroundDrawable extends LayerDrawable {

    protected ColorFilter pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);
    protected int disabledAlpha = 100;

    public CustomButtonBackgroundDrawable(Drawable d) {
      super(new Drawable[] { d });
    }

    @Override
    protected boolean onStateChange(int[] states) {
      boolean enabled = false;
      boolean pressed = false;

      for (int state : states) {
        if (state == android.R.attr.state_enabled)
          enabled = true;
        else if (state == android.R.attr.state_pressed)
          pressed = true;
      }

      mutate();
      if (enabled && pressed) {
        setColorFilter(pressedFilter);
      } else if (!enabled) {
        setColorFilter(null);
        setAlpha(disabledAlpha);
      } else {
        setColorFilter(null);
      }

      invalidateSelf();

      return super.onStateChange(states);
    }

    @Override
    public boolean isStateful() {
      return true;
    }
  }

}
