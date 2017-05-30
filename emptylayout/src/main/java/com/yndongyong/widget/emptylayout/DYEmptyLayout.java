package com.yndongyong.widget.emptylayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.AndroidCharacter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Created by yndongyong on 2017/3/28.
 */
public class DYEmptyLayout extends LinearLayout implements DYEmptyHelper, View.OnClickListener {

    private View containerView;
    private ImageView ivStatus;
    private TextView tvTips;
    private ProgressBar pbProgress;

    public static final int STATUS_LOADING = -1;
    public static final int STATUS_DATA_EMPTY = -2;
    public static final int STATUS_INTERFACE_ERROR = -3;
    public static final int STATUS_NETWORK_ERROR = -4;
    public static final int STATUS_HIDE = -5;

    private int mStatus = STATUS_HIDE;

    private OnStatusClickListener listener;

    private Configuration configuration;

    private Context mContext;

    public DYEmptyLayout(Context context) {
        this(context, null);
    }

    public DYEmptyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DYEmptyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        if (containerView == null) {
            containerView = getRefreshStatusView();
            containerView.setOnClickListener(this);
        }
        addView(containerView);
        this.configuration = createDefaultConfiguration();

    }

    @Override
    public View getRefreshStatusView() {
        View rootView = View.inflate(mContext, R.layout.empty_layout, null);
        ivStatus = (ImageView) rootView.findViewById(R.id.img_status);
        tvTips = (TextView) rootView.findViewById(R.id.tv_tips);
        pbProgress = (ProgressBar) rootView.findViewById(R.id.pb_progress);
        return rootView;
    }

    @Override
    public void changeToLoading() {
        mStatus = STATUS_LOADING;
        containerView.setVisibility(View.VISIBLE);
        ivStatus.setVisibility(GONE);
        tvTips.setVisibility(View.VISIBLE);
        tvTips.setText(configuration.loadingTips);
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeToDataSetEmpty() {
        mStatus = STATUS_DATA_EMPTY;
        containerView.setVisibility(View.VISIBLE);
        ivStatus.setVisibility(VISIBLE);
        ivStatus.setImageResource(configuration.dataSetEmptyImageResId);
        tvTips.setVisibility(View.VISIBLE);
        tvTips.setText(configuration.dataEmptyTips);
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void changeToInterfaceError() {
        mStatus = STATUS_INTERFACE_ERROR;
        containerView.setVisibility(View.VISIBLE);
        ivStatus.setVisibility(VISIBLE);
        ivStatus.setImageResource(configuration.interfaceErrorImageResId);
        tvTips.setVisibility(View.VISIBLE);
        tvTips.setText(configuration.interfaceErrorTips);
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void changeToNetWorkError() {
        mStatus = STATUS_NETWORK_ERROR;
        containerView.setVisibility(View.VISIBLE);
        ivStatus.setVisibility(VISIBLE);
        ivStatus.setImageResource(configuration.networkErrorImageResId);
        tvTips.setVisibility(View.VISIBLE);
        tvTips.setText(configuration.networkErrorTips);
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void changeToHide() {
        mStatus = STATUS_HIDE;
        containerView.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(mStatus);
        }
    }

    private Configuration createDefaultConfiguration() {
        Configuration builder = new Configuration();
        builder.loadingTips = "正在加载...";
        builder.dataEmptyTips = "暂无数据";
        builder.interfaceErrorTips = "加载失败";
        builder.networkErrorTips = "网络错误";

        builder.networkErrorImageResId = R.mipmap.ic_error;
        builder.interfaceErrorImageResId = R.mipmap.ic_retry;
        builder.dataSetEmptyImageResId = R.mipmap.ic_empty;

        return builder;
    }

    /**
     * 点击回调方法
     *
     * @param listener
     */
    public void setOnStatusClickListener(OnStatusClickListener listener) {
        this.listener = listener;
    }

    /**
     * @param _configuration
     */
    public void setConfiguration(Configuration _configuration) {
        if (_configuration.backgroundColor != -1) {
            this.configuration.backgroundColor = _configuration.backgroundColor;
            containerView.setBackgroundColor(this.configuration.backgroundColor);
        }

        if (!TextUtils.isEmpty(_configuration.dataEmptyTips)) {
            this.configuration.dataEmptyTips = _configuration.dataEmptyTips;
        }
        if (!TextUtils.isEmpty(_configuration.loadingTips)) {
            this.configuration.loadingTips = _configuration.loadingTips;
        }
        if (!TextUtils.isEmpty(_configuration.interfaceErrorTips)) {
            this.configuration.interfaceErrorTips = _configuration.interfaceErrorTips;
        }
        if (!TextUtils.isEmpty(_configuration.networkErrorTips)) {
            this.configuration.networkErrorTips = _configuration.networkErrorTips;
        }
        if (_configuration.dataSetEmptyImageResId != -1) {
            this.configuration.dataSetEmptyImageResId = _configuration.dataSetEmptyImageResId;
        }
        if (_configuration.interfaceErrorImageResId != -1) {
            this.configuration.interfaceErrorImageResId = _configuration.interfaceErrorImageResId;
        }
        if (_configuration.networkErrorImageResId != -1) {
            this.configuration.networkErrorImageResId = _configuration.networkErrorImageResId;
        }
        if (_configuration.listener != null)
            this.setOnStatusClickListener(_configuration.listener);

    }

    public interface OnStatusClickListener {
        void onClick(int status);
    }

    public static class Configuration {

        private String loadingTips;
        private String dataEmptyTips;
        private String interfaceErrorTips;
        private String networkErrorTips;

        private int networkErrorImageResId = -1;
        private int interfaceErrorImageResId = -1;
        private int dataSetEmptyImageResId = -1;

        private int backgroundColor =-1;

        private OnStatusClickListener listener;

        /**
         * 设置加载中的文字
         *
         * @param tips
         * @return
         */
        private Configuration setLoadingTips(String tips) {
            this.loadingTips = tips;
            return this;
        }

        /**
         * 设置数据为空的文字
         *
         * @param tips
         * @return
         */
        public Configuration setDataEmptyTips(String tips) {
            this.dataEmptyTips = tips;
            return this;
        }

        /**
         * 设置接口调用错误的提示
         *
         * @param tips
         * @return
         */
        public Configuration setInterfaceErrorTips(String tips) {
            this.interfaceErrorTips = tips;
            return this;
        }

        /**
         * 设置网络错误的文字提示
         *
         * @param tips
         * @return
         */
        public Configuration setNetworkErrorTips(String tips) {
            this.networkErrorTips = tips;
            return this;
        }
        /**
         * 设置网络错误的图片资源
         *
         * @param resId
         * @return
         */
        public Configuration setNetworkErrorImageResId(int resId) {
            this.networkErrorImageResId = resId;
            return this;
        }
        /**
         * 设置接口调用错误的图片资源
         *
         * @param resId
         * @return
         */
        public Configuration setInterfaceErrorImageResId(int resId) {
            this.interfaceErrorImageResId = resId;
            return this;
        }
        /**
         * 设置数据为空的图片资源
         *
         * @param resId
         * @return
         */
        public Configuration setDataSetEmptyImageResId(int resId) {
            this.dataSetEmptyImageResId = resId;
            return this;
        }

        /**
         * 当背景色不是白色是调用
         * @param color
         * @return
         */
        public Configuration setBackgroundColor(int color) {
            this.backgroundColor = color;
            return this;

        }
        /**
         * 设置回调操作
         *
         * @param _listener
         * @return
         */
        public Configuration setOnStatusClickListener(OnStatusClickListener _listener) {
            this.listener = _listener;
            return this;
        }

    }


}
