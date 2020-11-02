package Lock;

import java.util.concurrent.TimeUnit;

/**
 * @author phd
 * @version 1.0
 * @date 2020/11/2 9:56
 */

class Resource implements Runnable{
    private String lockA;
    private String lockB;

    Resource(String lockA,String lockB){
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"自己拥有 \t"+lockA+"\t 获取 \t"+lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockB){
                System.out.println("自己拥有 \t"+lockB+"\t 获取 \t"+lockA);
            }
        }
    }
}


public class DeadLock {

    public static void main(String args[]){
         String lockA = "lockA";
         String lockB = "lockB";
         new Thread(new Resource(lockA,lockB),"ThreadAAA").start();
         new Thread(new Resource(lockB,lockA),"ThreadBBB").start();
    }

}

/**
 C:\IDEA_workshop\JUCDemo\src\Lock>jps -l
 19248 sun.tools.jps.Jps
 21408
 50144 org.jetbrains.idea.maven.server.RemoteMavenServer
 64804 org.jetbrains.jps.cmdline.Launcher
 37656 Lock.DeadLock

 C:\IDEA_workshop\JUCDemo\src\Lock>jstack 37656
 2020-11-02 10:15:18
 Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.201-b09 mixed mode):

 "DestroyJavaVM" #13 prio=5 os_prio=0 tid=0x0000000003363800 nid=0xd4b8 waiting on condition [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "ThreadBBB" #12 prio=5 os_prio=0 tid=0x00000000194b1800 nid=0xb908 waiting for monitor entry [0x000000001a10f000]
 java.lang.Thread.State: BLOCKED (on object monitor)
 at Lock.Resource.run(DeadLock.java:31)
 - waiting to lock <0x00000000d5f7d750> (a java.lang.String)
 - locked <0x00000000d5f7d788> (a java.lang.String)
 at java.lang.Thread.run(Thread.java:748)

 "ThreadAAA" #11 prio=5 os_prio=0 tid=0x00000000194ae800 nid=0x9864 waiting for monitor entry [0x000000001a00e000]
 java.lang.Thread.State: BLOCKED (on object monitor)
 at Lock.Resource.run(DeadLock.java:31)
 - waiting to lock <0x00000000d5f7d788> (a java.lang.String)
 - locked <0x00000000d5f7d750> (a java.lang.String)
 at java.lang.Thread.run(Thread.java:748)

 "Service Thread" #10 daemon prio=9 os_prio=0 tid=0x00000000193cf800 nid=0x6ae8 runnable [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "C1 CompilerThread2" #9 daemon prio=9 os_prio=2 tid=0x00000000193bc000 nid=0xc378 waiting on condition [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "C2 CompilerThread1" #8 daemon prio=9 os_prio=2 tid=0x0000000019356800 nid=0x6cc waiting on condition [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "C2 CompilerThread0" #7 daemon prio=9 os_prio=2 tid=0x0000000019342000 nid=0xc1dc waiting on condition [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "Monitor Ctrl-Break" #6 daemon prio=5 os_prio=0 tid=0x0000000019340800 nid=0x887c runnable [0x0000000019a0e000]
 java.lang.Thread.State: RUNNABLE
 at java.net.SocketInputStream.socketRead0(Native Method)
 at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
 at java.net.SocketInputStream.read(SocketInputStream.java:171)
 at java.net.SocketInputStream.read(SocketInputStream.java:141)
 at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
 at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
 at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
 - locked <0x00000000d5f0d5d0> (a java.io.InputStreamReader)
 at java.io.InputStreamReader.read(InputStreamReader.java:184)
 at java.io.BufferedReader.fill(BufferedReader.java:161)
 at java.io.BufferedReader.readLine(BufferedReader.java:324)
 - locked <0x00000000d5f0d5d0> (a java.io.InputStreamReader)
 at java.io.BufferedReader.readLine(BufferedReader.java:389)
 at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:64)

 "Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x0000000017fc1800 nid=0x899c waiting on condition [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x0000000019318800 nid=0xe578 runnable [0x0000000000000000]
 java.lang.Thread.State: RUNNABLE

 "Finalizer" #3 daemon prio=8 os_prio=1 tid=0x000000000345d000 nid=0x1f10 in Object.wait() [0x000000001930e000]
 java.lang.Thread.State: WAITING (on object monitor)
 at java.lang.Object.wait(Native Method)
 - waiting on <0x00000000d5d88ed0> (a java.lang.ref.ReferenceQueue$Lock)
 at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
 - locked <0x00000000d5d88ed0> (a java.lang.ref.ReferenceQueue$Lock)
 at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
 at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)

 "Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x0000000003453000 nid=0x544c in Object.wait() [0x000000001920f000]
 java.lang.Thread.State: WAITING (on object monitor)
 at java.lang.Object.wait(Native Method)
 - waiting on <0x00000000d5d86bf8> (a java.lang.ref.Reference$Lock)
 at java.lang.Object.wait(Object.java:502)
 at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
 - locked <0x00000000d5d86bf8> (a java.lang.ref.Reference$Lock)
 at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

 "VM Thread" os_prio=2 tid=0x0000000017f77800 nid=0xd058 runnable

 "GC task thread#0 (ParallelGC)" os_prio=0 tid=0x0000000003379000 nid=0x4650 runnable

 "GC task thread#1 (ParallelGC)" os_prio=0 tid=0x000000000337a800 nid=0x8130 runnable

 "GC task thread#2 (ParallelGC)" os_prio=0 tid=0x000000000337c000 nid=0xff2c runnable

 "GC task thread#3 (ParallelGC)" os_prio=0 tid=0x000000000337d800 nid=0x8aac runnable

 "VM Periodic Task Thread" os_prio=2 tid=0x0000000019405800 nid=0xa474 waiting on condition

 JNI global references: 12


 Found one Java-level deadlock:
 =============================
 "ThreadBBB":
 waiting to lock monitor 0x0000000003458f28 (object 0x00000000d5f7d750, a java.lang.String),
 which is held by "ThreadAAA"
 "ThreadAAA":
 waiting to lock monitor 0x000000000345b868 (object 0x00000000d5f7d788, a java.lang.String),
 which is held by "ThreadBBB"

 Java stack information for the threads listed above:
 ===================================================
 "ThreadBBB":
 at Lock.Resource.run(DeadLock.java:31)
 - waiting to lock <0x00000000d5f7d750> (a java.lang.String)
 - locked <0x00000000d5f7d788> (a java.lang.String)
 at java.lang.Thread.run(Thread.java:748)
 "ThreadAAA":
 at Lock.Resource.run(DeadLock.java:31)
 - waiting to lock <0x00000000d5f7d788> (a java.lang.String)
 - locked <0x00000000d5f7d750> (a java.lang.String)
 at java.lang.Thread.run(Thread.java:748)

 Found 1 deadlock.

 */
