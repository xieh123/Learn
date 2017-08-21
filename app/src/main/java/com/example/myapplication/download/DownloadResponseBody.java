package com.example.myapplication.download;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by xieH on 2017/1/16 0016.
 */
public class DownloadResponseBody extends ResponseBody {
    private final ResponseBody mResponseBody;
    private BufferedSource mBufferedSource;

    private DownloadProgressListener progressListener;

    public DownloadResponseBody(ResponseBody responseBody, DownloadProgressListener progressListener) {
        this.progressListener = progressListener;
        this.mResponseBody = responseBody;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;

                if (null != progressListener) {

                    System.out.println("hh----------hh" + contentLength());

                    progressListener.update(totalBytesRead, mResponseBody.contentLength(), bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }
}
