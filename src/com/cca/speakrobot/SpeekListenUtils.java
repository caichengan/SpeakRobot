package com.cca.speakrobot;

import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * 听说工具
 * 
 * @包名:com.cca.speekrobot
 * @类名:SpeekListenUtils
 * @时间:下午9:53:30
 * @author Administrator
 * @描述:TODO
 */
public class SpeekListenUtils
{
	private Context	mContext;
	public SpeekListenUtils(Context context) {
		this.mContext = context;
		// 请勿在“=”与 appid 之间添加任务空字符或者转义符
		SpeechUtility.createUtility(context, SpeechConstant.APPID + "=57ab2dcc");
	}
	//听的功能
	public void listen(RecognizerDialogListener mRecognizerDialogListener)
	{

		// 1.创建RecognizerDialog对象
		RecognizerDialog mDialog = new RecognizerDialog(mContext, null);
		// 2.设置accent、language等参数
		mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
		// 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解 //结果 //
		// mDialog.setParameter("asr_sch", "1"); //
		// mDialog.setParameter("nlp_version", "2.0");
		// 3.设置回调接口
		mDialog.setListener(mRecognizerDialogListener);
		// 4.显示dialog，接收语音输入
		mDialog.show();

	}
	//说的功能
	public void speak(String text,SynthesizerListener mSynthesizerListener)
	{

		// 1.创建SpeechSynthesizer 对象, 第二个参数：本地合成时传InitListener 
		SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(mContext, null);
		// //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
		// //设置发音人（更多在线发音人，用户可参见 附录13.2
		 mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
		 mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
		 mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
		mTts.setParameter(SpeechConstant.ENGINE_TYPE,
		SpeechConstant.TYPE_CLOUD); //设置云端
		 //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm” //保存在 SD 卡需要在
		// AndroidManifest.xml 添加写 SD 卡权限 //仅支持保存为 pcm 和 wav
		// 格式，如果不需要保存合成音频，注释该行代码
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,"./sdcard/iflytek.pcm"); 
		//3.开始合成
		mTts.startSpeaking(text, mSynthesizerListener);
	}
}
