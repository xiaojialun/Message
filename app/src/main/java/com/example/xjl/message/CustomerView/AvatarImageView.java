package com.example.xjl.message.CustomerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Created by xjl on 2017/10/26.
 */

public class AvatarImageView extends ImageView {
    private Paint paint=new Paint();
    public AvatarImageView(Context context) {
        super(context);
    }

    public AvatarImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //将头像按比例缩放
    private Bitmap scaleBitmap(Bitmap bitmap){
        int width = getWidth();
        //需转出float 不然可能精度不够导致scale为0
        float scale=(float)width/bitmap.getWidth();
        Matrix matrix=new Matrix();
        matrix.postScale(scale,scale);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    //将原始图像裁剪成正方形
    private Bitmap dealRawBitmap(Bitmap bitmap){
        int width=bitmap.getWidth();
        int hight=bitmap.getHeight();
        //获取宽度
        int minWidth=width>hight?hight:width;
        //计算正方形范围
        int leftTopX=(width-minWidth)/2;
        int leftTopY=(hight-minWidth)/2;
        Bitmap newBitmap=Bitmap.createBitmap(bitmap,leftTopX,leftTopY,minWidth,minWidth,null,false);
        return scaleBitmap(newBitmap);
    }

    //
    private Bitmap toRoundCorner(Bitmap bitmap,int pixels){
        //指定为ARGB_4444可以减小图片大小
        Bitmap output=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas=new Canvas(output);

        final int color=0xff424242;
        final Rect rect=new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(color);
        int x=bitmap.getWidth();
        canvas.drawCircle(x/2,x/2,x/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,rect,rect,paint);
        return output;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable=getDrawable();
        if(drawable!=null){
            Bitmap rawBitmap=((BitmapDrawable)drawable).getBitmap();
            //处理Bitmap转成正方形
            Bitmap newBitmap=dealRawBitmap(rawBitmap);
            //将newBitmap转成圆形
            Bitmap circleBitmap=toRoundCorner(newBitmap,14);

            final Rect rect=new Rect(0,0,circleBitmap.getWidth(),circleBitmap.getHeight());
            paint.reset();
            //绘制到画布上
            canvas.drawBitmap(circleBitmap,rect,rect,paint);
        }else {
            super.onDraw(canvas);
        }
    }
}
