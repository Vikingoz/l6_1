package etc;

import java.util.Date;

public class MyCashElement<V> {
    private final V value;
    private final long createTime;
    private long idleTime;

    public MyCashElement(V value) {
        this.value = value;
        this.createTime = System.currentTimeMillis();
        this.idleTime = System.currentTimeMillis();
    }

    public V getValue() {
        return value;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getIdleTime() {
        return idleTime;
    }

    public void setIdleTime() {
        this.idleTime = System.currentTimeMillis();
    }
}
