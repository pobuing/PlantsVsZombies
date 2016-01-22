package com.d9ing.plantsvszombies.utils;

import com.d9ing.plantsvszombies.layer.MenuLayer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFlipXTransition;

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
}
