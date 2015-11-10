package cn.refactor.lib.colordialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.refactor.lib.colordialog.util.DisplayUtil;

/**
 * 作者 : andy
 * 日期 : 15/11/10 11:28
 * 邮箱 : andyxialm@gmail.com
 * 描述 : 提示性的Dialog
 */
public class PromptDialog extends Dialog {

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    public static final int DIALOG_TYPE_INFO    = 0;
    public static final int DIALOG_TYPE_HELP    = 1;
    public static final int DIALOG_TYPE_WRONG   = 2;
    public static final int DIALOG_TYPE_SUCCESS = 3;
    public static final int DIALOG_TYPE_WARNING = 4;
    public static final int DIALOG_TYPE_DEFAULT = DIALOG_TYPE_INFO;
    private int mDialogType;

    private TextView mTitleTv, mContentTv, mPositiveBtn;

    private CharSequence mTitle, mContent, mBtnText;

    private OnPositiveListener mOnPositiveListener;

    public PromptDialog(Context context) {
        this(context, 0);
    }

    public PromptDialog(Context context, int theme) {
        super(context, R.style.color_dialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initListener();

    }

    private void initView() {
        View contentView = View.inflate(getContext(), R.layout.layout_promptdialog, null);
        setContentView(contentView);
        resizeDialog();

        mTitleTv = (TextView) contentView.findViewById(R.id.tvTitle);
        mContentTv = (TextView) contentView.findViewById(R.id.tvContent);
        mPositiveBtn = (TextView) contentView.findViewById(R.id.btnPositive);

        View llBtnGroup = findViewById(R.id.llBtnGroup);
        ImageView logoIv = (ImageView) contentView.findViewById(R.id.logoIv);
        logoIv.setBackgroundResource(getLogoResId(mDialogType));

        LinearLayout topLayout = (LinearLayout) contentView.findViewById(R.id.topLayout);
        ImageView triangleIv = new ImageView(getContext());
        triangleIv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(getContext(), 10)));
        triangleIv.setImageBitmap(createTriangel((int) (DisplayUtil.getScreenSize(getContext()).x * 0.7), DisplayUtil.dp2px(getContext(), 10)));
        topLayout.addView(triangleIv);

        setBtnBackground(mPositiveBtn);
        setBottomCorners(llBtnGroup);


        int radius = DisplayUtil.dp2px(getContext(), 6);
        float[] outerRadii = new float[] { radius, radius, radius, radius, 0, 0, 0, 0 };
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(getContext().getResources().getColor(getColorResId(mDialogType)));
        LinearLayout llTop = (LinearLayout) findViewById(R.id.llTop);
        llTop.setBackgroundDrawable(shapeDrawable);

