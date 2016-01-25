package com.d9ing.plantsvszombies;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.d9ing.plantsvszombies.layer.FightLayer;
import com.d9ing.plantsvszombies.layer.WelComeLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {

    private CCDirector ccDirector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCocos2D(this);
    }


    /**
     * 初始化Cocos2d配置
     *
     * @param activity
     */
    private void initCocos2D(Activity activity) {
        //创建CCGL
        CCGLSurfaceView surfaceView = new CCGLSurfaceView(activity);

        setContentView(surfaceView);
        ccDirector = CCDirector.sharedDirector();
        ccDirector.attachInView(surfaceView);
        //显示帧率
        ccDirector.setDisplayFPS(true);
        //设置自动屏幕适配
        ccDirector.setScreenSize(480,320);
        //设置水平 向左对齐
        ccDirector.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
        createScene();

    }

    /**
     * 创建图层
     */
    private void createScene() {
        //创建场景
        CCScene scene = CCScene.node();
        //场景添加图层
        scene.addChild(new FightLayer());
        //运行图层
        ccDirector.runWithScene(scene);

    }

    /**
     * 生命周期方法
     */
    @Override
    protected void onResume() {
        super.onResume();
        ccDirector.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ccDirector.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ccDirector.end();
    }
}
