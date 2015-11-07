package cn.refactor.lib.colordialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 作者 : andy
 * 日期 : 15/11/7 17:26
 * 邮箱 : andyxialm@gmail.com
 * 描述 : Dialog
 */
public class ColorDialog extends Dialog implements View.OnClickListener {

    private View mBtnGroupView, mDividerView;

    private TextView mTitleTv, mContentTv, mPositiveBtn, mNegativeBtn;

    private ImageView mContentIv;

    private OnPositiveListener mPositiveListener;

    private OnNegativeListener mNegativeListener;

    private CharSequence mTitleText, mContentText, mPositiveText, mNegativeText;

    private Bitmap mContentBitmap;

    private Drawable mDrawable;

    private int mResId, mBackgroundColor;

    public ColorDialog(Context context) {
        this(context, 0);
    }

    public ColorDialog(Context context, int theme) {
        super(context, R.style.color_dialog);
        init();
    }

    private void init() {
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitleText = title;
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getText(titleId));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = View.inflate(getContext(), R.layout.layout_colordialog, null);
        setContentView(contentView);

        mTitleTv = (TextView) contentView.findViewById(R.id.tvTitle);
        mContentTv = (TextView) contentView.findViewById(R.id.tvContent);
        mContentIv = (ImageView) contentView.findViewById(R.id.ivContent);

        mPositiveBtn = (TextView) contentView.findViewById(R.id.btnPositive);
        mNegativeBtn = (TextView) contentView.findViewById(R.id.btnNegative);

        mDividerView = contentView.findViewById(R.id.divider);
        mBtnGroupView = contentView.findViewById(R.id.llBtnGroup);

        mPositiveBtn.setOnClickListener(this);
        mNegativeBtn.setOnClickListener(this);

        mTitleTv.setText(mTitleText);
        mContentTv.setText(mContentText);
        mPositiveBtn.setText(mPositiveText);
        mNegativeBtn.setText(mNegativeText);

        if (null == mPositiveListener && null == mNegativeListener) {
            mBtnGroupView.setVisibility(View.GONE);
        } else if (null == mPositiveListener && null != mNegativeListener) {
            mPositiveBtn.setVisibility(View.GONE);
            mDividerView.setVisibility(View.GONE);
            mNegativeBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.sel_def_gray));
        } else if (null != mPositiveListener && null == mNegativeListener ) {
            mNegativeBtn.setVisibility(View.GONE);
            mDividerView.setVisibility(View.GONE);
            mPositiveBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.sel_def_gray));
        }

        if (null != mDrawable) {
            mContentIv.setBackgroundDrawable(mDrawable);
        }

        if (null != mContentBitmap) {
            mContentIv.setImageBitmap(mContentBitmap);
        }

        if (0 != mResId) {
            mContentIv.setBackgroundResource(mResId);
        }

        setContentMode();
    }

    private void setContentMode() {
        boolean isImageMode = (null != mDrawable | null != mContentBitmap | 0 != mResId);
        boolean isTextMode = (!TextUtils.isEmpty(mContentText));

        if (isImageMode && isTextMode) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mContentTv.getLayoutParams();
            params.gravity = Gravity.BOTTOM;
            mContentTv.setLayoutParams(params);
            mContentTv.setBackgroundColor(Color.BLACK);
            mContentTv.getBackground().setAlpha(0x28);
            mContentTv.setVisibility(View.VISIBLE);
            mContentIv.setVisibility(View.VISIBLE);
            return;
        }

        if (isTextMode) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mContentTv.getLayoutParams();
            params.gravity = Gravity.NO_GRAVITY;
            mContentTv.setLayoutParams(params);
            mContentIv.setVisibility(View.GONE);
            mContentTv.setVisibility(View.VISIBLE);
            return;
        }

        if (isImageMode) {
            mContentTv.setVisibility(View.GONE);
            mContentIv.setVisibility(View.VISIBLE);
            return;
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (R.id.btnPositive == id) {
            mPositiveListener.onClick(this);
        } else if (R.id.btnNegative == id) {
            mNegativeListener.onClick(this);
        } else {
        }
    }

    public ColorDialog setColor(int color) {
        mBackgroundColor = color;
        return this;
    }

    public ColorDialog setColor(String color) {
        try {
            setColor(Color.parseColor(color));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ColorDialog setPositiveListener(CharSequence text, OnPositiveListener l) {
        mPositiveText = text;
        mPositiveListener = l;
        return this;
    }

    public ColorDialog setPositiveListener(int textId, OnPositiveListener l) {
        return setPositiveListener(getContext().getText(textId), l);
    }

    public ColorDialog setNegativeListener(CharSequence text, OnNegativeListener l) {
        mNegativeText = text;
        mNegativeListener = l;
        return this;
    }

    public ColorDialog setNegativeListener(int textId, OnNegativeListener l) {
        return setNegativeListener(getContext().getText(textId), l);
    }

    public ColorDialog setContentText(CharSequence text) {
        mContentText = text;
        return this;
    }

    public ColorDialog setContentText(int textId) {
        return setContentText(getContext().getText(textId));
    }

    public ColorDialog setContentImage(Drawable drawable) {
        mDrawable = drawable;
        return this;
    }

    public ColorDialog setContentImage(Bitmap bitmap) {
        mContentBitmap = bitmap;
        return this;
    }

    public ColorDialog setContentImage(int resId) {
        mResId = resId;
        return this;
    }

    public CharSequence getContentText() {
        return mContentText;
    }

    public CharSequence getTitleText() {
        return mTitleText;
    }

    public CharSequence getPositiveText() {
        return mPositiveText;
    }

    public CharSequence getNegativeText() {
        return mNegativeText;
    }

    public interface OnPositiveListener {
        void onClick(ColorDialog dialog);
    }

    public interface OnNegativeListener {
        void onClick(ColorDialog dialog);
    }
}
