/*
 * Copyright (C) 2011-2014 Chris Vest (mr.chrisvest@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package stormpot;

import java.util.concurrent.BlockingQueue;

public class QAllocThread_ShutdownNullsPoll_Test
extends AllocThread_ShutdownNullsPool_TestTemplate<QSlot<Poolable>, QAllocThread<Poolable>> {

  @Override
  protected QAllocThread<Poolable> createAllocThread(
      BlockingQueue<QSlot<Poolable>> live, BlockingQueue<QSlot<Poolable>> dead) {
    return new QAllocThread<Poolable>(
        live, dead, config, new QSlot<Poolable>(live));
  }

  @Override
  protected QSlot<Poolable> createSlot(BlockingQueue<QSlot<Poolable>> live) {
    QSlot<Poolable> slot = new QSlot<Poolable>(live);
    slot.obj = new GenericPoolable(slot);
    return slot;
  }
}
