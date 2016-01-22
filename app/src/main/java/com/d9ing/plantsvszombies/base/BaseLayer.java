package com.d9ing.plantsvszombies.base;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

/**
 * 所有图层的基类
 * Created by wx on 2016/1/22.
 */
public class BaseLayer extends CCLayer {
    protected CGSize winSize;
    public BaseLayer() {
        //获取屏幕尺寸
        winSize = CCDirector.sharedDirector().getWinSize();
    }
}
