package com.d9ing.plantsvszombies.layer;

import com.d9ing.plantsvszombies.base.BaseLayer;

import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGSize;

/**
 * 战斗的图层
 * Created by wx on 2016/1/22.
 */
public class FightLayer extends BaseLayer {
    private CCTMXTiledMap map;
    private CGSize contentSize;

    public FightLayer() {
        init();
    }


    private void init() {
        loadMap();
        //地图加载完成，移动地图
        moveMap();

    }

    /**
     * 移动地图
     */
    private void moveMap() {
        float moveDistance = -contentSize.width +winSize.width;
        //串行动作
        CCMoveBy moveBy = CCMoveBy.action(3, ccp(moveDistance, 0));
        CCSequence sequence = CCSequence.actions(CCDelayTime.action(3), moveBy);
        map.runAction(sequence);
    }

    /**
     * 加载地图
     */
    private void loadMap() {
        map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
        //设置地图锚点
        map.setAnchorPoint(0.5f,0.5f);
        //设置地图位置
        contentSize = map.getContentSize();
        map.setPosition(contentSize.width/2, contentSize.height/2);
        //添加图层
        this.addChild(map);
    }
}
