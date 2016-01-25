package com.d9ing.plantsvszombies.layer;

import org.cocos2d.nodes.CCSprite;

import java.util.HashMap;
import java.util.Map;

/**
 * 展示的植物
 * Created by wx on 2016/1/22.
 */
public class ShowPlant {
    private static Map<Integer, Map<String, String>> db;

    //模拟数据库查询
    static {
        String format = "image/fight/chose/choose_default%02d.png";
        db = new HashMap<Integer, Map<String, String>>();
        for (int i = 1; i <= 9; i++) {
            HashMap<String, String> value = new HashMap<String, String>();
            //路径名
            value.put("path", String.format(format, i));
            //阳光值
            value.put("sun", 50 + "");
            db.put(i, value);
        }
    }

    private final CCSprite bgSprite;

    private CCSprite showSprite;

    public ShowPlant(int id) {
        //获取图片路径
        Map<String, String> plant = db.get(id);
        String path = plant.get("path");
        //创建植物的元素
        showSprite = CCSprite.sprite(path);
        //设置锚点
        showSprite.setAnchorPoint(0, 0);
        bgSprite = CCSprite.sprite(path);
        bgSprite.setOpacity(150);//半透明设置
        bgSprite.setAnchorPoint(0, 0);
    }

    public CCSprite getShowSprite() {
        return showSprite;
    }

    public CCSprite getBgSprite() {
        return bgSprite;
    }
}
