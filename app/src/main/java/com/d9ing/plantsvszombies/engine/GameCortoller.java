package com.d9ing.plantsvszombies.engine;

import com.d9ing.plantsvszombies.bean.PrimaryZombies;
import com.d9ing.plantsvszombies.layer.ShowPlant;
import com.d9ing.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 游戏控制器
 * Created by wx on 2016/1/25.
 */
public class GameCortoller {
    private static GameCortoller ctroller = new GameCortoller();
    //是否开始的标记
    public boolean isStart;
    private CCTMXTiledMap map;
    private List<ShowPlant> selectPlants;
    private List<CGPoint> roadPoints;

    /*单例*/
    private GameCortoller() {

    }

    public static GameCortoller getInstance() {
        return ctroller;
    }

    private static List<FightLine> lines;

    //管理行战场
    static {
        lines = new ArrayList<FightLine>();
        for (int i = 0; i < 5; i++) {
            FightLine line = new FightLine(i);
            lines.add(line);
        }
    }

    /**
     * 开始游戏方法
     *
     * @param map          地图
     * @param selectPlants 已选植物的集合
     */
    public void startGame(CCTMXTiledMap map, List<ShowPlant> selectPlants) {
        isStart = true;
        this.map = map;
        this.selectPlants = selectPlants;
        loadMap();
        //定时器添加 1.调用的方法名 2调用方法的对象 3 间隔时间 4是否暂停
        CCScheduler.sharedScheduler().schedule("addZombies", this, 20, false);
        addZombies(0);
    }

    /**
     * 解析地图
     */
    private void loadMap() {
        roadPoints = CommonUtils.getMapPoints(map, "road");
    }

    /**
     * 添加僵尸
     */
    public void addZombies(float f) {
        Random random = new Random();
        int lineNum = random.nextInt(5);
        PrimaryZombies primaryZomby = new PrimaryZombies(roadPoints.get(lineNum * 2), roadPoints.get(lineNum * 2 + 1));

        map.addChild(primaryZomby);
    }

    /**
     * 结束游戏方法
     */
    public void endGame() {
        isStart = false;
    }
}
