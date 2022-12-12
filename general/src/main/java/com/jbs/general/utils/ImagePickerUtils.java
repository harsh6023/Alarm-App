package com.jbs.general.utils;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.FileProvider;

import com.jbs.general.General;
import com.jbs.general.R;
import com.jbs.general.annotation.FileMimeType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Jaymin Soni
 * Email: jayminbsoni94@gmail.com
 * Github: https://github.com/jayminbsoni
 */
@Singleton
public class ImagePickerUtils {

    private final Context context;
    private FileUtils fileUtils;

    private ScalingUtils scalingUtils;

    @Inject
    public ImagePickerUtils(Context context) {
        this.context = context;
    }

    public interface IImagePicker {
        void onImagePick(File file);
    }

    private final int COMPRESSION = 100;

    private String mDirectoryPath;
    private final String mFileName = ("profile").concat(".jpg");
    private String mFilePath;

    private Activity activity;
    private IImagePicker iImagePicker;

    public void initializeImagePicker(Activity activity, IImagePicker iImagePicker) {
        fileUtils = General.getInstance().getAppComponent().provideFileUtils();
        scalingUtils = General.getInstance().getAppComponent().provideScalingUtils();

        this.activity = activity;
        this.iImagePicker = iImagePicker;
        this.mDirectoryPath = activity.getCacheDir().getAbsolutePath().concat("/").concat(this.activity.getString(R.string.app_name)).concat("/");
        mFilePath = mDirectoryPath.concat(mFileName);
    }

    public void pickImageFromGallery(ActivityResultLauncher<Intent> activityResultLauncher) {
        // Open Gallery to pick image.
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType(FileMimeType.IMAGE_ALL);

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, FileMimeType.IMAGE_ALL);

        Intent chooserIntent = Intent.createChooser(pickIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        activityResultLauncher.launch(chooserIntent);
        Log.e("Heree", "dghdghghdghd");
        //activity.startActivityForResult(chooserIntent, Constants.RequestCodes.RC_GALLERY);
    }

    public void onGalleryResult(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED)
            return;

        if (data == null || data.getData() == null) {
            Toast.makeText(activity, "Unable to load image!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (iImagePicker != null) {
            try {
                iImagePicker.onImagePick(getPickedRotatedCompressedFile(data));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pickImageFromCamera() {
        if (mDirectoryPath == null) {
            Toast.makeText(activity, "Unable to create directory!", Toast.LENGTH_SHORT).show();
            return;
        }

        File directoryFile = new File(mDirectoryPath);

        // Create project directory if not exists
        if (!directoryFile.exists())
            directoryFile.mkdirs();

        File outputFile = new File(mFilePath);
        Uri outputFileUri = getFileUri(activity, outputFile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        activity.startActivityForResult(cameraIntent, Constants.RequestCodes.RC_CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if (requestCode == Constants.RequestCodes.RC_GALLERY) {

                if (resultCode == RESULT_CANCELED) {
                    return;
                }

                if (data == null || data.getData() == null) {
                    Log.e("Herer", "fdhfhjds");
                    Toast.makeText(activity, "Unable to load image!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (iImagePicker != null) {
                    try {
                        Toast.makeText(context, "Passed 1", Toast.LENGTH_SHORT).show();
                        Log.e("ResultData", data + " ** ");
                        iImagePicker.onImagePick(getPickedRotatedCompressedFile(data));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, e.getMessage() + " **", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, "Herer ...?", Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == Constants.RequestCodes.RC_CAMERA) {
                if (resultCode == RESULT_CANCELED) {
                    if (mFilePath != null) {
                        File outputFile = new File(mFilePath);
                        if (outputFile.exists())
                            outputFile.delete();
                    }
                } else {

                    if (iImagePicker != null) {
                        try {
                            iImagePicker.onImagePick(getCapturedRotatedCompressedFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else {
            Log.e("Herer", "fdhfhjds");
        }
    }

    private Bitmap getRotedFile(String filePath) {
        if (filePath == null) return null;
        int rotate = 0;
        try {
            ExifInterface exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap bmp = BitmapFactory.decodeFile(filePath);

        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    private File getCapturedRotatedCompressedFile() throws IOException {
        if (getRotedFile(mFilePath) == null) return null;

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        getRotedFile(mFilePath).compress(Bitmap.CompressFormat.JPEG, COMPRESSION, bytes);
        File f = new File(mFilePath);
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return new File(decodeFile(f.getAbsolutePath()));
    }

    private File getPickedRotatedCompressedFile(Intent data) throws IOException {
        Log.e("CheckFilesPAth", (data.getData() == null) + "\n\n" + (getRotedFile(fileUtils.getRealFileFromURI(activity,
                data.getData()).getAbsolutePath()) == null) + "\n\n");

        if (data.getData() == null) return null;
        if (getRotedFile(fileUtils.getRealFileFromURI(activity,
                data.getData()).getAbsolutePath()) == null) return null;

        File file = new File(decodeFile(fileUtils.getRealFileFromURI(activity, data.getData()).getAbsolutePath()));

        Bitmap bitmap = getRotedFile(fileUtils.getRealFileFromURI(activity, data.getData()).getAbsolutePath());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESSION, bytes);
        file.createNewFile();
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(bytes.toByteArray());
        fo.close();
        return file;
    }

    private String decodeFile(String path) {
        int desiredWidth = 512;
        int desiredHeight = 512;

        String strMyImagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = scalingUtils.decodeFile(path, desiredWidth, desiredHeight, ScalingUtils.ScalingLogic.FIT);

            if (!(unscaledBitmap.getWidth() <= desiredWidth && unscaledBitmap.getHeight() <= desiredHeight)) {
                // Part 2: Scale image
                scaledBitmap = scalingUtils.createScaledBitmap(unscaledBitmap, desiredWidth, desiredHeight, ScalingUtils.ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            // Store to tmp file
            File mFolder = new File(mDirectoryPath);
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }

            File f = new File(mFolder.getAbsolutePath(), mFileName);

            strMyImagePath = f.getAbsolutePath();
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(f);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESSION, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;

    }

    public Uri getFileUri(Activity activity, File file) {
        return FileProvider.getUriForFile(activity.getApplicationContext(), activity.getPackageName() + ".provider", file);
    }

    /**
     * queryName
     */
    public String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    public Bitmap getUriToBitmap(Uri selectedImage) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 400;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveBitmapToFile(Bitmap bmp, String fileName) {
        try {
            //imgMain.setImageBitmap(bmp);
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName + ".png");
            FileOutputStream fos = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, COMPRESSION, fos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
