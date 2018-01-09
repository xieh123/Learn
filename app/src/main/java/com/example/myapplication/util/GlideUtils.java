package com.example.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.widget.CircleImageView;

import java.io.File;


public class GlideUtils {


    //图片加载监听器
    public abstract interface ImageLoadListener {

        /**
         * 图片加载成功回调
         *
         * @param uri      图片url 或资源id 或 文件
         * @param view     目标载体，不传则为空
         * @param resource 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
         */
        <T, K> void onLoadingComplete(T uri, ImageView view, K resource);

        /**
         * 图片加载异常返回
         *
         * @param source 图片地址、File、资源id
         * @param e      异常信息
         */
        <T> void onLoadingError(T source, Exception e);

    }


    public abstract interface ImageLoadDetailListener {

        /**
         * 图片加载成功回调
         *
         * @param uri      图片url 或资源id 或 文件
         * @param view     目标载体，不传则为空
         * @param resource 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
         */
        <T, K> void onLoadingComplete(T uri, ImageView view, K resource);

        /**
         * 图片加载异常返回
         *
         * @param source 图片地址、File、资源id
         * @param e      异常信息
         */
        <T> void onLoadingError(T source, Exception e);

        <T> void onLoadingStart(T source);

    }


    /**
     * 图片加载监听类
     *
     * @param <T> 图片链接 的类型
     * @param <K> 图片资源返回类型
     * @param <Z> 返回的图片url
     */
    private static class GlideListener<T, K, Z> implements RequestListener<T, K> {

        ImageLoadListener imageLoadListener = null;
        Z url;
        ImageView imageView = null;

        GlideListener(ImageLoadListener imageLoadListener, Z url, ImageView view) {
            this.imageLoadListener = imageLoadListener;
            this.url = url;
            this.imageView = view;
        }

        GlideListener(ImageLoadListener imageLoadListener, Z url) {
            this.imageLoadListener = imageLoadListener;
            this.url = url;
        }

        GlideListener(Z url) {
            this.url = url;
        }

        @Override
        public boolean onResourceReady(K resource, T model, Target<K> target, boolean isFromMemoryCache, boolean isFirstResource) {
            if (null != imageLoadListener) {
                if (imageView != null) {
                    imageLoadListener.onLoadingComplete(url, imageView, resource);
                } else {
                    imageLoadListener.onLoadingComplete(url, null, resource);
                }
            }

            return false;
        }

        @Override
        public boolean onException(Exception e, T model, Target<K> target, boolean isFirstResource) {
            if (imageLoadListener != null) {
                imageLoadListener.onLoadingError(url, e);
            }
            return false;
        }
    }


    /**
     * 获取存储器上的图片,回调返回GlideDrawable
     *
     * @param context           上下文
     * @param file              文件File
     * @param imageLoadListener 回调监听器
     */
    public static void loadImage(Context context, final File file, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<File> type = Glide.with(context).load(file);
        if (type != null) {
            type.listener(new GlideListener<File, GlideDrawable, File>(imageLoadListener, file));
        }
    }

    /**
     * 获取资源中的图片，回调返回GlideDrawable
     *
     * @param context           上下文
     * @param resourceId        资源id
     * @param imageLoadListener 回调监听器
     */
    public static void loadImage(Context context, final int resourceId, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<Integer> type = Glide.with(context).load(resourceId);
        if (type != null) {
            type.listener(new GlideListener<Integer, GlideDrawable, Integer>(imageLoadListener, resourceId));
        }
    }

