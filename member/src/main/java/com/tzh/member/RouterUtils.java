package com.tzh.member;

import com.tzh.router.IRouter;
import com.tzh.router.TRouter;

/**
 * create by tuzanhua on 2020/4/18
 */
public class RouterUtils implements IRouter {


    public void putActivity(){
        TRouter.getInstance().putActivity("member/member",MemberActivity.class);
    }
}
