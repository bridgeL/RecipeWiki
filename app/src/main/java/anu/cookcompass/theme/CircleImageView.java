package anu.cookcompass.theme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

/**
 * @author u7759982, Jiangbei Zhang
 * @feature Data-Profile
 * Self defined class extends ImageView,to create circle shape image,and draw
 * one green circle to distinguish the profile image.
 */
public class CircleImageView extends androidx.appcompat.widget.AppCompatImageView {
    private Paint paint;
    private float radius;

    /**
     * constructor initialize when only context provided
     */
    public CircleImageView(Context context) {
        super(context);
        init();
    }

    /**
     constructor initialize when both context and attributes are provided
     */
    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //initial the painter with anti alias
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override//self define logic for draw the picture
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        //transfer drawable to bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //calculate how to resize the image to fit the imageview
        Matrix matrix = getMatrix(bitmap);
        bitmapShader.setLocalMatrix(matrix);

        paint.setShader(bitmapShader);
        radius = Math.min(getWidth(), getHeight()) / 2f;
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, paint);
        //draw the boarder with color
        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.GREEN);  // color GREEN
        borderPaint.setStrokeWidth(3);  // 3dp width
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius - 1.5f, borderPaint);

    }

    /**
     * This function aims to calculate how to resize the photo to fit the image view
     */
    @NonNull
    private Matrix getMatrix(Bitmap bitmap) {
        float scale;
        float dx;
        float dy;

        //justify image width with the view height or the image height with the view width
        if (bitmap.getWidth() >getHeight() || bitmap.getHeight() > getWidth()) {
            //for the bitmap,if its height> width, resize depends on height
            //else resize on width
            scale = bitmap.getWidth() > bitmap.getHeight() ?
                    (float) getWidth() / bitmap.getWidth() :
                    (float) getHeight() / bitmap.getHeight();
            dx = (getWidth() - bitmap.getWidth() * scale) * 0.5f;
            dy = (getHeight() - bitmap.getHeight() * scale) * 0.5f;
        } else {
            scale = bitmap.getWidth() < bitmap.getHeight() ?
                    (float) getWidth() / bitmap.getWidth() :
                    (float) getHeight() / bitmap.getHeight();
            dx = (getWidth() - bitmap.getWidth() * scale) * 0.5f;
            dy = (getHeight() - bitmap.getHeight() * scale) * 0.5f;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);//width resize and height resize
        matrix.postTranslate(dx, dy);
        return matrix;
    }
}