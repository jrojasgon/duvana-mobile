package inope.com.toolrepo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public final class PathUtils {

    private PathUtils() {
    }

    public static String generatePath(Uri uri, Context context) {
        String filePath = null;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            filePath = generateFromKitkat(uri, context);
        }

        if (filePath != null) {
            return filePath;
        }

        Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath == null ? uri.getPath() : filePath;
    }

    @TargetApi(19)
    private static String generateFromKitkat(Uri uri, Context context) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String wholeID = DocumentsContract.getDocumentId(uri);

            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }

            cursor.close();
        }
        return filePath;
    }
}
