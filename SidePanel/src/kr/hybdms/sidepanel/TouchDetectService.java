package kr.hybdms.sidepanel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.graphics.PixelFormat;

public class TouchDetectService extends Service {
	private ImageView mTouchDetector;							
	private WindowManager.LayoutParams mParams;		
	private WindowManager mWindowManager;		
	
	

	private OnTouchListener mViewTouchListener = new OnTouchListener() {
	    @Override public boolean onTouch(View v, MotionEvent event) {
	        switch(event.getAction()) {

	            case MotionEvent.ACTION_DOWN:
	            	
	            	Intent lsp = new Intent(getBaseContext(), LeftSidePanel.class);
	            	lsp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            	getApplication().startActivity(lsp);
	                break;
	        }
	        
	        return true;
	    }
	};
	
    @Override
    public IBinder onBind(Intent arg0) { return null; }
    
    @Override
    public void onCreate() {
        super.onCreate();
        
       

            mTouchDetector = new ImageView(this);                                         //뷰 생성
            mTouchDetector.setImageResource(R.drawable.detector);
            mTouchDetector.setOnTouchListener(mViewTouchListener);              //팝업뷰에 터치 리스너 등록
            //최상위 윈도우에 넣기 위한 설정
            mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,//항상 최 상위. 터치 이벤트 받을 수 있음.
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,  //포커스를 가지지 않음
                PixelFormat.TRANSLUCENT);                                        //투명
            mParams.gravity = Gravity.LEFT | Gravity.CENTER;                   //왼쪽 상단에 위치하게 함.
            
            mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);  //윈도우 매니저
            mWindowManager.addView(mTouchDetector, mParams);      //윈도우에 뷰 넣기. permission 필요.
        }
        
    

   

  

	@Override
    public void onDestroy() {
        if(mWindowManager != null) {        //서비스 종료시 뷰 제거. *중요 : 뷰를 꼭 제거 해야함.
            if(mTouchDetector != null) mWindowManager.removeView(mTouchDetector);
        }
        super.onDestroy();
    }
}