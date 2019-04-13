package etc;

import java.lang.ref.SoftReference;
import java.util.*;

public class MyCashe<K, V> {

    private final long maxElements;
    private final long lifeTime;
    private final long idleTime;

    private Map<K, SoftReference<MyCashElement<V>>> myCashElements =
            new HashMap<K, SoftReference<MyCashElement<V>>>();

    private int miss;
    private int hit;
    private Timer timer = new Timer();

    public MyCashe(long maxElements, long lifeTime, long idleTime) {
        this.maxElements = maxElements;
        this.lifeTime = lifeTime;
        this.idleTime = idleTime;
    }

    public void put(final K key, final V value) {
        final MyCashElement<V> myCashElement = new MyCashElement(value);

        if (myCashElements.size() == maxElements) {
            K firstKey = myCashElements.keySet().iterator().next();
            myCashElements.remove(firstKey);
        }

        myCashElements.put(key,
                new SoftReference<MyCashElement<V>>(myCashElement));


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MyCashElement<V> myCashElement =
                        Optional.ofNullable(myCashElements.get(key))
                                .map(k -> k.get())
                                .orElse(null);
                if (myCashElement == null ||
                        myCashElement.getCreateTime() + lifeTime <
                                System.currentTimeMillis()){
                    System.out.println("drop key [" + key +"] by lifeTime");
                    myCashElements.remove(key);
                    this.cancel();
                }
            }
            }
            ,
            lifeTime
        );


        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {

                               MyCashElement<V> myCashElement =
                                       Optional.ofNullable(myCashElements.get(key))
                                               .map(k -> k.get())
                                               .orElse(null);
                               if (myCashElement == null ||
                                       myCashElement.getIdleTime() + idleTime <
                                               System.currentTimeMillis()){
                                   System.out.println("drop key [" + key +"] by idleTime");
                                   myCashElements.remove(key);
                                   this.cancel();
                               }
                           }
                       }
                ,
                idleTime, idleTime);

    }

    public V get(final K key) {

        MyCashElement<V> myCashElement =
                Optional.ofNullable(myCashElements.get(key))
                        .map(k -> k.get())
                        .orElse(null);

        if (myCashElement != null) {
            hit++;
            myCashElement.setIdleTime();
            return myCashElement.getValue();
        } else {
            miss++;
            return null;
        }
    }

    public int getMiss() {
        return miss;
    }

    public int getHit() {
        return hit;
    }
}
