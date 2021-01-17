package cn.mikylin.litjson.utils;

public enum TacheJsonAction {

    TACHE_BEGIN_READ_KEY,
    TACHE_BEGIN_READ_VALUE,
    TACHE_END_READ_VALUE;

    public static boolean isBeginReadKey(TacheJsonAction action) {
        return action == TACHE_BEGIN_READ_KEY;
    }

    public static boolean isBeginReadValue(TacheJsonAction action) {
        return action == TACHE_BEGIN_READ_VALUE;
    }

    public static boolean isEndReadValue(TacheJsonAction action) {
        return action == TACHE_END_READ_VALUE;
    }
}
