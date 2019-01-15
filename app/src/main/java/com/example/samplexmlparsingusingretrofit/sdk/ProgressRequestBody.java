package com.example.samplexmlparsingusingretrofit.sdk;

import android.support.annotation.StringDef;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {
    private File mFile;
    private String mPath;
    private String mimeType;
    private UploadCallbacks mListener;

    @StringDef({TYPE_IMAGE, TYPE_VIDEO, TYPE_FILE})
    @Retention(RetentionPolicy.SOURCE)

    public @interface MimeType {
    }


    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_FILE = "file";

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage);

        void onError();

        void onFinish();
    }

    public ProgressRequestBody(final File file, @MimeType String mimeType, final UploadCallbacks listener) {
        mFile = file;
        this.mimeType = mimeType;
        mListener = listener;
    }

    @Override
    public MediaType contentType() {
        // i want to upload only images
        if (mimeType.equalsIgnoreCase(TYPE_VIDEO)) {
            return MediaType.parse("video/*");
        } else if (mimeType.equalsIgnoreCase(TYPE_IMAGE)) {
            return MediaType.parse("image/*");
        } else {
            return MediaType.parse("*/*");
        }

    }

    @Override
    public long contentLength() {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                int percentage = (int) ((uploaded / (float) fileLength) * 100);

                //use interface for updating activity
                this.mListener.onProgressUpdate(percentage);
            }

        } finally {
            in.close();
        }
    }




    /*private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;
        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            mListener.onProgressUpdate((int)(100 * mUploaded / mTotal));
        }
    }*/

}
