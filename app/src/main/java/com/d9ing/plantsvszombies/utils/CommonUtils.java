package com.d9ing.plantsvszombies.utils;

import com.d9ing.plantsvszombies.layer.MenuLayer;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFlipXTransition;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 公共方法工具类
 * Created by wx on 2016/1/22.
 */
public class CommonUtils {


    public static void changeLayer(CCLayer ccLayer) {
        CCScene scene = CCScene.node();
        scene.addChild(ccLayer);
        //1.切换动画的时间 2要切换的场景
        //旋转过度
        CCFlipXTransition transition = CCFlipXTransition.transition(2, scene, 1);
        CCDirector.sharedDirector().replaceScene(transition);
    }

    public static List<CGPoint> getMapPoints(CCTMXTiledMap map, String mapName) {
        List<CGPoint> zombilesPoints = new ArrayList<CGPoint>();
        CCTMXObjectGroup objectGroupNamed = map.objectGroupNamed(mapName);
        ArrayList<HashMap<String, String>> objects = objectGroupNamed.objects;
        for (HashMap<String, String> hashMap : objects) {
            int x = Integer.valueOf(hashMap.get("x"));
            int y = Integer.valueOf(hashMap.get("y"));
            CGPoint cgPoint = CCNode.ccp(x, y);
            zombilesPoints.add(cgPoint);
        }
        return zombilesPoints;
    }

    /**
     * 播放序列帧
     * @param format 格式化的图片文件路径
     * @param number  帧数
     * @param isForever 是否循环
     * @return
     */
    public static CCAction getAnimate(String format, int number, boolean isForever) {
        ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
        //格式化文件名
        //创建帧
        for (int i = 1; i <= number; i++) {
            CCSpriteFrame displayedFrame = CCSprite.sprite(String.format(format, i)).displayedFrame();
            frames.add(displayedFrame);
        }
        //配置序列帧的信息
        //参数1 动作名字 参数2 每一帧播放的时间 参数3 所有用到的帧
        CCAnimation animation = CCAnimation.animation("走路", 0.2f, frames);
        //播放序列帧
        CCAnimate ccAnimate = CCAnimate.action(animation, isForever);
        if (isForever) {
            CCAnimate animate = CCAnimate.action(animation);
            CCRepeatForever forever = CCRepeatForever.action(animate);
            return forever;
        } else {
            CCAnimate animate = CCAnimate.action(animation, false);
            return animate;

        }
    }
}