    /**
     * 获取网络图片，回调返回 GlideDrawable
     *
     * @param context           上下文
     * @param url               图片url
     * @param imageLoadListener 回调监听器
     */
    public static void loadImage(Context context, final String url, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<String> type = Glide.with(context).load(url);
        if (type != null) {
            type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url));
        }

    }

    /**
     * 加载存储器上的图片到目标载体
     *
     * @param file      文件File
     * @param imageView 要显示到的图片ImageView
     */
    public static void loadImage(final ImageView imageView, final File file, final ImageLoadListener imageLoadListener) {
        Glide.with(imageView.getContext())
                .load(file)
                .listener(new GlideListener<File, GlideDrawable, File>(imageLoadListener, file, imageView))
                .into(imageView);
    }

    /**
     * 加载资源中的图片到目标载体
     *
     * @param resourceId 资源id
     * @param imageView  图片View
     */
    public static void loadImage(final ImageView imageView, final int resourceId, final ImageLoadListener imageLoadListener) {
        Glide.with(imageView.getContext())
                .load(resourceId)
                .listener(new GlideListener<Integer, GlideDrawable, Integer>(imageLoadListener, resourceId, imageView))
                .into(imageView);
    }


    /**
     * 加载圆形头像，用的是普通ImageView，有动画效果
     *
     * @param url               图片url
     * @param imageView         要显示到的ImageView
     * @param imageLoadListener 加载回调监听器
     * @param parms             设置占位符和加载失败图片
     */
    public static void loadCircleImage(final ImageView imageView, final String url, final ImageLoadListener imageLoadListener, final int... parms) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        if (parms != null && parms.length > 0) {
            type.placeholder(parms[0]);   //占位符
            if (parms.length > 1) {
                type.error(parms[1]);    //图片加载失败显示图片
            }
        }
        type.transform(new CircleTransform(imageView.getContext()));
        type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url, imageView))
                .into(imageView);
    }


    /**
     * 加载网络图片到指定ImageView，支持CircleImageView
     *
     * @param url               图片url
     * @param imageView         要显示的ImageView
     * @param imageLoadListener 图片加载回调
     * @param parms             第一个是error的图片
     */
    public static void loadImage(Context context, final ImageView imageView, final String url, final ImageLoadListener imageLoadListener, final int... parms) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        if (type != null) {
            type.asBitmap();
            if (parms != null && parms.length > 0) {
                type.placeholder(parms[0]);   //占位符
                if (parms.length > 1) {
                    type.error(parms[1]);    //图片加载失败显示图片
                }
            }

            //单张CircleImageView不允许动画，不然会不显示,
            if (imageView instanceof CircleImageView) {
                type.dontAnimate();
            }

            type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url, imageView))
                    .into(imageView);
        }
    }

    /**
     * 加载一帧视频，添加圆角
     *
     * @param url       图片地址
     * @param imageView 要加载到的ImageView
     */
    public static void loadImageFormVideo(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .override(DensityUtils.dp2px(imageView.getContext(), 150), DensityUtils.dp2px(imageView.getContext(), 150))
                //       .placeholder(R.drawable.default_article_image)
                .dontAnimate()
                .into(imageView);
    }


    public static void loadImageDetail(Context context, final ImageView imageView, final String url, final Drawable drawable, final ImageLoadDetailListener imageLoadListener) {
        Glide.with(context).load(url)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (imageView != null && resource != null) {
                            imageView.setImageDrawable(resource);
                        }
                        if (imageLoadListener != null) {
                            imageLoadListener.onLoadingComplete(url, imageView, resource);
                        }

                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        if (drawable != null && imageView != null) {
                            imageView.setImageDrawable(drawable);
                        }

                    }

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        if (imageLoadListener != null) {
                            imageLoadListener.onLoadingStart(placeholder);
                        }

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        if (imageLoadListener != null) {
                            imageLoadListener.onLoadingError(errorDrawable, e);
                        }
                    }
                });
    }


    /**
     * 加载bitmap，回调返回 Bitmap
     *
     * @param context           上下文
     * @param url               图片url
     * @param imageLoadListener 图片加载监听器
     */
    public static void loadImageBitmap(Context context, final String url, final ImageLoadListener imageLoadListener) {
        Glide.with(context).load(url)
                .asBitmap()
                .listener(new GlideListener<String, Bitmap, String>(imageLoadListener, url));

    }


    /**
     * 加载GifDrawable，回调返回 GifDrawable
     *
     * @param context           上下文
     * @param url               图片url
     * @param imageLoadListener 图片加载监听器
     */
    public static void loadImageGif(Context context, final String url, final ImageLoadListener imageLoadListener) {
        Glide.with(context).load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new GlideListener<String, GifDrawable, String>(imageLoadListener, url));

    }


    /**
     * 加载GifDrawable，回调返回 GifDrawable
     *
     * @param url               图片url
     * @param imageLoadListener 图片加载监听器
     */
    public static void loadImageGifThumb(ImageView imageView, final String url, Drawable drawable, final ImageLoadListener imageLoadListener) {
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }

        Glide.with(imageView.getContext())
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new GlideListener<String, Bitmap, String>(imageLoadListener, url))
                .into(imageView);
    }


    /**
     * 加载gif图片到指定ImageView
     *
     * @param imageView         图片View
     * @param url               图片Url
     * @param imageLoadListener 图片加载监听器
     */
    public static void loadImageGif(final ImageView imageView, final String url, final ImageLoadListener imageLoadListener) {
        Glide.with(imageView.getContext())
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new GlideListener<String, GifDrawable, String>(imageLoadListener, url, imageView))
                .into(imageView);
    }

    /**
     *
     * @param context 上下文
     * @param t    t
     */
    public static <T> void loadImage(Context context, ImageView imageView,  T t) {
        Glide.with(context).load(t).into(imageView);
    }


    /**
     * 加载圆形头像，用的是普通ImageView，有动画效果
     *
     * @param url       图片url
     * @param imageView 要显示到的ImageView
     * @param parms     设置占位符和加载失败图片
     */
    public static void loadCircleImage(final ImageView imageView, final String url, final int... parms) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        if (parms != null && parms.length > 0) {
            type.placeholder(parms[0]);   //占位符
            if (parms.length > 1) {
                type.error(parms[1]);    //图片加载失败显示图片
            }
        }
        type.transform(new CircleTransform(imageView.getContext()));
        type.into(imageView);
    }


    /**
     * 加载网络图片到指定ImageView，支持CircleImageView
     *
     * @param url       图片url
     * @param imageView 要显示的ImageView
     * @param parms     第一个是error的图片
     */
    public static void loadImage(Context context, final ImageView imageView, final String url, final int... parms) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        if (type != null) {
            type.asBitmap();
            if (parms != null && parms.length > 0) {
                type.placeholder(parms[0]);   //占位符
                if (parms.length > 1) {
                    type.error(parms[1]);    //图片加载失败显示图片
                }
            }

            //单张CircleImageView不允许动画，不然会不显示,
            if (imageView instanceof CircleImageView) {
                type.dontAnimate();
            }

            type.into(imageView);
        }
    }

    /**
     * 释放内存
     *
     * @param context 上下文
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }


    /**
     * 取消所有正在下载或等待下载的任务。
     *
     * @param context 上下文
     */
    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context 上下文
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }


    /**
     * 清除所有缓存
     *
     * @param context 上下文
     */
    public static void cleanAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }

}