        mTitleTv.setText(mTitle);
        mContentTv.setText(mContent);
        mPositiveBtn.setText(mBtnText);
    }

    private void resizeDialog() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int)(DisplayUtil.getScreenSize(getContext()).x * 0.7);
        getWindow().setAttributes(params);
    }

    private int getLogoResId(int mDialogType) {
        if (DIALOG_TYPE_DEFAULT == mDialogType) {
            return R.mipmap.ic_info;
        }
        if (DIALOG_TYPE_INFO == mDialogType) {
            return R.mipmap.ic_info;
        }
        if (DIALOG_TYPE_HELP == mDialogType) {
            return R.mipmap.ic_help;
        }
        if (DIALOG_TYPE_WRONG == mDialogType) {
            return R.mipmap.ic_wrong;
        }
        if (DIALOG_TYPE_SUCCESS == mDialogType) {
            return R.mipmap.ic_success;
        }
        if (DIALOG_TYPE_WARNING == mDialogType) {
            return R.mipmap.icon_warning;
        }
        return R.mipmap.ic_info;
    }

    private int getColorResId(int mDialogType) {
        if (DIALOG_TYPE_DEFAULT == mDialogType) {
            return R.color.color_type_info;
        }
        if (DIALOG_TYPE_INFO == mDialogType) {
            return R.color.color_type_info;
        }
        if (DIALOG_TYPE_HELP == mDialogType) {
            return R.color.color_type_help;
        }
        if (DIALOG_TYPE_WRONG == mDialogType) {
            return R.color.color_type_wrong;
        }
        if (DIALOG_TYPE_SUCCESS == mDialogType) {
            return R.color.color_type_success;
        }
        if (DIALOG_TYPE_WARNING == mDialogType) {
            return R.color.color_type_warning;
        }
        return R.color.color_type_info;
    }

    private int getSelBtn(int mDialogType) {
        if (DIALOG_TYPE_DEFAULT == mDialogType) {
            return R.drawable.sel_btn;
        }
        if (DIALOG_TYPE_INFO == mDialogType) {
            return R.drawable.sel_btn_info;
        }
        if (DIALOG_TYPE_HELP == mDialogType) {
            return R.drawable.sel_btn_help;
        }
        if (DIALOG_TYPE_WRONG == mDialogType) {
            return R.drawable.sel_btn_wrong;
        }
        if (DIALOG_TYPE_SUCCESS == mDialogType) {
            return R.drawable.sel_btn_success;
        }
        if (DIALOG_TYPE_WARNING == mDialogType) {
            return R.drawable.sel_btn_warning;
        }
        return R.drawable.sel_btn;
    }

    private void initListener() {
        findViewById(R.id.btnPositive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPositiveListener != null) {
                    mOnPositiveListener.onClick(PromptDialog.this);
                }
            }
        });
    }

    private Bitmap createTriangel(int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        return getBitmap(width, height, getContext().getResources().getColor(getColorResId(mDialogType)));
    }

    private Bitmap getBitmap(int width, int height, int backgroundColor) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, BITMAP_CONFIG);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(backgroundColor);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(width, 0);
        path.lineTo(width / 2, height);
        path.close();

        canvas.drawPath(path, paint);
        return bitmap;

    }

    public void setBtnBackground(final TextView btnOk) {
        btnOk.setTextColor(createColorStateList(getContext().getResources().getColor(getColorResId(mDialogType)),
                getContext().getResources().getColor(R.color.color_dialog_gray)));
        btnOk.setBackgroundDrawable(getContext().getResources().getDrawable(getSelBtn(mDialogType)));
    }


    private void setBottomCorners(View llBtnGroup) {
        int radius = DisplayUtil.dp2px(getContext(), 6);
        float[] outerRadii = new float[] { 0, 0, 0, 0, radius, radius, radius, radius };
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(Color.WHITE);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        llBtnGroup.setBackgroundDrawable(shapeDrawable);
    }

    private ColorStateList createColorStateList(int normal, int pressed) {
        return createColorStateList(normal, pressed, Color.BLACK, Color.BLACK);
    }

    private ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[] { pressed, focused, normal, focused, unable, normal };
        int[][] states = new int[6][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_window_focused };
        states[5] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public PromptDialog setTitleText(CharSequence title) {
        mTitle = title;
        return this;
    }

    public PromptDialog setTitleText(int resId) {
        return setTitleText(getContext().getString(resId));
    }

    public PromptDialog setContentText(CharSequence content) {
        mContent = content;
        return this;
    }

    public PromptDialog setContentText(int resId) {
        return setContentText(getContext().getString(resId));
    }

    public TextView getTitleTextView() {
        return mTitleTv;
    }

    public TextView getContentTextView() {
        return mContentTv;
    }

    public PromptDialog setDialogType(int type) {
        mDialogType = type;
        return this;
    }

    public PromptDialog setPositiveListener(CharSequence btnText, OnPositiveListener l) {
        mBtnText = btnText;
        return setPositiveListener(l);
    }

    public PromptDialog setPositiveListener(int stringResId, OnPositiveListener l) {
        return setPositiveListener(getContext().getString(stringResId), l);
    }

    public PromptDialog setPositiveListener(OnPositiveListener l) {
        mOnPositiveListener = l;
        return this;
    }

    public interface OnPositiveListener {
        void onClick(PromptDialog dialog);
    }

}
