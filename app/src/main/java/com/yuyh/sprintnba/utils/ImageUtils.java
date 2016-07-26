package com.yuyh.sprintnba.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yuyh.library.utils.io.FileUtils;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.library.utils.toast.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author yuyh.
 * @date 2016/7/26.
 */
public class ImageUtils {

    private static String saveDir = Environment.getExternalStorageDirectory() + "/SprintNBA/images";

    /**
     * Fresco 保存图片。若有磁盘缓存，则从缓存拷贝，否则重新下载。
     *
     * @param context
     * @param picUrl
     */
    public static void saveImage(Context context, String picUrl) {
        //根据图片url获取到磁盘缓存CacheKey
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(Uri.parse(picUrl)));

        // 获取缓存文件
        File localFile = null;
        if (cacheKey != null) {
            if (ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getMainDiskStorageCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }

        String filename = System.currentTimeMillis() + ".jpg";
        File file = new File(saveDir + "/" + filename);
        // 判断是否缓存
        if (localFile == null) {
            ImageUtils.downLoadImage(context, picUrl, file);
            return;
        } else {
            ImageUtils.copyImage(context, localFile, file);
        }
    }

    /**
     * 保存文件
     *
     * @param context
     * @param src     源文件
     * @param dst     目标文件
     * @return
     */
    public static boolean copyImage(Context context, File src, File dst) {

        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(src);
            in = fi.getChannel();//得到对应的文件通道
            if (!dst.exists())
                FileUtils.createFile(dst);
            fo = new FileOutputStream(dst);
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
            ToastUtils.showSingleToast("图片保存成功");
            insertImage(context, dst.getAbsolutePath(), dst.getName(), null);
            return true;
        } catch (IOException e) {
            LogUtils.e("1:" + e.toString());
            ToastUtils.showSingleToast("图片保存失败");
            return false;
        } finally {
            try {

                if (fi != null) {
                    fi.close();
                }

                if (in != null) {
                    in.close();
                }

                if (fo != null) {
                    fo.close();
                }

                if (out != null) {
                    out.close();
                }

            } catch (IOException e) {
                LogUtils.e(e.toString());
                return false;
            }
        }
    }

    /**
     * 下载图片并保存
     *
     * @param url
     * @param dst
     * @param context
     */
    public static void downLoadImage(final Context context, String url, final File dst) {
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(Bitmap bitmap) {
                if (bitmap == null) {
                    ToastUtils.showSingleToast("图片保存失败，bitmap null");
                }
                if (!dst.exists())
                    FileUtils.createFile(dst);
                try {
                    FileOutputStream fos = new FileOutputStream(dst);
                    assert bitmap != null;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    ToastUtils.showSingleToast("图片保存成功");
                    insertImage(context, dst.getAbsolutePath(), dst.getName(), null);
                } catch (IOException e) {
                    LogUtils.e("2:" + e.toString());
                    ToastUtils.showSingleToast("图片保存失败");
                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
            }
        }, CallerThreadExecutor.getInstance());
    }

    /**
     * 保存图片到本地并插入到图库
     *
     * @param context
     * @param bmp
     * @return
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(saveDir, fileName);
        if (!file.exists()) {
            FileUtils.createFile(file);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return insertImage(context, file.getAbsolutePath(), fileName, null);
    }

    /**
     * 图片文件插入系统图库
     *
     * @param context
     * @param imagePath
     * @param name
     * @param description
     * @return
     */
    public static boolean insertImage(Context context, String imagePath, String name, String description) {
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imagePath, name, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        // 通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));
        return true;
    }

}
