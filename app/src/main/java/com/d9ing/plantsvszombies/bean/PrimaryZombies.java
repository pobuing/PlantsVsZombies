package com.d9ing.plantsvszombies.bean;

import com.d9ing.plantsvszombies.baseelment.BaseElement;
import com.d9ing.plantsvszombies.baseelment.Zombies;
import com.d9ing.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

/**
 * 基本的僵尸
 * Created by wx on 2016/1/25.
 */
public class PrimaryZombies extends Zombies {
    String format = "image/zombies/zombies_1/walk/z_1_%02d.png";

    public PrimaryZombies(CGPoint startPoint, CGPoint endPoint) {
        super("image/zombies/zombies_1/walk/z_1_01.png");
        //播放序列帧
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        setPosition(startPoint);
        move();
    }

    @Override
    public void move() {
        CCAction animate = CommonUtils.getAnimate(format, 7, true);
        this.runAction(animate);
        //计算速度
        float speed = CGPointUtil.distance(getPosition(), endPoint) / this.speed;
        CCMoveTo moveTo = CCMoveTo.action(speed, endPoint);
        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "endGame"));
        this.runAction(sequence);
    }

    public void endGame(){
        this.destroy();
    }

    @Override
    public void attack(BaseElement element) {

    }

    @Override
    public void attacked(int attack) {

    }

    @Override
    public void baseAction() {

    }
}
