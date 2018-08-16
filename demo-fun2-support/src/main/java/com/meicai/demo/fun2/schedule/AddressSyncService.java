package com.meicai.demo.fun2.schedule;

import com.alibaba.dubbo.schedule.ScheduleParam;
import com.alibaba.dubbo.schedule.dto.TaskResponse;
import com.alibaba.dubbo.schedule.support.AbstractScheduleTaskProcess;
import com.alibaba.dubbo.schedule.manager.ScheduleTaskManager;
import com.sprucetec.bone.ump.annotation.JProfiler;
import com.sprucetec.bone.ump.annotation.JProfilerEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressSyncService extends AbstractScheduleTaskProcess<TaskResponse<AddressSyncDto>> {
    private static final Logger logger = Logger.getLogger(AddressSyncService.class);
    private final String addressSyncTask = "addressSync";

    @Autowired
    private ScheduleTaskManager scheduleTaskManager;

    @Override
    @JProfiler(key = "AddressSyncService.selectTasks", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    protected List<TaskResponse<AddressSyncDto>> selectTasks(ScheduleParam param, Integer curServer) {
        return scheduleTaskManager.queryExecuteTasks(addressSyncTask, param, curServer);
    }

    @Override
    @JProfiler(key = "AddressSyncService.executeTasks", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    protected void executeTasks(List<TaskResponse<AddressSyncDto>> tasks) {
        scheduleTaskManager.lockTasks(tasks);
        for (TaskResponse<AddressSyncDto> task : tasks) {
            try {
                // TODO: 调用对方提供的服务
                scheduleTaskManager.doneTask(task);
            } catch (Throwable e) {
                scheduleTaskManager.errorTask(task, e);
                logger.error("AddressSyncService executeTasks taskId=" + task.getId() + ",error:" + e.getMessage(), e);
            }
        }
    }

}
