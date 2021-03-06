/*
 * This file is a part of ServerTools <http://servertools.info>
 *
 * Copyright (c) 2014 ServerTools
 * Copyright (c) 2014 contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.servertools.core.task;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TickHandler {

    private final ConcurrentLinkedQueue<ITickTask> tasks = new ConcurrentLinkedQueue<>();

    public TickHandler() {
        FMLCommonHandler.instance().bus().register(this);
    }

    public void registerTask(ITickTask task) {
        tasks.offer(task);
    }

    @SubscribeEvent
    public void tickStart(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) { return; }
        for (ITickTask task : tasks) {
            if (task.isComplete()) {
                tasks.remove(task);
                continue;
            }
            task.tick();
        }
    }
}
