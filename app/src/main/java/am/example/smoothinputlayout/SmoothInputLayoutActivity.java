package am.example.smoothinputlayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import am.widget.smoothinputlayout.SmoothInputLayout;

public class SmoothInputLayoutActivity extends BaseActivity implements View.OnClickListener,
        View.OnTouchListener, TextWatcher, SmoothInputLayout.OnVisibilityChangeListener{

    private SmoothInputLayout lytContent;
    private View btnVoice;
    private EditText edtInput;
    private View btnEmoji;
    private View btnSendVoice;
    private View btnMore;
    private View btnSend;
    private View vEmoji;
    private View vMore;
    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_smoothinputlayout;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setSupportActionBar(R.id.smoothinputlayout_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        lytContent = (SmoothInputLayout) findViewById(R.id.sil_lyt_content);
        btnVoice = findViewById(R.id.sil_ibtn_voice);
        edtInput = (EditText) findViewById(R.id.sil_edt_input);
        btnEmoji = findViewById(R.id.sil_ibtn_emoji);
        btnSendVoice = findViewById(R.id.sil_btn_send_voice);
        btnMore = findViewById(R.id.sil_ibtn_more);
        btnSend = findViewById(R.id.sil_ibtn_send);
        vEmoji = findViewById(R.id.sil_lyt_emoji);
        vMore = findViewById(R.id.sil_lyt_more);
        lytContent.setOnVisibilityChangeListener(this);
        btnVoice.setOnClickListener(this);
        edtInput.addTextChangedListener(this);
        btnEmoji.setOnClickListener(this);
        btnSendVoice.setOnClickListener(this);
        btnMore.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        edtInput.setOnTouchListener(this);
        findViewById(R.id.sil_v_list).setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sil_ibtn_voice:
                btnMore.setSelected(false);
                btnEmoji.setSelected(false);
                if (btnVoice.isSelected()) {
                    btnVoice.setSelected(false);
                    showInputWidget();
                } else {
                    btnVoice.setSelected(true);
                    lytContent.closeInputPane();
                    lytContent.closeKeyboard(true);
                    showVoiceWidget();
                }
                break;
            case R.id.sil_btn_send_voice:
                Toast.makeText(getApplicationContext(), R.string.smoothinputlayout_voice,
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.sil_ibtn_emoji:
                btnMore.setSelected(false);
                if (btnEmoji.isSelected()) {
                    btnEmoji.setSelected(false);
                    showInput();
                } else {
                    btnEmoji.setSelected(true);
                    showEmoji();
                }
                break;
            case R.id.sil_ibtn_more:
                btnEmoji.setSelected(false);
                btnVoice.setSelected(false);
                if (btnMore.isSelected()) {
                    btnMore.setSelected(false);
                    showInput();
                } else {
                    btnMore.setSelected(true);
                    showMore();
                }
                break;
            case R.id.sil_ibtn_send:
                sendMessage();
                break;
        }
    }

    /**
     * 显示输入控件
     */
    private void showInputWidget() {
        edtInput.setVisibility(View.VISIBLE);
        btnEmoji.setVisibility(View.VISIBLE);
        btnSendVoice.setVisibility(View.GONE);
        afterTextChanged(edtInput.getText());
        showInput();
    }

    /**
     * 显示语音控件
     */
    private void showVoiceWidget() {
        edtInput.setVisibility(View.GONE);
        btnEmoji.setVisibility(View.GONE);
        btnSendVoice.setVisibility(View.VISIBLE);
        btnMore.setVisibility(View.VISIBLE);
        btnSend.setVisibility(View.GONE);
    }

    /**
     * 显示输入面板
     */
    private void showInput() {
        lytContent.showKeyboard();
        afterTextChanged(edtInput.getText());
    }

    /**
     *  显示Emoji面板
     */
    private void showEmoji() {
        vEmoji.setVisibility(View.VISIBLE);
        vMore.setVisibility(View.GONE);
        lytContent.showInputPane(true);
    }

    /**
     * 显示更多面板
     */
    private void showMore() {
        edtInput.setVisibility(View.VISIBLE);
        btnEmoji.setVisibility(View.VISIBLE);
        btnSendVoice.setVisibility(View.GONE);
        vEmoji.setVisibility(View.GONE);
        vMore.setVisibility(View.VISIBLE);
        lytContent.showInputPane(false);
    }

    private void sendMessage() {
        Toast.makeText(getApplicationContext(), edtInput.getText().toString().trim(),
                Toast.LENGTH_SHORT).show();
        edtInput.setText(null);
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.toString().trim().length() > 0) {
            btnMore.setVisibility(View.GONE);
            btnSend.setVisibility(View.VISIBLE);
        } else {
            btnMore.setVisibility(View.VISIBLE);
            btnSend.setVisibility(View.GONE);
        }
    }

    @Override
    public void onVisibilityChange(int visibility) {
        if (visibility == View.GONE) {
            btnEmoji.setSelected(false);
        } else {
            btnEmoji.setSelected(vEmoji.getVisibility() == View.VISIBLE);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.sil_v_list:
                btnVoice.setSelected(false);
                btnEmoji.setSelected(false);
                btnMore.setSelected(false);
                lytContent.closeKeyboard(true);
                lytContent.closeInputPane();
                break;
            case R.id.sil_edt_input:
                btnVoice.setSelected(false);
                btnEmoji.setSelected(false);
                btnMore.setSelected(false);
                afterTextChanged(edtInput.getText());
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (lytContent.isInputPaneOpen()) {
            lytContent.closeInputPane();
            return;
        }
        super.onBackPressed();
    }
}
