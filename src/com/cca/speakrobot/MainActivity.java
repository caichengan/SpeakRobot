package com.cca.speakrobot;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cca.speakrobot.ListDataBean.Cwbean;
import com.cca.speakrobot.ListDataBean.Wsbean;
import com.cca.speekrobot.R;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;


public class MainActivity extends Activity {

	public static final String	TAG	= "MainActivity";

	private SpeekListenUtils utils;
	
	private ListView mList;//listView
	
	private MyListAdapter adapter;//������
	
	private List<ConvertBean> mDatas;//listview�����ݼ���list
	
	private String content="";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }
	private void initData()
	{
		 utils=new SpeekListenUtils(this);
	     mList=(ListView) findViewById(R.id.list_speak);
		//��������
		adapter=new MyListAdapter();
		mList.setAdapter(adapter);
		
	}
	public void clickListen(View view){
		
		utils.listen(new MyRecognizerDialogListener());
	}
	private class MyRecognizerDialogListener implements RecognizerDialogListener{

		@Override
		public void onError(SpeechError arg0)
		{
		}
		@Override
		public void onResult(RecognizerResult arg0, boolean isLast)
		{
			String result=arg0.getResultString();
			Log.i(TAG, "---"+result);
			
			//����json
			Gson json=new Gson();
			ListDataBean bean=json.fromJson(result, ListDataBean.class);
			
			 content += getResult(bean);
			 
			 //�ж��Ƿ������һ��
			 if(isLast){
				 //���һ�Ρ��ʵĻ�content------>convert
				 ConvertBean ask=new ConvertBean();
				 ask.type=0;
				 ask.text=content;
				 if(mDatas==null){
					 mDatas=new LinkedList<ConvertBean>();
				 }
				 mDatas.add(ask);
				 
				 //���content
				 content="";
				 
				 //UIˢ��
				 adapter.notifyDataSetChanged();
				 
				 //�ش�
				 String text=ask.text;
				 ConvertBean answer=null;
				 Random adm=new Random();
				 int textSize=RobotRes.resText.length;
				 int icomSize=RobotRes.resIcon.length;
				 if(text.contains("��Ů")){//����������ܶ��ж�
					 //bean
					 answer =new ConvertBean();
					 answer.type=1;
					 answer.img=RobotRes.resIcon[adm.nextInt(icomSize)];
					 answer.text=RobotRes.resText[adm.nextInt(textSize)];
					 
				 }else{
					 answer =new ConvertBean();
					 answer.type=1;
					 answer.img=-1;
					 answer.text="������˼��û����������˵һ�飡";
				 }
				 
				 //˵����
				 utils.speak(answer.text, null);
				 
				 mDatas.add(answer);
				 //UIˢ��
				 adapter.notifyDataSetChanged();
				 
				 //����������ɼ�
				 mList.setSelection(adapter.getCount());
			 }
		}
	}
	//��ȡ����
	public String getResult(ListDataBean bean)
	{
		List<Wsbean> wsbean=bean.ws;
		StringBuffer sb=new StringBuffer();
		for(Wsbean ws:wsbean){
			List<Cwbean> cwbean=ws.cw;
			for(Cwbean cw:cwbean){
				 sb.append(cw.w);
			}
		}
		return sb.toString();
	}
	//������
	class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount()
		{
			if(mDatas!=null){return mDatas.size();}
			return 0;
		}

		@Override
		public Object getItem(int position)
		{
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			if(convertView==null){
				convertView=View.inflate(getApplicationContext(), R.layout.list_item, null);
				holder=new ViewHolder();
				holder.mAskLinLayout=convertView.findViewById(R.id.ask_container);
				holder.mAnswerRelLayout=convertView.findViewById(R.id.answer_container);
				holder.asktext=(TextView) convertView.findViewById(R.id.ask_text);
				holder.answertext=(TextView) convertView.findViewById(R.id.answer_text);
				holder.answerimg=(ImageView) convertView.findViewById(R.id.answer_img);
				convertView.setTag(holder);
				
			}else{
				holder=(ViewHolder) convertView.getTag();
				
			}
			//��������
			ConvertBean data=mDatas.get(position);
			
			if(data.type==0){
				//��
				holder.mAskLinLayout.setVisibility(View.VISIBLE);
				holder.mAnswerRelLayout.setVisibility(View.GONE);
				
				holder.asktext.setText(data.text);
				
			}else{
				//��
				holder.mAskLinLayout.setVisibility(View.GONE);
				holder.mAnswerRelLayout.setVisibility(View.VISIBLE);
				
				holder.answertext.setText(data.text);
				if(data.img!=-1){
				holder.answerimg.setImageResource(data.img);
				}
			}
			return convertView;
		}
	}
	class ViewHolder{
		View mAskLinLayout;
		View mAnswerRelLayout;
		TextView asktext;
		TextView answertext;
		ImageView answerimg;
	}
}
