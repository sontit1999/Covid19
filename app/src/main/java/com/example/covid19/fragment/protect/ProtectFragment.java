package com.example.covid19.fragment.protect;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.covid19.R;
import com.example.covid19.base.BaseFragment;
import com.example.covid19.databinding.FragProtectBinding;
import com.example.covid19.utils.Constant;

public class ProtectFragment extends BaseFragment<FragProtectBinding,ProtectViewModel> {
    @Override
    public Class<ProtectViewModel> getViewmodel() {
        return ProtectViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.frag_protect;
    }

    @Override
    public void setBindingViewmodel() {
      binding.setViewmodel(viewmodel);
    }

    @Override
    public void ViewCreated() {
         setupwwebview();
    }
    private  void setupwwebview()
    {

        binding.webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        binding.webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);
        binding.webview.loadUrl("https://baomoi.com/");
        binding.webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // hidden progessbar
                binding.spinKit.setVisibility(View.GONE);
            }
        });
    }
}
