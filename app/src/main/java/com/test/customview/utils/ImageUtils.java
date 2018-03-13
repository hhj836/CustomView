/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test.customview.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;


public class ImageUtils {
    public static final int SCALE_IMAGE_WIDTH = 720;//加载本地图片缩放尺寸
    public static final int SCALE_IMAGE_HEIGHT = 1280;



    public static Bitmap decodeScaleImage(String path, int width, int height) {
        BitmapFactory.Options localOptions = getBitmapOptions(path);
        if((localOptions.outHeight<=height)&&localOptions.outWidth<=width){
            localOptions.inSampleSize=1;
        }else {
            localOptions.inSampleSize = calculateInSampleSize(localOptions, width, height);
        }
        localOptions.inJustDecodeBounds = false;
        try {
            Bitmap localBitmap= BitmapFactory.decodeFile(path, localOptions);
            return localBitmap;
        }catch (Exception e){

        }
        return  null;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            //使用需要的宽高的最大值来计算比率
            final int suitedValue = reqHeight > reqWidth ? reqHeight : reqWidth;
            final int heightRatio = Math.round((float) height / (float) suitedValue);
            final int widthRatio = Math.round((float) width / (float) suitedValue);

            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;//用最大
        }
        return inSampleSize;
    }


    public static Bitmap rotaingImageView(int rotate, Bitmap paramBitmap) {
        Matrix localMatrix = new Matrix();
        localMatrix.postRotate(rotate);
        Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix, true);
        return localBitmap;
    }

    public static BitmapFactory.Options getBitmapOptions(String paramString) {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(paramString, localOptions);
        return localOptions;
    }

    /**
     * 根据路径获得图片并压缩返回bitmap
     *
     * @param filePath
     * @return Bitmap
     */
    public static Bitmap decodeLocalFile(String filePath) {



       return decodeScaleImage(filePath,ImageUtils.SCALE_IMAGE_WIDTH, ImageUtils.SCALE_IMAGE_HEIGHT);
    }


}
