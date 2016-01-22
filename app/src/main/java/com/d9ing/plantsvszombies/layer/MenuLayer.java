package com.d9ing.plantsvszombies.layer;

import com.d9ing.plantsvszombies.base.BaseLayer;
import com.d9ing.plantsvszombies.utils.CommonUtils;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

/**
 * 菜单图层
 * Created by wx on 2016/1/21.
 */
public class MenuLayer extends BaseLayer {
    public MenuLayer() {
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        //创建元素
        CCSprite menuSpr = CCSprite.sprite("image/menu/main_menu_bg.jpg");
        //设置锚点
        menuSpr.setAnchorPoint(0, 0);
        //添加
        this.addChild(menuSpr);
        //状态选择器
        CCSprite normalSprite = CCSprite.sprite("image/menu/start_adventure_default.png");
        CCSprite selectSprite = CCSprite.sprite("image/menu/start_adventure_press.png");
        //菜单item 1.默认显示的精灵 2选中的精灵 3对象 4对应对象的方法
        CCMenuItem items = CCMenuItemSprite.item(normalSprite, selectSprite, this, "click");

        CCMenu menu = CCMenu.menu(items);
        //设置菜单缩放
        menu.setScale(0.5f);
        //设置位置
        menu.setPosition(winSize.width / 2 - 25, winSize.height / 2 - 110);
        //添加菜单
        this.addChild(menu);
    }

    /**
     * 菜单点击事件
     * 菜单能够反射该方法，该方法需要一个Object的参数
     *
     * @param o 表示具体点击的哪个条目的对象
     */
    public void click(Object o) {
        //切换到战斗的地图
        CommonUtils.changeLayer(new FightLayer());
    }
}
