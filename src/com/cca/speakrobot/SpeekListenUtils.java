package com.cca.speakrobot;

import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * ��˵����
 * 
 * @����:com.cca.speekrobot
 * @����:SpeekListenUtils
 * @ʱ��:����9:53:30
 * @author Administrator
 * @����:TODO
 */
public class SpeekListenUtils
{
	private Context	mContext;
	public SpeekListenUtils(Context context) {
		this.mContext = context;
		// �����ڡ�=���� appid ֮�����������ַ�����ת���
		SpeechUtility.createUtility(context, SpeechConstant.APPID + "=57ab2dcc");
	}
	//���Ĺ���
	public void listen(RecognizerDialogListener mRecognizerDialogListener)
	{

		// 1.����RecognizerDialog����
		RecognizerDialog mDialog = new RecognizerDialog(mContext, null);
		// 2.����accent��language�Ȳ���
		mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
		// ��Ҫ��UI�ؼ�����������⣬����������²������ã�����֮��onResult�ص����ؽ���������� //��� //
		// mDialog.setParameter("asr_sch", "1"); //
		// mDialog.setParameter("nlp_version", "2.0");
		// 3.���ûص��ӿ�
		mDialog.setListener(mRecognizerDialogListener);
		// 4.��ʾdialog��������������
		mDialog.show();

	}
	//˵�Ĺ���
	public void speak(String text,SynthesizerListener mSynthesizerListener)
	{

		// 1.����SpeechSynthesizer ����, �ڶ������������غϳ�ʱ��InitListener 
		SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(mContext, null);
		// //2.�ϳɲ������ã������MSC Reference Manual��SpeechSynthesizer ��
		// //���÷����ˣ��������߷����ˣ��û��ɲμ� ��¼13.2
		 mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //���÷�����
		 mTts.setParameter(SpeechConstant.SPEED, "50");//��������
		 mTts.setParameter(SpeechConstant.VOLUME, "80");//������������Χ 0~100
		mTts.setParameter(SpeechConstant.ENGINE_TYPE,
		SpeechConstant.TYPE_CLOUD); //�����ƶ�
		 //���úϳ���Ƶ����λ�ã����Զ��屣��λ�ã��������ڡ�./sdcard/iflytek.pcm�� //������ SD ����Ҫ��
		// AndroidManifest.xml ���д SD ��Ȩ�� //��֧�ֱ���Ϊ pcm �� wav
		// ��ʽ���������Ҫ����ϳ���Ƶ��ע�͸��д���
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,"./sdcard/iflytek.pcm"); 
		//3.��ʼ�ϳ�
		mTts.startSpeaking(text, mSynthesizerListener);
	}
}
