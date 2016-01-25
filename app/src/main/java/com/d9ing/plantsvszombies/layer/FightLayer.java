package com.d9ing.plantsvszombies.layer;

import android.view.MotionEvent;

import com.d9ing.plantsvszombies.base.BaseLayer;
import com.d9ing.plantsvszombies.bean.ShowZombies;
import com.d9ing.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 战斗的图层
 * Created by wx on 2016/1/22.
 */
public class FightLayer extends BaseLayer {
    private CCTMXTiledMap map;
    private CGSize contentSize;
    private List<CGPoint> zombilesPoints;
    private CCSprite choose;
    private CCSprite choosed;
    //选择植物是否在移动标识
    private boolean isLock;
    //反选植物是否删除标识
    private boolean isDel;

    public FightLayer() {
        init();
    }


    private void init() {
        loadMap();
        //地图加载完成，移动地图
        moveMap();
        //解析地图
        parserMap();
        //放置僵尸
        showZombies();
    }

    /**
     * 加载展示用的僵尸
     */
    private void showZombies() {
        for (CGPoint point : zombilesPoints
                ) {
            ShowZombies showZombies = new ShowZombies();
            //设置僵尸的位置
            showZombies.setPosition(point);
            //这里屏幕并没有显示，地图没有移动，所以加载对象使用地图，即将僵尸加载到地图上
            map.addChild(showZombies);
        }
    }

    /**
     * 解析地图
     */
    private void parserMap() {
        zombilesPoints = CommonUtils.getMapPoints(map, "zombies");
        //放置僵尸

    }

    /**
     * 移动地图
     */
    private void moveMap() {
        float moveDistance = -contentSize.width + winSize.width;
        //串行动作
        CCMoveBy moveBy = CCMoveBy.action(3, ccp(moveDistance, 0));
        CCSequence sequence = CCSequence.actions(CCDelayTime.action(3), moveBy, CCDelayTime.action(2), CCCallFunc.action(this, "loadContainer"));
        map.runAction(sequence);
    }

    /**
     * 加载地图
     */
    private void loadMap() {
        map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
        //设置地图锚点
        map.setAnchorPoint(0.5f, 0.5f);
        //设置地图位置
        contentSize = map.getContentSize();
        map.setPosition(contentSize.width / 2, contentSize.height / 2);
        //添加图层
        this.addChild(map);
    }

    /**
     * 加载选择容器
     */
    public void loadContainer() {
        //创建已经选择的容器
        choosed = CCSprite.sprite("image/fight/chose/fight_chose.png");
        //创建可选择植物的容器元素
        choose = CCSprite.sprite("image/fight/chose/fight_choose.png");
        choose.setAnchorPoint(0, 0);
        this.addChild(choosed);
        this.addChild(choose);
        //设置已选择的到左上角
        choosed.setAnchorPoint(0, 1f);
        choosed.setPosition(0, winSize.height);
        loadShowPlant();
    }

    private List<ShowPlant> showPlants;

    /**
     * 加载展示用的植物
     */
    private void loadShowPlant() {
        showPlants = new ArrayList<ShowPlant>();
        for (int i = 1; i <= 9; i++) {
            ShowPlant plant = new ShowPlant(i);
            CCSprite showSprite = plant.getShowSprite();
            //获取背景的元素
            CCSprite bgSprite = plant.getBgSprite();
            bgSprite.setPosition(16 + ((i - 1) % 4) * 54, 174 - ((i - 1) / 4) * 59);
            choose.addChild(bgSprite);
            //设置植物的位置排列
            showSprite.setPosition(16 + ((i - 1) % 4) * 54, 174 - ((i - 1) / 4) * 59);
            choose.addChild(showSprite);
            showPlants.add(plant);
        }
        //开启点击事件
        setIsTouchEnabled(true);
    }

    /**
     * 点击事件处理
     *
     * @param event
     * @return
     */
    private List<ShowPlant> selectPlants = new CopyOnWriteArrayList<ShowPlant>();

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        //Android--->Cocos2D
        CGPoint point = this.convertTouchToNodeSpace(event);
        CGRect box = choose.getBoundingBox();
        CGRect choseBox = choosed.getBoundingBox();
        //反选植物逻辑
        if (CGRect.containsPoint(choseBox, point)) {
            //重置标识位
            isDel = false;
            for (ShowPlant plant : selectPlants
                    ) {
                CGRect selectBox = plant.getShowSprite().getBoundingBox();
                if (CGRect.containsPoint(selectBox, point)) {
                    //背景精灵定位，移动回背景精灵
                    CCMoveTo moveTo = CCMoveTo.action(0.5f, plant.getBgSprite().getPosition());
                    plant.getShowSprite().runAction(moveTo);
                    //从集合中移除
                    selectPlants.remove(plant);
                    isDel = true;
                    continue;
                }
                if (isDel) {
                    //未选择植物向左移动
                    CCMoveBy moveBy = CCMoveBy.action(0.5f, ccp(-53, 0));
                    plant.getShowSprite().runAction(moveBy);
                }
            }
        }
        //选择植物逻辑
        else if (CGRect.containsPoint(box, point)) {
            if (selectPlants.size() < 5&&!isLock) {

                //遍历集合
                for (ShowPlant plant : showPlants
                        ) {
                    CGRect box2 = plant.getShowSprite().getBoundingBox();
                    //判断当前点在哪个植物图片上
                    if (CGRect.containsPoint(box2, point)) {
                        isLock = true;
                        System.out.println("选中");
                        CCMoveTo moveTo = CCMoveTo.action(0.5f, ccp(75 + selectPlants.size() * 53, 255));
                        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "unlock"));
                        plant.getShowSprite().runAction(sequence);
                        selectPlants.add(plant);
                    }

                }
            }
        }
        return super.ccTouchesBegan(event);
    }

    /**
     * 控制解锁
     */
    public void unlock(){
        isLock = false;
    }
}
