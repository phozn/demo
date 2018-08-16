package com.meicai.demo.fun1.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.schedule.ScheduleParam;
import com.alibaba.dubbo.schedule.dto.TaskResponse;
import com.alibaba.dubbo.schedule.support.AbstractScheduleTaskProcess;
import com.alibaba.dubbo.schedule.manager.ScheduleTaskManager;
import com.sprucetec.bone.ump.annotation.JProfiler;
import com.sprucetec.bone.ump.annotation.JProfilerEnum;
import com.meicai.demo.fun1.entity.Order;
import com.meicai.demo.fun1.manager.OrderManager;
import com.meicai.demo.fun1.service.impl.OrderTaskServiceImpl;

/**
 * @author sylink 处理失败的订单任务
 */
@Service
public class OrderTaskSyncService extends AbstractScheduleTaskProcess<TaskResponse<Order>> {
    @Autowired
    private ScheduleTaskManager scheduleTaskManager;

    @Autowired
    private OrderManager orderManager;

    @Override
    @JProfiler(key = "OrderTaskSyncService.execute", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    public int execute(ScheduleParam param, Integer curServer) {
        return super.execute(param, curServer);
    }

    @Override
    protected List<TaskResponse<Order>> selectTasks(ScheduleParam param, Integer curServer) {
        return scheduleTaskManager.queryExecuteTasks(OrderTaskServiceImpl.ORDERTASKTYPE, param, curServer);
    }

    @Override
    protected void executeTasks(List<TaskResponse<Order>> tasks) {
        this.scheduleTaskManager.lockTasks(tasks);
        for (TaskResponse<Order> task : tasks) {
            try {
                this.orderManager.addOrder(task.getTaskObject());
                this.scheduleTaskManager.doneTask(task);
            } catch (Throwable e) {
                this.scheduleTaskManager.errorTask(task, e);
                logger.error("OrderTaskSyncService.executeTasks,taskId=" + task.getId() + ",error:" + e.getMessage(), e);
            }
        }
    }

}
