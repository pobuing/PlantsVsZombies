package com.d9ing.plantsvszombies.layer;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import com.d9ing.plantsvszombies.base.BaseLayer;
import com.d9ing.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFlipXTransition;
import org.cocos2d.transitions.CCJumpZoomTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;

/**
 * Created by wx on 2016/1/21.
 */
public class WelComeLayer extends BaseLayer {

    private CCSprite logoSprite;
    private CCSprite start;

    public WelComeLayer() {

        new AsyncTask<Void, Void, Void>() {
            /**
             * 子线程运行之前
             */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /**
             * 子线程之前执行
             * @param params
             * @return
             */
            @Override
            protected Void doInBackground(Void... params) {
                //模拟后台数据加载操作
                SystemClock.sleep(8000);
                return null;
            }

            /**
             *在子线程之后执行
             * @param aVoid
             */
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (start != null) {
                    start.setVisible(true);
                    //打开当前图层的触摸事件
                    setIsTouchEnabled(true);
                }
            }
        }.execute();
        init();

    }

    /**
     * 触摸事件发生时
     *
     * @param event
     * @return
     */
    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        //获取按钮的框体
        CGRect box = start.getBoundingBox();
        //转换坐标点 Android---->Cocos2d
        CGPoint point = this.convertTouchToNodeSpace(event);
        //判断触摸发生位置
        if (CGRect.containsPoint(box, point)) {
            //切换图层
            CommonUtils.changeLayer(new MenuLayer());

        }
        return super.ccTouchesBegan(event);
    }

    /**
     * 初始化方法
     */
    private void init() {
        logo();

    }

    private void logo() {
        //创建显示元素
        logoSprite = CCSprite.sprite("image/popcap_logo.png");

        //设置Logo位置
        logoSprite.setPosition(winSize.width / 2, winSize.height / 2);
        //图层添加元素
        this.addChild(logoSprite);
        //设置动作
        CCHide ccHide = CCHide.action();
        //停留1秒钟
        CCDelayTime delaytime = CCDelayTime.action(3);
        //串联动作,执行完成后调用欢迎界面
        CCSequence sequence = CCSequence.actions(delaytime, ccHide, delaytime, CCCallFunc.action(this, "loadWelcome"));
        logoSprite.runAction(sequence);
    }

    /**
     * 欢迎界面
     */
    public void loadWelcome() {
        CCSprite welbg = CCSprite.sprite("image/welcome.jpg");
        //修改锚点
        welbg.setAnchorPoint(0, 0);
        //添加元素到图层
        this.addChild(welbg);
        //创建进度
        loading();
    }

    /**
     * 创建进度
     */
    private void loading() {
        CCSprite loading = CCSprite.sprite("image/loading/loading_01.png");
        //设置loading位置
        loading.setPosition(winSize.width / 2, 30);
        this.addChild(loading);
        /*播放序列帧*/
        ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
        String format = "image/loading/loading_%02d.png";
        for (int i = 1; i <= 9; i++) {
            CCSpriteFrame ccSpriteFrame = CCSprite.sprite(String.format(format, i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        //1.名称 2 播放时间 3 帧集合
        CCAnimation animation = CCAnimation.animation("", 0.2f, frames);
        //不要永不停止循环播放，指定第二个参数为false
        CCAnimate animate = CCAnimate.action(animation, false);
        loading.runAction(animate);
        //开始游戏按钮
        start = CCSprite.sprite("image/loading/loading_start.png");
        start.setPosition(winSize.width / 2, 30);
        //设置不可见
        start.setVisible(false);
        this.addChild(start);
    }
}
