package com.d9ing.plantsvszombies.bean;

import com.d9ing.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

/**
 * 僵尸的实体类
 * Created by wx on 2016/1/22.
 */
public class ShowZombies extends CCSprite {
    public ShowZombies() {
        super("image/zombies/zombies_1/shake/z_1_01.png");
        //为防止出屏幕。更改锚点到两腿之间
        setAnchorPoint(0.5f,0);
        setScale(0.5f);
        //播放序列帧
        String format = "image/zombies/zombies_1/shake/z_1_%02d.png";
        CCAction animate = CommonUtils.getAnimate(format, 2, true);
        this.runAction(animate);
    }
}
