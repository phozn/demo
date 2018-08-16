package com.meicai.demo.cluster;

import com.alibaba.dubbo.rpc.RpcContext;

/**
 * 取得集群Environment
 */
public class ClusterUtil {
    /**
     * 订单按模10划分集群
     *
     * @param orderId
     * @return
     */
    public static void setOrderEnv(long orderId) {
        String env = "C" + (orderId % 10);
        RpcContext.getContext().setAttachment("environment", env);
    }

    public static void setWmsEnv(int cityId) {
        String env;
        if (cityId == 1) {
            env = "F1";
        } else if (cityId == 5) {
            env = "F2";
        } else {
            env = "C" + (cityId % 4);
        }
        RpcContext.getContext().setAttachment("environment", env);
    }
}
