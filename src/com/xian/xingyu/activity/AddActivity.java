
package com.xian.xingyu.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xian.xingyu.R;
import com.xian.xingyu.adapter.AddAdapter;
import com.xian.xingyu.base.BaseActivity;
import com.xian.xingyu.bean.EmotionInfo;
import com.xian.xingyu.bean.FileDataInfo;
import com.xian.xingyu.db.DBInfo.Emotion;
import com.xian.xingyu.db.DBInfo.FileData;
import com.xian.xingyu.db.DBManager;
import com.xian.xingyu.util.BaseUtil;
import com.xian.xingyu.view.AddGridView;
import com.xian.xingyu.view.CommonHeadView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends BaseActivity implements OnClickListener {

    private static final String LOG_TAG = "lmf";

    private static final int RC_CAPTURE_IMAGE = 101;
    private Uri fileUri;

    private Context mContext;

    private CommonHeadView mHeadView;
    private EditText mEditText;
    private Button mCameraBtn, mTakePhotoBtn, mRangeBtn;
    private LinearLayout mEditLl;
    private ImageView mEditImageIv;
    private AddGridView mEditGv;
    private AddAdapter mAddAdapter;

    private final int type = Emotion.TYPE_PRIVATE;

    private List<FileDataInfo> mFileList;
    private List<Bitmap> mBitmapList;

    private DBManager mDBManager;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.add_activity);
        mContext = this;

        mDBManager = DBManager.getInstance(mContext);

        initTitle();
        initView();

    }

    private void initTitle() {
        mHeadView = (CommonHeadView) findViewById(R.id.commonhead);
        mHeadView.setTitle("编辑信息页面");
        mHeadView.setLeftText("取消", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });

        mHeadView.setRightText("保存", new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mEditText.getText().length() == 0
                        && (mFileList == null || mFileList.size() == 0)) {
                    Log.e("lmf", ">>>>>>>>请输入必要的信息>>>>>>>>");
                    return;
                }

                // TODO Auto-generated method stub
                Log.e("lmf", ">>>>>>>>保存>>>>>>>>");
                EmotionInfo info = new EmotionInfo();
                info.setContent(mEditText.getText().toString());
                info.setStamp(System.currentTimeMillis());
                info.setType(type);
                info.setStatus(Emotion.STATUS_LOCAL);
                if (mFileList != null && mFileList.size() > 0) {
                    info.setHasPic(true);
                } else {
                    info.setHasPic(false);
                }

                Uri uri = mDBManager.insertEmotion(info);
                Log.e("lmf", ">>>>>>>>uri>>>>>>>>>>" + uri);
                if (info.isHasPic() && uri != null) {
                    String idStr = uri.getLastPathSegment();
                    long id = -1;
                    try {
                        id = Long.valueOf(idStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("lmf", ">>>>>>>>id>>>>>>>>>>" + id + ":" + mFileList.size());
                    if (id != -1) {
                        for (FileDataInfo data : mFileList) {
                            data.setFileId(id);
                            mDBManager.insertFileData(data);
                        }
                    }
                }
                Toast.makeText(mContext, "保存成功", 2000).show();
                onBackPressed();
            }
        });
    }

    private void initView() {

        mEditText = (EditText) findViewById(R.id.add_edit_et);
        mEditLl = (LinearLayout) findViewById(R.id.add_edit_ll);

        mCameraBtn = (Button) findViewById(R.id.add_btm_camera_btn);
        mTakePhotoBtn = (Button) findViewById(R.id.add_btm_take_photo_btn);
        mRangeBtn = (Button) findViewById(R.id.add_btm_range_btn);

        mEditImageIv = (ImageView) findViewById(R.id.add_edit_image1_iv);

        mEditGv = (AddGridView) findViewById(R.id.add_edit_gv);

        mCameraBtn.setOnClickListener(this);
        mTakePhotoBtn.setOnClickListener(this);
        mRangeBtn.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        // overridePendingTransition(R.anim.slide_in_left,
        // R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("lmf", "onDestroy>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.add_btm_camera_btn:
                // create Intent to take a picture and return control to the
                // calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // create a file to save the image
                fileUri = BaseUtil.fromFile(BaseUtil.getFilePath(mContext,
                        BaseUtil.FILE_TYPE_NATIVE));
                // set the image file name
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the
                                                                   // image file
                                                                   // name

                // start the image capture Intent
                startActivityForResult(intent, RC_CAPTURE_IMAGE);
                break;
            case R.id.add_btm_take_photo_btn:
                Intent intentPhoto =
                        new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentPhoto, RC_CAPTURE_IMAGE);
                break;
            case R.id.add_btm_range_btn:

                break;

            default:
                break;
        }
    }

    private static final int IMAGE_SCALE = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult: requestCode: " + requestCode + ", resultCode: "
                + requestCode + ", data: " + data);

        switch (requestCode) {
            case RC_CAPTURE_IMAGE:
                if (RESULT_OK == resultCode) {
                    Log.d(LOG_TAG, "RESULT_OK");
                    String filePath;
                    // Check if the result includes a thumbnail Bitmap
                    if (data != null) {
                        // 没有指定特定存储路径的时候
                        Log.d(LOG_TAG, "data is NOT null, file on default position.");

                        // 指定了存储路径的时候（intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);）
                        // Image captured and saved to fileUri specified in the
                        // Intent
                        Toast.makeText(this, "Image saved to:\n" + data.getData(),
                                Toast.LENGTH_LONG).show();
                        filePath = BaseUtil.selectImageByUri(mContext, data.getData());
                        if (!TextUtils.isEmpty(filePath)) {
                            File oldFile = new File(filePath);
                            File newFile = BaseUtil
                                    .getFilePath(mContext, BaseUtil.FILE_TYPE_NATIVE);
                            BaseUtil.copyFile(oldFile, newFile);
                            filePath = newFile.getAbsolutePath();
                        }

                    } else {
                        filePath = fileUri.getPath();

                    }
                    Log.e("lmf", ">>>>>>>>>>filePath>>>>>>>>>>" + filePath);
                    if (!TextUtils.isEmpty(filePath)) {
                        Log.d(LOG_TAG, "data IS null, file saved on target position.");

                        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

                        factoryOptions.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(filePath, factoryOptions);

                        int imageWidth = factoryOptions.outWidth;
                        int imageHeight = factoryOptions.outHeight;

                        // Determine how much to scale down the image
                        int scaleFactor = Math.min(imageWidth / IMAGE_SCALE, imageHeight
                                / IMAGE_SCALE);
                        Log.e("lmf", ">>>>>>>imageWidth / IMAGE_SCALE>>>>>>>>>>" + imageWidth
                                );
                        Log.e("lmf", ">>>>>>>imageHeight / IMAGE_SCALE>>>>>>>>>>" + imageHeight
                                );
                        Log.e("lmf", ">>>>>>>imageWidth / IMAGE_SCALE>>>>>>>>>>" + imageWidth
                                / IMAGE_SCALE
                                );
                        Log.e("lmf", ">>>>>>>imageHeight / IMAGE_SCALE>>>>>>>>>>" + imageHeight
                                / IMAGE_SCALE
                                );
                        Log.e("lmf", ">>>>>>>scaleFactor>>>>>>>>>>" + scaleFactor);
                        // Decode the image file into a Bitmap sized to fill the
                        // View
                        factoryOptions.inJustDecodeBounds = false;
                        factoryOptions.inSampleSize = scaleFactor;
                        factoryOptions.inPurgeable = true;

                        Bitmap bitmap = BitmapFactory.decodeFile(filePath, factoryOptions);

                        if (mAddAdapter == null) {
                            mFileList = new ArrayList<FileDataInfo>();
                            mBitmapList = new ArrayList<Bitmap>();
                            mAddAdapter = new AddAdapter(mContext);
                            mAddAdapter.setList(mBitmapList);
                            mEditGv.setAdapter(mAddAdapter);
                        }

                        FileDataInfo info = new FileDataInfo();
                        info.setFileType(FileData.FILE_TYPE_EMOTION);
                        info.setUri(filePath);
                        File fileThumb = BaseUtil.saveBitmapFile(mContext, bitmap);
                        if (fileThumb != null) {
                            info.setThumbUri(fileThumb.getPath());
                        }

                        info.setType(FileData.TYPE_IMAGE);
                        info.setStatus(FileData.STATUS_LOCAL);

                        mFileList.add(info);
                        mBitmapList.add(bitmap);
                        mAddAdapter.notifyDataSetChanged();

                        mEditImageIv.setImageBitmap(bitmap);
                    } else {
                        Log.e("lmf", "error>>>>>>>>>>>>>>>>");
                    }

                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                    Log.e("lmf", ">>>>>>>>User cancelled the image capture>>>>>>>>>");
                } else {
                    // Image capture failed, advise user
                    Log.e("lmf", ">>>>>>>>Image capture failed, advise user>>>>>>>>>");
                }
                break;

            default:
                break;
        }

    }

}
