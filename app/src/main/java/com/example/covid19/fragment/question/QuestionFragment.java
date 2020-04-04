package com.example.covid19.fragment.question;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.covid19.R;
import com.example.covid19.base.BaseFragment;
import com.example.covid19.databinding.FragQuestionBinding;
import com.example.covid19.utils.Constant;

public class QuestionFragment extends BaseFragment<FragQuestionBinding,QuestionViewModel> {
    String url ;
    public QuestionFragment() {
    }
    public QuestionFragment(String url) {
        this.url = url;
    }
    @Override
    public Class<QuestionViewModel> getViewmodel() {
        return QuestionViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.frag_question;
    }

    @Override
    public void setBindingViewmodel() {
          binding.setViewmodel(viewmodel);
    }

    @Override
    public void ViewCreated() {
        setupwwebview();
        event();
    }

    private void event() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if( binding.webview.canGoBack()){
                   binding.webview.goBack();
               }
            }
        });
        binding.ivGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.webview.canGoForward()){
                    binding.webview.goForward();
                }
            }
        });
    }

    private  void setupwwebview()
    {
        if(url==null){
            url = Constant.urlMain;
        }
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
        binding.webview.loadUrl(url);
        binding.webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
               // hidden progessbar
                binding.spinKit.setVisibility(View.GONE);
            }
        });
    }
}
