package com.tzh.login;

import com.tzh.router.IRouter;
import com.tzh.router.TRouter;

public class RouterUtils implements IRouter {


    public void putActivity(){
        TRouter.getInstance().putActivity("login/login",LoginActivity.class);
    }
}
